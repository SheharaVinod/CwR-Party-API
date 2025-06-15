package lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated;

import lk.cwresports.CwRPartyAPI.Core.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKickPartyEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final Player kickedPlayer;
    private final Party party;

    public PlayerKickPartyEvent(Player kickedPlayer, Party party) {
        this.kickedPlayer = kickedPlayer;
        this.party = party;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Party getParty() {
        return party;
    }

    public Player getKickedPlayer() {
        return kickedPlayer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
