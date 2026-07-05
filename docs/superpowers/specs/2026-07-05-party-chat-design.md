# Party Chat System — Design Spec

## Overview
Per-party chat system where each `Party` owns a `PartyChat` instance. Messages can be sent directly via `/party chat <msg>` or `/party-chat <msg>`, or toggled so all chat goes to the party.

## Architecture

### API Module (`lk.cwresports.CwRPartyAPI.Core`)

**`PartyChatFormatter`**
- Stores a format string with placeholders `%player_name%` and `{message}`
- `format(playerName, rawMsg)` strips ALL color+format codes from `rawMsg` via `ChatColor.stripColor`, then inserts into the format string and translates color codes
- Provides `DEFAULT_FORMAT = "&a%player_name% &f&l-&r &e{message}"`

**`PartyChat`**
- Owned 1:1 by `Party`, constructed in `Party(owner, plugin)`
- Manages `Set<UUID> toggledPlayers` (per-party, not global)
- `sendMessage(Player sender, String rawMsg)` — formats via `PartyChatFormatter`, broadcasts to all party members via `party.getCopyOfAllMembersIncludingTheOwner()`
- `toggle(Player)`, `isToggled(Player)` — per-player toggle state

**`Party`** — adds `getPartyChat(): PartyChat`, creates PartyChat in constructor

### Plugin Module

**`PartyChatCommand`** — `/party-chat [message]`
- With args: send message directly to party chat, no toggle
- Without args: toggle (existing behavior)

**`PartyCommand.chat()`** — `/party chat [message]`
- With args: send message directly to party chat (NEW)
- Without args: toggle (existing behavior)

**`PartyChatEventListener`** — `AsyncPlayerChatEvent`
- Cancel event if player is in a party with party chat toggled
- Send the cancelled message to party chat via `PartyChat.sendMessage()`

**`config.yml`**
```yaml
PartyChat:
  format: "&a%player_name% &f&l-&r &e{message}"
```

**`CwRPartyAPI.onEnable()`** — loads format from config, creates `PartyChatFormatter`

## Behavior
- Player chat colors (`&0-&f`) and formatting (`&l,&o,&n,&m,&k`) are stripped — plain text only
- Message color in the format is configurable (default yellow `&e`)
- `/party chat <msg>` and `/party-chat <msg>` send immediately without toggle
- Each party has its own toggled-player tracking
