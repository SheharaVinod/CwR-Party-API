package lk.cwresports.CwRPartyAPI.Utils;

import lk.cwresports.CwRPartyAPI.CwRPartyAPI;
import org.bukkit.ChatColor;

public class TextStrings {
    public static String PLAYER_NOT_ONLINE = "&6{s} &7player is not online.";
    public static String PLAYER_HAS_NO_PARTY = "&6{s} &7player has no party.";
    public static String YOU_ARE_NOT_IN_A_PARTY = "&7You are not in a party.";
    public static String YOU_ARE_ALREADY_IN_A_PARTY = "&7You are already in a party.";
    public static String YOU_ARE_NOT_INVITED = "&7You have no invitations.";
    public static String YOU_ARE_NOT_THE_OWNER = "&7You are not the owner.";
    public static String PARTY_ALREADY_OPENED = "&7Party is already opened.";
    public static String PARTY_OPENED = "&7Party open for public.";
    public static String PARTY_ALREADY_CLOSED = "&7Party is already closed.";
    public static String PARTY_CLOSED = "&7Party is no longer public.";

    public static String PARTY_CREATED_SUCCESSFULLY = "&7Party created.";
    public static String YOU_HAVE_INVITE = "&7{s} invite you to join there party.";
    public static String WELCOME_JOIN_MASSAGE = "&7You are join {s}'s party.";
    public static String NEW_MEMBER_JOIN = "&7{s} is join party.";
    public static String YOU_ARE_LEFT = "&7You are leaving from the party.";
    public static String MEMBER_LEFT = "&7{s} is left the party.";
    public static String WORLD_PLAYER_PARTY_PUBLIC = "&7{s} party is open for public.";
    public static String OWNER_OPEN_PARTY = "&7Party is now public.";
    public static String OWNER_CLOSED_PARTY = "&7Party is now private.";
    public static String YOU_ARE_KICKED = "&7You are kicked from the party.";
    public static String PLAYER_KICKED = "&7{s} is kicked from the party.";
    public static String PLAYER_PROMOTE = "&7{s} is promoted in the party.";
    public static String PLAYER_PROMOTE_AS_NEW_OWNER = "&7You promoted as owner in the party.";
    public static String DISBAND_PARTY = "&7Party was disband.";


    public static String[] help = {
            "&a&m&l-------------------------------------",
            " &6You can party with you friends,",
            " &6simply create party and invite your friends.",
            "&a&m&l-------------------------------------",
            "   &7/party help - show this massage",
            "   &7/party create <player> <player>...<player> - to create and invite players",
            "   &7/party invite <player> <player>...<player> - invite to players",
            "   &7/party kick <player> <player>...<player> - to kick members",
            "   &7/party leave - leave from party",
            "   &7/party disband - disband the party",
            "   &7/party open - open party for public",
            "   &7/party close - make party private",
            "   &7/party promote <player> - promote player to owner",
            "&a&m&l-------------------------------------"
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
