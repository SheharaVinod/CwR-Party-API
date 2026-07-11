package lk.cwresports.CwRPartyAPI.Expansions;

import lk.cwresports.CwRPartyAPI.Core.Party;
import lk.cwresports.CwRPartyAPI.Core.PartyManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class CwRPartyExpansion extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "cwrparty";
    }

    @Override
    public String getAuthor() {
        return "Mr_Unknown";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (player == null) {
            return null;
        }

        PartyManager manager = PartyManager.getInstance();

        if (!manager.isInAParty(player)) {
            if (params.equalsIgnoreCase("owner")) {
                return "none";
            } else if (params.equalsIgnoreCase("playcount")) {
                return "0";
            }
            return null;
        }

        Party party = manager.getPartyOf(player);

        if (params.equalsIgnoreCase("owner")) {
            return party.getOwner().getName();
        } else if (params.equalsIgnoreCase("playcount")) {
            return String.valueOf(1 + party.getMembers().size());
        }

        return null;
    }
}
