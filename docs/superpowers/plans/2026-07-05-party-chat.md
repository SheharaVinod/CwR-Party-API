# Party Chat System — Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add a per-party chat system with configurable format, color stripping, and direct message command support.

**Architecture:** Each `Party` owns a `PartyChat` instance (API module). `PartyChatFormatter` handles format parsing. Plugin module handles commands and events. Config in config.yml drives the format.

**Tech Stack:** Java 8, Spigot 1.8.8 API, Maven

## Global Constraints

- Java 8 source/target compatibility
- No external dependencies beyond Spigot API
- Follow existing code style (no comments, same package conventions)
- Build output: `CwR-Party-API-v{version}.jar`

---

### Task 1: Create PartyChatFormatter (API module)

**Files:**
- Create: `API/src/main/java/lk/cwresports/CwRPartyAPI/Core/PartyChatFormatter.java`

**Interfaces:**
- Produces: `PartyChatFormatter` class used by `PartyChat` in Task 2

- [ ] **Step 1: Create PartyChatFormatter.java**

```java
package lk.cwresports.CwRPartyAPI.Core;

import org.bukkit.ChatColor;

public class PartyChatFormatter {
    public static final String DEFAULT_FORMAT = "&a%player_name% &f&l-&r &e{message}";

    private String format;

    public PartyChatFormatter(String format) {
        this.format = format;
    }

    public PartyChatFormatter() {
        this(DEFAULT_FORMAT);
    }

    public String format(String playerName, String message) {
        String stripped = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message));
        return ChatColor.translateAlternateColorCodes('&',
                format.replace("%player_name%", playerName).replace("{message}", stripped));
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
```

- [ ] **Step 2: Build**

Run: `mvn clean package`
Expected: SUCCESS in CwR-Party-API-API module

- [ ] **Step 3: Commit**

```bash
git add API/src/main/java/lk/cwresports/CwRPartyAPI/Core/PartyChatFormatter.java
git commit -m "feat: add PartyChatFormatter with configurable format and color stripping"
```

---

### Task 2: Create PartyChat and integrate into Party (API module)

**Files:**
- Create: `API/src/main/java/lk/cwresports/CwRPartyAPI/Core/PartyChat.java`
- Modify: `API/src/main/java/lk/cwresports/CwRPartyAPI/Core/Party.java`

**Interfaces:**
- Consumes: `PartyChatFormatter` from Task 1
- Produces: `PartyChat` class, `Party.getPartyChat()` method

- [ ] **Step 1: Create PartyChat.java**

```java
package lk.cwresports.CwRPartyAPI.Core;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PartyChat {
    private final Party party;
    private final Set<UUID> toggledPlayers = new HashSet<>();
    private PartyChatFormatter formatter;

    public PartyChat(Party party, PartyChatFormatter formatter) {
        this.party = party;
        this.formatter = formatter;
    }

    public boolean isToggled(Player player) {
        return toggledPlayers.contains(player.getUniqueId());
    }

    public void toggle(Player player) {
        if (isToggled(player)) {
            toggledPlayers.remove(player.getUniqueId());
        } else {
            toggledPlayers.add(player.getUniqueId());
        }
    }

    public void setToggled(Player player, boolean toggled) {
        if (toggled) {
            toggledPlayers.add(player.getUniqueId());
        } else {
            toggledPlayers.remove(player.getUniqueId());
        }
    }

    public void sendMessage(Player sender, String message) {
        String formatted = formatter.format(sender.getName(), message);
        for (Player member : party.getCopyOfAllMembersIncludingTheOwner()) {
            member.sendMessage(formatted);
        }
    }

    public void setFormatter(PartyChatFormatter formatter) {
        this.formatter = formatter;
    }

    public PartyChatFormatter getFormatter() {
        return formatter;
    }
}
```

- [ ] **Step 2: Add PartyChat to Party.java**

In `Party.java`:
- Add field: `private final PartyChat partyChat;`
- Add import for PartyChat
- In constructor, after `manager = PartyManager.getInstance();`, add:
  ```java
  this.partyChat = new PartyChat(this, new PartyChatFormatter());
  ```
- Add method:
  ```java
  public PartyChat getPartyChat() {
      return partyChat;
  }
  ```

- [ ] **Step 3: Build**

Run: `mvn clean package`
Expected: SUCCESS

- [ ] **Step 4: Commit**

```bash
git add API/src/main/java/lk/cwresports/CwRPartyAPI/Core/PartyChat.java
git add API/src/main/java/lk/cwresports/CwRPartyAPI/Core/Party.java
git commit -m "feat: add PartyChat per-party instance with toggle and send"
```

---

### Task 3: Refactor PartyChatCommand for direct message support (Plugin module)

**Files:**
- Modify: `Plugin/src/main/java/lk/cwresports/CwRPartyAPI/Commands/PartyChatCommand.java`

**Interfaces:**
- Consumes: `Party`, `PartyManager`, `PartyChat` from API module
- `/party-chat [message]` — with message → direct send; without → toggle

- [ ] **Step 1: Update PartyChatCommand.java**

Replace the `onCommand` method and add helper methods:

```java
@Override
public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
    if (!(commandSender instanceof Player)) {
        return true;
    }
    Player player = (Player) commandSender;

    if (!PartyManager.getInstance().isInAParty(player)) {
        player.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_NOT_IN_A_PARTY));
        return true;
    }

    Party party = PartyManager.getInstance().getPartyOf(player);

    if (strings.length > 0) {
        String message = String.join(" ", strings);
        party.getPartyChat().sendMessage(player, message);
        return true;
    }

    togglePartyChat(player);
    return true;
}
```

