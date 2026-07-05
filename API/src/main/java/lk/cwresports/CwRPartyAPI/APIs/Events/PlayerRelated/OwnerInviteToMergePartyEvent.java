package lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated;

import lk.cwresports.CwRPartyAPI.Core.Party;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OwnerInviteToMergePartyEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final Party inviters;
    private final Party receivers;

    public OwnerInviteToMergePartyEvent(Party inviters, Party receivers) {
        this.inviters = inviters;
        this.receivers = receivers;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Party getReceivers() {
        return receivers;
    }

    public Party getInviters() {
        return inviters;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
