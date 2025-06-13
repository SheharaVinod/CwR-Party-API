package lk.cwresports.CwRPartyAPI.APIs.Events;

import lk.cwresports.CwRPartyAPI.Core.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OwnerInviteToPartyEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final Party party;
    private final Player invitedPlayer;

    public OwnerInviteToPartyEvent(Party party, Player invitedPlayer) {
        this.party = party;
        this.invitedPlayer = invitedPlayer;
    }

    public Player getInvitedPlayer() {
        return invitedPlayer;
    }

    public Party getParty() {
        return party;
    }

    public Player getOwner() {
        return party.getOwner();
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
