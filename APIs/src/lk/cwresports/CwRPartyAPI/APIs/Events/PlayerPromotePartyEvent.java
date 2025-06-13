package lk.cwresports.CwRPartyAPI.APIs.Events;

import lk.cwresports.CwRPartyAPI.Core.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerPromotePartyEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final Party party;
    private final Player newOwner;
    private final Player oldOwner;

    public PlayerPromotePartyEvent(Party party, Player newOwner, Player oldOwner) {
        this.party = party;
        this.newOwner = newOwner;
        this.oldOwner = oldOwner;
    }

    public Party getParty() {
        return party;
    }

    public Player getNewOwner() {
        return newOwner;
    }

    public Player getOldOwner() {
        return oldOwner;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
