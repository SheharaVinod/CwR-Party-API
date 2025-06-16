package lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated;

import lk.cwresports.CwRPartyAPI.Core.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OwnerTryToJoinHisOwnPartyEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final Player owner;
    private final Party party;

    public OwnerTryToJoinHisOwnPartyEvent(Player owner, Party party) {
        this.owner = owner;
        this.party = party;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Player getOwner() {
        return owner;
    }

    public Party getParty() {
        return party;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
