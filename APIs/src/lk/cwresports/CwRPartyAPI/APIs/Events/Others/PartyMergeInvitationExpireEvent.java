package lk.cwresports.CwRPartyAPI.APIs.Events.Others;

import lk.cwresports.CwRPartyAPI.Core.Party;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PartyMergeInvitationExpireEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final Party inviters;
    private final Party receives;

    public PartyMergeInvitationExpireEvent(Party inviters, Party receives) {
        this.inviters = inviters;
        this.receives = receives;
    }

    public Party getInviters() {
        return inviters;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Party getReceives() {
        return receives;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
