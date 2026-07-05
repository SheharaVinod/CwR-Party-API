package lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated;

import lk.cwresports.CwRPartyAPI.Core.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLeavePartyEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final Player leavedPlayer;
    private final Party party;

    public PlayerLeavePartyEvent(Player leavedPlayer, Party party) {
        this.leavedPlayer = leavedPlayer;
        this.party = party;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Party getParty() {
        return party;
    }

    public Player getLeavedPlayer() {
        return leavedPlayer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
