package lk.cwresports.CwRPartyAPI.APIs.Events.Others;

import lk.cwresports.CwRPartyAPI.Core.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PartyJoinInvitationExpireEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final Player invitedPlayer;
    private final Party inviteFrom;

    public PartyJoinInvitationExpireEvent(Player invitedPlayer, Party inviteFrom) {
        this.invitedPlayer = invitedPlayer;
        this.inviteFrom = inviteFrom;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Party getInviteFrom() {
        return inviteFrom;
    }

    public Player getInvitedPlayer() {
        return invitedPlayer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
