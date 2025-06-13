package lk.cwresports.CwRPartyAPI.Utils;

import lk.cwresports.CwRPartyAPI.CwRPartyAPI;
import org.bukkit.ChatColor;

public class TextStrings {
    public static String PLAYER_NOT_ONLINE = "{s} player is not online.";
    public static String PLAYER_HAS_NO_PARTY = "{s} player has no party.";
    public static String YOU_ARE_NOT_IN_A_PARTY = "You are not in a party.";
    public static String YOU_ARE_NOT_INVITED = "You have no invitations.";
    public static String YOU_ARE_NOT_THE_OWNER = "You are not the owner.";
    public static String PARTY_ALREADY_OPENED = "Party is already opened.";
    public static String PARTY_OPENED = "Party open for public.";
    public static String PARTY_ALREADY_CLOSED = "Party is already closed.";
    public static String PARTY_CLOSED = "Party is no longer public.";

    public static String[] help = {
            "-------------------------------------",
            " You can party with you friends,",
            " simply create party and invite your friends.",
            "-------------------------------------",
            "   /party help - show this massage",
            "   /party create <player> <player>...<player> - to create and invite players",
            "   /party invite <player> <player>...<player> - to invite players",
            "   /party kick <player> <player>...<player> - to kick players",
            "   /party leave - leave from party",
            "   /party disband - disband the party",
            "   /party open - open party for public",
            "   /party close - make party private",
            "   /party promote <player> - promote player to owner",
            "-------------------------------------"
    };


    public static String colorize(String massage) {
        return colorize(massage, true);
    }

    public static String colorize(String massage, boolean with_prefix) {
        if (with_prefix) {
            return colorize(CwRPartyAPI.PREFIX, false) + " " + colorize(massage, false);
        }
        return ChatColor.translateAlternateColorCodes('&', massage);
    }
}
