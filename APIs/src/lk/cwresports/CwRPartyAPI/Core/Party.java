package lk.cwresports.CwRPartyAPI.Core;

import lk.cwresports.CwRPartyAPI.APIs.Events.PartyRelated.PartyCloseEvent;
import lk.cwresports.CwRPartyAPI.APIs.Events.PartyRelated.PartyDisbandEvent;
import lk.cwresports.CwRPartyAPI.APIs.Events.PartyRelated.PartyOpenEvent;
import lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class Party {
    private Player owner;
    private final LinkedList<Player> list_of_players = new LinkedList<>();
    private final Set<Player> invited_players = new HashSet<>();
    private final Set<Player> invited_parties = new HashSet<>();
    private boolean isOpened = false;
    private final PartyManager manager;
    private final Plugin plugin;

    // times.
    private final long party_join_invite_expired_in;
    private final long party_merge_invite_expired_in;


    public Party(Player owner, Plugin plugin, long join_exp, long merge_exp) {
        this.owner = owner;
        this.plugin = plugin;

        this.party_merge_invite_expired_in = merge_exp;
        this.party_join_invite_expired_in = join_exp;

        manager = PartyManager.getInstance();
        manager.registerPlayer(owner, this);

        // call event
        Event event = new PlayerCreatePartyEvent(owner, this);
        Bukkit.getPluginManager().callEvent(event);
    }

    public Player getOwner() {
        if (owner.isOnline()) {
            return owner;
        } else {
            return getCoreOwner();
        }
    }

    public Player getRealOwner() {
        return owner;
    }

    public boolean isOwner(Player player) {
        return player == getOwner();
    }

    public Player getCoreOwner() {
        if (list_of_players.isEmpty()) {
            return owner;
        }
        return list_of_players.getFirst();
    }

    public LinkedList<Player> getMembers() {
        return list_of_players;
    }

    public List<Player> getAllMembersExcept(Player player) {
        List<Player> list = getCopyOfAllMembersIncludingTheOwner();
        list.remove(player);
        return list;
    }

    public List<Player> getCopyOfAllMembersIncludingTheOwner() {
        List<Player> playerList = new ArrayList<>(list_of_players);
        playerList.add(owner);
        return playerList;
    }

    @SuppressWarnings("Please don't use derectly use PartyManager instead.")
    public void invite(Player player, InviteTo to) {
        if (to == InviteTo.JOIN_PARTY) {
            // call event
            Event event = new OwnerInviteToPartyEvent(this, player);
            Bukkit.getPluginManager().callEvent(event);

            invited_players.add(player);

            Bukkit.getScheduler().runTaskLater(plugin, party_join_invite_expired_in,)
        } else if (to == InviteTo.MERGE_WITH_PARTY) {
            boolean inAParty = manager.isInAParty(player);
            if (inAParty) {
                // call event
                Event event = new OwnerInviteToMergePartyEvent(this, manager.getPartyOf(player));
                Bukkit.getPluginManager().callEvent(event);

                invited_parties.add(player);
            }
        }
    }

    public boolean hasInvite(Player player) {
        if (isOpened) return true;
        return invited_players.contains(player);
    }

    public void denied(Player player) {
        // call event
        Event event = new PlayerDeniedInvitationEvent(player, this);
        Bukkit.getPluginManager().callEvent(event);

        invited_players.remove(player);
    }

    public void disbandParty() {
        // call event
        Event event = new PartyDisbandEvent(this);
        Bukkit.getPluginManager().callEvent(event);

        for (Player member : getMembers()) {
            removePlayer(member);
        }
        manager.unregisterPlayer(owner);
    }

    public void addPlayer(Player player) {
        if (player == owner) return;

        if (isOpened) {
            add_player_If_he_is_not_already_in_the_party_and_not_the_owner(player);
            return;
        }

        if (invited_players.contains(player)) {
            add_player_If_he_is_not_already_in_the_party_and_not_the_owner(player);
            invited_players.remove(player);
        }
    }

    public void removePlayer(Player player) {
        manager.unregisterPlayer(player);
        list_of_players.remove(player);
    }

    public void open() {
        isOpened = true;

        // call event
        Event event = new PartyOpenEvent(this);
        Bukkit.getPluginManager().callEvent(event);
    }

    public void closed() {
        isOpened = false;

        // call event
        Event event = new PartyCloseEvent(this);
        Bukkit.getPluginManager().callEvent(event);
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void mergeWith(Party anotherParty) {
        Player anotherOwner = anotherParty.getOwner();

        manager.unregisterPlayer(anotherOwner);
        list_of_players.addFirst(anotherOwner);
        manager.registerPlayer(anotherOwner, this);

        for (Player member : anotherParty.getMembers()) {
            manager.unregisterPlayer(member);
            addPlayerLast(member);
        }
    }

    public void promote(Player player) {
        // call event
        Event event = new PlayerPromotePartyEvent(this, player, owner);
        Bukkit.getPluginManager().callEvent(event);

        if (!list_of_players.contains(player)) return;
        list_of_players.remove(player);
        addPlayerLast(owner);
        owner = player;
    }


    private void add_player_If_he_is_not_already_in_the_party_and_not_the_owner(Player player) {
        if (!(owner == player)) return;
        if (!list_of_players.contains(player)) {
            addPlayerLast(player);
        }
    }

    private void addPlayerLast(Player player) {
        list_of_players.addLast(player);
        manager.registerPlayer(player, this);
    }
}
