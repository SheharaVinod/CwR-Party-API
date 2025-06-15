package lk.cwresports.CwRPartyAPI.Core;

import lk.cwresports.CwRPartyAPI.APIs.Events.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.*;

public class Party {
    private Player owner;
    private final LinkedList<Player> list_of_players = new LinkedList<>();
    private final Set<Player> invited_players = new HashSet<>();
    private boolean isOpened = false;
    private final PartyManager manager;

    public Party(Player owner) {
        this.owner = owner;
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
    public void invite(Player player) {
        // call event
        Event event = new OwnerInviteToPartyEvent(this, player);
        Bukkit.getPluginManager().callEvent(event);

        invited_players.add(player);
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
