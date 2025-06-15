package lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated;

import lk.cwresports.CwRPartyAPI.Core.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDeniedInvitationEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final Player deniedPlayer;
    private final Party party;

    public PlayerDeniedInvitationEvent(Player deniedPlayer, Party party) {
        this.deniedPlayer = deniedPlayer;
        this.party = party;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Party getParty() {
        return party;
    }

    public Player getDeniedPlayer() {
        return deniedPlayer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
