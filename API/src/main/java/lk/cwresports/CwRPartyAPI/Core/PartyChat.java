package lk.cwresports.CwRPartyAPI.Core;

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
