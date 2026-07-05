package lk.cwresports.CwRPartyAPI.Listeners;

import lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated.PlayerLeavePartyEvent;
import lk.cwresports.CwRPartyAPI.Commands.PartyChatCommand;
import lk.cwresports.CwRPartyAPI.Core.Party;
import lk.cwresports.CwRPartyAPI.Core.PartyManager;
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

    @EventHandler
    public void onPlayerLeftFromParty(PlayerLeavePartyEvent event) {
        Player player = event.getLeavedPlayer();
        if (PartyManager.getInstance().isInAParty(player)) {
            Party party = PartyManager.getInstance().getPartyOf(player);
            party.getPartyChat().setToggled(player, false);
        }
    }
}
