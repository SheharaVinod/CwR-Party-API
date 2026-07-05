package lk.cwresports.CwRPartyAPI.APIs.Events.PartyRelated;

import lk.cwresports.CwRPartyAPI.Core.Party;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PartyCloseEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final Party party;

    public PartyCloseEvent(Party party) {
        this.party = party;
    }

    public Party getParty() {
        return party;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
