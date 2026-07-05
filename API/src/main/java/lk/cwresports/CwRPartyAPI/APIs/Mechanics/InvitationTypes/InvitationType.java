package lk.cwresports.CwRPartyAPI.APIs.Mechanics.InvitationTypes;


import lk.cwresports.CwRPartyAPI.Core.Party;
import org.bukkit.entity.Player;

import java.util.Set;


public interface InvitationType {
    // every invitation must expire.
    // every invitation has guest.
    // every invitation has requested party.

    Player getReceivedPlayer();

    Party getRequestedParty();

    long getExpireTimeInTicks();

    boolean hasInvitation(Player player);

    Set<Player> getInvitedPlayers();

    void denied(Player player);
}
