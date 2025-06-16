package lk.cwresports.CwRPartyAPI.Listeners;

import lk.cwresports.CwRPartyAPI.APIs.Events.PartyRelated.PartyCloseEvent;
import lk.cwresports.CwRPartyAPI.APIs.Events.PartyRelated.PartyDisbandEvent;
import lk.cwresports.CwRPartyAPI.APIs.Events.PartyRelated.PartyOpenEvent;
import lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated.*;
import lk.cwresports.CwRPartyAPI.Utils.TextStrings;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class PartyEventListener implements Listener {
    @EventHandler
    public void onPlayerCreateAParty(PlayerCreatePartyEvent event) {
        event.getOwner().sendMessage(TextStrings.colorize(TextStrings.PARTY_CREATED_SUCCESSFULLY));
    }

    private void sendPartyInvite(Player owner, Player invitedPlayer) {
        TextComponent message = new TextComponent(TextStrings.colorize(TextStrings.YOU_HAVE_INVITE.formatted(owner.getName())));
        message.addExtra(new TextComponent("\n"));

        TextComponent acceptButton = new TextComponent(ChatColor.GREEN + " [Join]");
        TextComponent space = new TextComponent("  ");
        TextComponent denyButton = new TextComponent(ChatColor.RED + "[Deny]");

        acceptButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party join " + owner.getName()));
        acceptButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to accept " + owner.getName() + "'s party invitation").color(ChatColor.GREEN).create()));

        // denyButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party deny " + owner.getName()));
        denyButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to deny " + owner.getName() + "'s party invitation").color(ChatColor.RED).create()));

        message.addExtra(acceptButton);
        message.addExtra(space);
        message.addExtra(denyButton);

        invitedPlayer.spigot().sendMessage(message);
    }

    @EventHandler
    public void onOwnerInviteToParty(OwnerInviteToPartyEvent event) {
        Player owner = event.getOwner();
        Player invitedPlayer = event.getInvitedPlayer();
        if (owner == null) return;
        owner.sendMessage(TextStrings.colorize(TextStrings.SEND_INVITATION.formatted(invitedPlayer.getName())));
        // invitedPlayer.sendMessage(TextStrings.colorize(TextStrings.YOU_HAVE_INVITE.formatted(owner.getName())));
        // TODO: Create clickable massage to join.
        // invitedPlayer.sendMessage(TextStrings.colorize("&6/party join " + owner.getName()));

        sendPartyInvite(owner, invitedPlayer);

    }

    @EventHandler
    public void onPlayerJoinParty(PlayerJoinPartyEvent event) {
        List<Player> members = event.getMembersExceptJoinedPlayer();
        Player joinedPlayer = event.getJoinedPlayer();
        Player owner = event.getParty().getOwner();
        for (Player member : members) {
            member.sendMessage(TextStrings.colorize(TextStrings.NEW_MEMBER_JOIN.formatted(joinedPlayer.getName())));
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
            if (owner == worldPLayer) continue;
            worldPLayer.sendMessage(TextStrings.colorize(TextStrings.WORLD_PLAYER_PARTY_PUBLIC.formatted(owner.getName())));
            worldPLayer.sendMessage(TextStrings.colorize("&6/party join " + owner.getName()));
            // TODO click able massage needed.
            // sendPartyInvite(owner, worldPLayer);
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
