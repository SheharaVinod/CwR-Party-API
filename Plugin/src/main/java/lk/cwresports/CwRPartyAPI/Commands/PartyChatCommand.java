package lk.cwresports.CwRPartyAPI.Commands;

import lk.cwresports.CwRPartyAPI.Core.Party;
import lk.cwresports.CwRPartyAPI.Core.PartyChat;
import lk.cwresports.CwRPartyAPI.Core.PartyManager;
import lk.cwresports.CwRPartyAPI.Utils.TextStrings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyChatCommand implements CommandExecutor {
    private static final String name = "party-chat";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            return true;
        }
        Player player = (Player) commandSender;

        if (!PartyManager.getInstance().isInAParty(player)) {
            player.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_NOT_IN_A_PARTY));
            return true;
        }

        Party party = PartyManager.getInstance().getPartyOf(player);

        if (strings.length > 0) {
            String message = String.join(" ", strings);
            party.getPartyChat().sendMessage(player, message);
            return true;
        }

        togglePartyChat(player);
        return true;
    }

    public static void togglePartyChat(Player player) {
        if (!PartyManager.getInstance().isInAParty(player)) return;
        PartyChat partyChat = PartyManager.getInstance().getPartyOf(player).getPartyChat();
        partyChat.toggle(player);
        if (partyChat.isToggled(player)) {
            player.sendMessage(TextStrings.colorize("&7You are now in party chat."));
        } else {
            player.sendMessage(TextStrings.colorize("&7You are no longer in party chat."));
        }
    }

    public static boolean isInPartyChat(Player player) {
        if (!PartyManager.getInstance().isInAParty(player)) return false;
        return PartyManager.getInstance().getPartyOf(player).getPartyChat().isToggled(player);
    }

    public static String getName() {
        return name;
    }
}
