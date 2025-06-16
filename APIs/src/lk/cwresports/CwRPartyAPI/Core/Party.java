package lk.cwresports.CwRPartyAPI.Core;

import lk.cwresports.CwRPartyAPI.APIs.Events.PartyRelated.PartyCloseEvent;
import lk.cwresports.CwRPartyAPI.APIs.Events.PartyRelated.PartyDisbandEvent;
import lk.cwresports.CwRPartyAPI.APIs.Events.PartyRelated.PartyOpenEvent;
import lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated.OwnerTryToJoinHisOwnPartyEvent;
import lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated.PlayerCreatePartyEvent;
import lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated.PlayerJoinPartyEvent;
import lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated.PlayerPromotePartyEvent;
import lk.cwresports.CwRPartyAPI.APIs.Mechanics.InvitationTypes.InvitationType;
import lk.cwresports.CwRPartyAPI.APIs.Mechanics.InvitationTypes.JoinToPartyInvitationType;
import lk.cwresports.CwRPartyAPI.APIs.Mechanics.PlayerRemovingTypes.LeftPlayer;
import lk.cwresports.CwRPartyAPI.APIs.Mechanics.PlayerRemovingTypes.RemovingTypes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class Party {
    private Player owner;
    private final LinkedList<Player> list_of_players = new LinkedList<>();

    private boolean isOpened = false;
    private final PartyManager manager;
    private final Plugin plugin;

    private final Map<Player, InvitationType> invitationTypesMap = new HashMap<>();

    public Party(Player owner, Plugin plugin) {
        this.owner = owner;
        this.plugin = plugin;

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

    public Plugin getPlugin() {
        return plugin;
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

    public void invite(Player player, InvitationType to) {
        invitationTypesMap.put(player, to);
    }

    public <T> boolean hasInvite(Player player, Class<T> to) {
        if (invitationTypesMap.containsKey(player)) {
            InvitationType type = invitationTypesMap.get(player);
            if (type != null) {
                if (type.getClass() == to) {
                    return type.hasInvitation(player);
                }
            }
        }
        return false;
    }

    public void denied(Player player) {
        if (invitationTypesMap.containsKey(player)) {
            invitationTypesMap.get(player).denied(player);
        }
    }

    public void disbandParty() {
        // call event
        Event event = new PartyDisbandEvent(this);
        Bukkit.getPluginManager().callEvent(event);

        for (Player member : getMembers()) {
            removePlayer(new LeftPlayer(member, list_of_players));
        }

        invitationTypesMap.clear();
        manager.unregisterPlayer(owner);
    }

    public void addPlayer(Player player) {
        if (player == owner) {
            // call event.
            Event event = new OwnerTryToJoinHisOwnPartyEvent(player, this);
            Bukkit.getPluginManager().callEvent(event);
            return;
        }

        if (isOpened) {
            add_player_If_he_is_not_already_in_the_party_and_not_the_owner(player);
            return;
        }

        if (hasInvite(player, JoinToPartyInvitationType.class)) {
            if (invitationTypesMap.containsKey(player)) {
                Set<Player> invitedPlayers = invitationTypesMap.get(player).getInvitedPlayers();
                if (invitedPlayers.contains(player)) {
                    add_player_If_he_is_not_already_in_the_party_and_not_the_owner(player);
                    invitedPlayers.remove(player);

                    // call event
                    Event event = new PlayerJoinPartyEvent(player, this);
                    Bukkit.getPluginManager().callEvent(event);
                }
            }
        }
    }

    public void removePlayer(RemovingTypes type) {
        type.execute(this, this.manager);
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
