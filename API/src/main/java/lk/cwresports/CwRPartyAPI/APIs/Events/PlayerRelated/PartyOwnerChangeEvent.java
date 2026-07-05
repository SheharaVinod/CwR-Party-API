package lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated;

import lk.cwresports.CwRPartyAPI.Core.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PartyOwnerChangeEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final Party party;
    private final Player oldOwner;
    private final Player newOwner;

    public PartyOwnerChangeEvent(Party party, Player oldOwner, Player newOwner) {
        this.party = party;
        this.oldOwner = oldOwner;
        this.newOwner = newOwner;
    }

    public Party getParty() {
        return party;
    }

    public Player getOldOwner() {
        return oldOwner;
    }

    public Player getNewOwner() {
        return newOwner;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
