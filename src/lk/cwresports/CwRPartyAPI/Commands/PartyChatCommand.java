package lk.cwresports.CwRPartyAPI.Commands;

import lk.cwresports.CwRPartyAPI.Core.PartyManager;
import lk.cwresports.CwRPartyAPI.Utils.TextStrings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class PartyChatCommand implements CommandExecutor {
    private static final String name = "party-chat";
    private static final Set<Player> toggledPlayer = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return true;
        }

        if (!PartyManager.getInstance().isInAParty(player)) {
            return true;
        }

        togglePartyChat(player);
        return true;
    }

    public static void togglePartyChat(Player player) {
        if (isInPartyChat(player)) {
            leavePartyChat(player);
        } else {
            joinPartyChat(player);
        }
    }

    public static void joinPartyChat(Player player) {
        toggledPlayer.add(player);
        player.sendMessage(TextStrings.colorize("&7You are now in party chat."));
    }

    public static void leavePartyChat(Player player) {
        toggledPlayer.remove(player);
        player.sendMessage(TextStrings.colorize("&7You are no longer in party chat."));
    }

    public static boolean isInPartyChat(Player player) {
        return toggledPlayer.contains(player);
    }


    public static String getName() {
        return name;
    }
}
