package lk.cwresports.CwRPartyAPI.Core;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartyManager {
    private static PartyManager instance;
    private final Map<Player, Party> playerPartyMap = new HashMap<>();
    private final Map<Party, Party> partyPartyMap = new HashMap<>();


    public boolean isInAParty(Player player) {
        return playerPartyMap.containsKey(player);
    }

    public Party getPartyOf(Player player) {
        return playerPartyMap.get(player);
    }

    public List<String> getMembersOf(Party party) {
        List<String> names = new ArrayList<>();
        for (Player player : party.getMembers()) {
            names.add(player.getName());
        }
        return names;
    }

    public List<String> getAllMembersOf(Party party) {
        List<String> names = new ArrayList<>();
        for (Player player : party.getCopyOfAllMembersIncludingTheOwner()) {
            names.add(player.getName());
        }
        return names;
    }

    public List<String> getPartyList() {
        List<String> names = new ArrayList<>();
        for (Player player : playerPartyMap.keySet()) {
            names.add(player.getName());
        }
        return names;
    }

    public static PartyManager getInstance() {
        if (instance == null) {
            instance = new PartyManager();
        }
        return instance;
    }


    public void registerPlayer(Player player, Party party) {
        playerPartyMap.put(player, party);
    }

    public void unregisterPlayer(Player player) {
        playerPartyMap.remove(player);
    }
}
