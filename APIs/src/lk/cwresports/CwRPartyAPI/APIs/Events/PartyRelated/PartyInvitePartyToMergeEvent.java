package lk.cwresports.CwRPartyAPI.APIs.Events.PartyRelated;

import lk.cwresports.CwRPartyAPI.Core.Party;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PartyInvitePartyToMergeEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final Party senders;
    private final Party receivers;

    public PartyInvitePartyToMergeEvent(Party senders, Party receivers) {
        this.senders = senders;
        this.receivers = receivers;
    }

    public Party getReceivers() {
        return receivers;
    }

    public Party getSenders() {
        return senders;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
