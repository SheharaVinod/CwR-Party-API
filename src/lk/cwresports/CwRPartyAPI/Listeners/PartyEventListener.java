package lk.cwresports.CwRPartyAPI.Listeners;

import lk.cwresports.CwRPartyAPI.APIs.Events.*;
import lk.cwresports.CwRPartyAPI.Utils.TextStrings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class PartyEventListener implements Listener {
    @EventHandler
    public void onPlayerCreateAParty(PlayerCreatePartyEvent event) {
        event.getOwner().sendMessage(TextStrings.colorize(TextStrings.PARTY_CREATED_SUCCESSFULLY));
    }

    @EventHandler
    public void onOwnerInviteToParty(OwnerInviteToPartyEvent event) {
        Player owner = event.getOwner();
        event.getInvitedPlayer().sendMessage(TextStrings.colorize(TextStrings.YOU_HAVE_INVITE.formatted(owner.getName())));
        // TODO: Create clickable massage to join.
        event.getInvitedPlayer().sendMessage(TextStrings.colorize("&6/party join " + owner.getName()));
    }

    @EventHandler
    public void onPlayerJoinParty(PlayerJoinPartyEvent event) {
        List<Player> members = event.getMembersExceptJoinedPlayer();
        Player joinedPlayer = event.getJoinedPlayer();
        Player owner = event.getParty().getOwner();
        for (Player member : members) {
            member.sendMessage(TextStrings.colorize(TextStrings.NEW_MEMBER_JOIN));
        }
        joinedPlayer.sendMessage(TextStrings.colorize(TextStrings.WELCOME_JOIN_MASSAGE.formatted(owner.getName())));
    }

    @EventHandler
    public void onPlayerLeftTheParty(PlayerLeavePartyEvent event) {
        Player leavedPlayer = event.getLeavedPlayer();
        List<Player> members = event.getParty().getAllMembersExcept(leavedPlayer);
        for (Player member : members) {
            member.sendMessage(TextStrings.colorize(TextStrings.MEMBER_LEFT.formatted(leavedPlayer.getName())));
        }
        leavedPlayer.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_LEFT));
    }

    @EventHandler
    public void onPlayerKickFromTheParty(PlayerKickPartyEvent event) {
        Player kickedPlayer = event.getKickedPlayer();
        List<Player> members = event.getParty().getAllMembersExcept(kickedPlayer);
        for (Player member : members) {
            member.sendMessage(TextStrings.colorize(TextStrings.PLAYER_KICKED.formatted(kickedPlayer.getName())));
        }
        kickedPlayer.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_KICKED));
    }

    @EventHandler
    public void onPartyOpen(PartyOpenEvent event) {
        Player owner = event.getParty().getOwner();
        List<Player> worldPLayers = owner.getWorld().getPlayers();
        for (Player worldPLayer : worldPLayers) {
            worldPLayer.sendMessage(TextStrings.colorize(TextStrings.WORLD_PLAYER_PARTY_PUBLIC));
        }
        owner.sendMessage(TextStrings.colorize(TextStrings.OWNER_OPEN_PARTY));
    }

    @EventHandler
    public void onPartyClose(PartyCloseEvent event) {
        Player owner = event.getParty().getOwner();
        owner.sendMessage(TextStrings.colorize(TextStrings.OWNER_CLOSED_PARTY));
    }

    @EventHandler
    public void onPlayerPromote(PlayerPromotePartyEvent event) {
        Player newOwner = event.getNewOwner();
        newOwner.sendMessage(TextStrings.colorize(TextStrings.PLAYER_PROMOTE_AS_NEW_OWNER));
        List<Player> members = event.getParty().getAllMembersExcept(newOwner);
        for (Player member : members) {
            member.sendMessage(TextStrings.colorize(TextStrings.PLAYER_PROMOTE));
        }
    }

    @EventHandler
    public void onDisband(PartyDisbandEvent event) {
        List<Player> members = event.getParty().getCopyOfAllMembersIncludingTheOwner();
        for (Player member : members) {
            member.sendMessage(TextStrings.colorize(TextStrings.DISBAND_PARTY));
        }
    }
}
