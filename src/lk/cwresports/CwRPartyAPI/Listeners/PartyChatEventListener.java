package lk.cwresports.CwRPartyAPI.Listeners;

import lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated.PlayerLeavePartyEvent;
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
                for (Player member : party.getCopyOfAllMembersIncludingTheOwner()) {
                    String msg = "&b" + player.getName() + " &f>>>&b " + event.getMessage();
                    member.sendMessage(TextStrings.colorize(msg, false));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerLeftFromParty(PlayerLeavePartyEvent event) {
        PartyChatCommand.leavePartyChat(event.getLeavedPlayer());
    }
}