Remove the `toggledPlayer` field and toggle methods — they're now per-party in `PartyChat`. The static methods `joinPartyChat`, `leavePartyChat`, `isInPartyChat` should now delegate to `PartyChat`. Update them:

```java
public static boolean isInPartyChat(Player player) {
    if (!PartyManager.getInstance().isInAParty(player)) return false;
    return PartyManager.getInstance().getPartyOf(player).getPartyChat().isToggled(player);
}

public static void togglePartyChat(Player player) {
    if (!PartyManager.getInstance().isInAParty(player)) return;
    PartyChat partyChat = PartyManager.getInstance().getPartyOf(player).getPartyChat();
    partyChat.toggle(player);
    if (partyChat.isToggled(player)) {
        player.sendMessage(TextStrings.colorize("&7You are now in party chat."));
    } else {
        player.sendMessage(TextStrings.colorize("&7You are no longer in party chat."));
    }
}
```

- [ ] **Step 2: Build**

Run: `mvn clean package`
Expected: SUCCESS

- [ ] **Step 3: Commit**

```bash
git add Plugin/src/main/java/lk/cwresports/CwRPartyAPI/Commands/PartyChatCommand.java
git commit -m "refactor: PartyChatCommand uses per-party PartyChat, supports direct message"
```

---

### Task 4: Update PartyCommand.chat() for direct message (Plugin module)

**Files:**
- Modify: `Plugin/src/main/java/lk/cwresports/CwRPartyAPI/Commands/PartyCommand.java`

**Interfaces:**
- `/party chat [message]` — with message → direct send; without → toggle

- [ ] **Step 1: Update chat() method in PartyCommand.java**

Replace the `chat` method:

```java
public void chat(Player sender, String[] strings) {
    if (!PartyManager.getInstance().isInAParty(sender)) {
        sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_NOT_IN_A_PARTY));
        return;
    }

    if (strings.length > 1) {
        String[] messageArgs = new String[strings.length - 1];
        System.arraycopy(strings, 1, messageArgs, 0, strings.length - 1);
        String message = String.join(" ", messageArgs);
        Party party = PartyManager.getInstance().getPartyOf(sender);
        party.getPartyChat().sendMessage(sender, message);
        return;
    }

    PartyChatCommand.togglePartyChat(sender);
}
```

- [ ] **Step 2: Build**

Run: `mvn clean package`
Expected: SUCCESS

- [ ] **Step 3: Commit**

```bash
git add Plugin/src/main/java/lk/cwresports/CwRPartyAPI/Commands/PartyCommand.java
git commit -m "feat: /party chat <message> sends directly to party chat"
```

---

### Task 5: Update PartyChatEventListener (Plugin module)

**Files:**
- Modify: `Plugin/src/main/java/lk/cwresports/CwRPartyAPI/Listeners/PartyChatEventListener.java`

**Interfaces:**
- Consumes: `PartyChat.isToggled()`, `PartyChat.sendMessage()`

- [ ] **Step 1: Update PartyChatEventListener.java**

Replace the handler to use per-party chat:

```java
package lk.cwresports.CwRPartyAPI.Listeners;

import lk.cwresports.CwRPartyAPI.Commands.PartyChatCommand;
import lk.cwresports.CwRPartyAPI.Core.Party;
import lk.cwresports.CwRPartyAPI.Core.PartyManager;
import lk.cwresports.CwRPartyAPI.Utils.TextStrings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PartyChatEventListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (PartyManager.getInstance().isInAParty(player)) {
            if (PartyChatCommand.isInPartyChat(player)) {
                event.setCancelled(true);
                Party party = PartyManager.getInstance().getPartyOf(player);
                party.getPartyChat().sendMessage(player, event.getMessage());
            }
        }
    }
}
```

- [ ] **Step 2: Build**

Run: `mvn clean package`
Expected: SUCCESS

- [ ] **Step 3: Commit**

```bash
git add Plugin/src/main/java/lk/cwresports/CwRPartyAPI/Listeners/PartyChatEventListener.java
git commit -m "refactor: PartyChatEventListener uses per-party PartyChat"
```

---

### Task 6: Add config entries and load in main plugin

**Files:**
- Modify: `Plugin/src/main/resources/config.yml`
- Modify: `Plugin/src/main/java/lk/cwresports/CwRPartyAPI/CwRPartyAPI.java`

- [ ] **Step 1: Add PartyChat section to config.yml**

Append at end of config.yml:

```yaml
PartyChat:
  format: "&a%player_name% &f&l-&r &e{message}"
```

- [ ] **Step 2: Load format in CwRPartyAPI.onEnable()**

After `ConfigUtils.reload(this);`, add:

```java
String chatFormat = getConfig().getString("PartyChat.format", PartyChatFormatter.DEFAULT_FORMAT);
PartyChatFormatter globalFormatter = new PartyChatFormatter(chatFormat);
```

- [ ] **Step 3: Build**

Run: `mvn clean package`
Expected: SUCCESS

- [ ] **Step 4: Commit**

```bash
git add Plugin/src/main/resources/config.yml
git add Plugin/src/main/java/lk/cwresports/CwRPartyAPI/CwRPartyAPI.java
git commit -m "feat: add PartyChat format to config and load on enable"
```

---

### Task 7: Final build, verify, and push

- [ ] **Step 1: Full clean build**

Run: `mvn clean package`
Expected: BUILD SUCCESS, `Plugin/target/CwR-Party-API-v1.0.0.jar` exists

- [ ] **Step 2: Verify plugin.yml has correct version**

Run: `unzip -p Plugin/target/CwR-Party-API-v1.0.0.jar plugin.yml | grep version`
Expected: `version: 1.0.0`

- [ ] **Step 3: Push all commits**

```bash
git push
```
