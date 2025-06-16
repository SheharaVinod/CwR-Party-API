package lk.cwresports.CwRPartyAPI.APIs.Mechanics.PlayerRemovingTypes;

import lk.cwresports.CwRPartyAPI.Core.Party;
import lk.cwresports.CwRPartyAPI.Core.PartyManager;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public interface RemovingTypes {
    // need LinkedList<Player>
    // need player
    // need manager
    Player getPlayer();

    LinkedList<Player> getMembers();

    void execute(Party party, PartyManager manager);
}
