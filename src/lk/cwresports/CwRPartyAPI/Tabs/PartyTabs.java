package lk.cwresports.CwRPartyAPI.Tabs;

import lk.cwresports.CwRPartyAPI.Commands.PartyCommand;
import lk.cwresports.CwRPartyAPI.Core.Party;
import lk.cwresports.CwRPartyAPI.Core.PartyManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PartyTabs implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return List.of();
        }

        if (strings.length == 1) {
            return List.of(PartyCommand.subCommands);
        }

        if (strings.length > 1) {
            if (strings[0].equalsIgnoreCase(PartyCommand.CREATE)) {
                return getWorldPlayers(player);
            } else if (strings[0].equalsIgnoreCase(PartyCommand.INVITE)) {
                return getWorldPlayers(player);
            } else if (strings[0].equalsIgnoreCase(PartyCommand.KICK)) {
                return partyMembers(player);
            } else if (strings[0].equalsIgnoreCase(PartyCommand.JOIN)) {
                return getWorldPlayers(player);
            } else if (strings[0].equalsIgnoreCase(PartyCommand.PROMOTE)) {
                return partyMembers(player);
            }
        }

        return List.of();
    }

    private List<String> getWorldPlayers(Player sender) {
        List<String> nameList = new ArrayList<>();
        List<Player> worldPlayers = sender.getWorld().getPlayers();
        for (Player worldPlayer : worldPlayers) {
            nameList.add(worldPlayer.getName());
        }
        return nameList;
    }

    private List<String> partyMembers(Player sender) {
        if (!PartyManager.getInstance().isInAParty(sender)) {
            return List.of();
        }
        Party party = PartyManager.getInstance().getPartyOf(sender);

        if (!party.isOwner(sender)) {
            return List.of();
        }
        LinkedList<Player> partyMembers = party.getMembers();

        List<String> nameList = new ArrayList<>();
        for (Player worldPlayer : partyMembers) {
            nameList.add(worldPlayer.getName());
        }

        return nameList;
    }
}
