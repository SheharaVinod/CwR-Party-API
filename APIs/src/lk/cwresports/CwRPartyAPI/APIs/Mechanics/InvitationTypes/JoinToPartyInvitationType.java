package lk.cwresports.CwRPartyAPI.APIs.Mechanics.InvitationTypes;

import lk.cwresports.CwRPartyAPI.APIs.Events.Others.PartyJoinInvitationExpireEvent;
import lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated.OwnerInviteToPartyEvent;
import lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated.PlayerDeniedInvitationEvent;
import lk.cwresports.CwRPartyAPI.Core.Party;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JoinToPartyInvitationType implements InvitationType {
    private static final Map<Party, Set<Player>> invited_players = new HashMap<>();

    private final Player player;
    private final Party party;
    private final long expire_in;

    public JoinToPartyInvitationType(Party party, Player player, long expire_in) {
        if (!invited_players.containsKey(party)) {
            invited_players.put(party, new HashSet<>());
        }

        this.player = player;
        this.party = party;
        this.expire_in = expire_in;

        invited_players.get(party).add(player);

        //call event
        Event event = new OwnerInviteToPartyEvent(party, player);
        Bukkit.getPluginManager().callEvent(event);

        Bukkit.getScheduler().runTaskLater(party.getPlugin(), () -> {
            Set<Player> players = invited_players.get(this.party);
            if (!players.contains(this.player)) {
                return;
            }

            // call event
            if (this.player == null) {
                return;
            }

            Event expireEvent = new PartyJoinInvitationExpireEvent(this.player, this.party);
            Bukkit.getPluginManager().callEvent(expireEvent);

        }, expire_in);
    }

    @Override
    public Player getReceivedPlayer() {
        return player;
    }

    @Override
    public Party getRequestedParty() {
        return party;
    }

    @Override
    public long getExpireTimeInTicks() {
        return expire_in;
    }

    @Override
    public boolean hasInvitation(Player player) {
        return invited_players.get(this.party).contains(player);
    }

    @Override
    public Set<Player> getInvitedPlayers() {
        return invited_players.get(this.party);
    }

    @Override
    public void denied(Player player) {
        // call event
        Event event = new PlayerDeniedInvitationEvent(player, this.party);
        Bukkit.getPluginManager().callEvent(event);

        invited_players.get(this.party).remove(player);
    }
}
