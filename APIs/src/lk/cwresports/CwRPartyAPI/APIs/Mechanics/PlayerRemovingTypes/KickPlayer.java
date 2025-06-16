package lk.cwresports.CwRPartyAPI.APIs.Mechanics.PlayerRemovingTypes;

import lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated.PlayerKickPartyEvent;
import lk.cwresports.CwRPartyAPI.Core.Party;
import lk.cwresports.CwRPartyAPI.Core.PartyManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.LinkedList;

public class KickPlayer implements RemovingTypes {
    private final Player player;
    private final LinkedList<Player> members;

    public KickPlayer(Player player, LinkedList<Player> list_of_players) {
        this.player = player;
        this.members = list_of_players;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public LinkedList<Player> getMembers() {
        return members;
    }

    @Override
    public void execute(Party party, PartyManager manager) {
        manager.unregisterPlayer(player);
        getMembers().remove(player);

        // call event
        Event event = new PlayerKickPartyEvent(this.player, party);
        Bukkit.getPluginManager().callEvent(event);
    }
}
