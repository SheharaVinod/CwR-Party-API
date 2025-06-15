package lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated;

import lk.cwresports.CwRPartyAPI.Core.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerCreatePartyEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final Player owner;
    private final Party party;

    public PlayerCreatePartyEvent(Player owner, Party party) {
        this.owner = owner;
        this.party = party;
    }

    public Party getParty() {
        return party;
    }

    public Player getOwner() {
        return owner;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
