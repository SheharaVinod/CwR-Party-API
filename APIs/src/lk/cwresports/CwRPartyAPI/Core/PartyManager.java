package lk.cwresports.CwRPartyAPI.Core;

import org.bukkit.entity.Player;

import java.util.HashMap;
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
