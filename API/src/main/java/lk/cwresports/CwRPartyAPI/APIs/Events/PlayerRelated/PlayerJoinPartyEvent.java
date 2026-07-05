package lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated;

import lk.cwresports.CwRPartyAPI.Core.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class PlayerJoinPartyEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final Player joinedPlayer;
    private final Party party;

    public PlayerJoinPartyEvent(Player joinedPlayer, Party party) {
        this.joinedPlayer = joinedPlayer;
        this.party = party;
    }

    public Player getJoinedPlayer() {
        return joinedPlayer;
    }

    public List<Player> getMembersExceptJoinedPlayer() {
        return party.getAllMembersExcept(joinedPlayer);
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
