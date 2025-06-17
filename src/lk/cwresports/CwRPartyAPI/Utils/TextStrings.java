package lk.cwresports.CwRPartyAPI.Utils;

import lk.cwresports.CwRPartyAPI.CwRPartyAPI;
import org.bukkit.ChatColor;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextStrings {
    public static String PLAYER_NOT_ONLINE = "&6%s &7player is not online.";
    public static String PLAYER_HAS_NO_PARTY = "&6%s &7player has no party.";
    public static String YOU_ARE_NOT_IN_A_PARTY = "&7You are not in a party.";
    public static String YOU_ARE_ALREADY_IN_A_PARTY = "&7You are already in a party.";
    public static String YOU_ARE_NOT_INVITED = "&7You have no invitations.";
    public static String YOU_ARE_NOT_THE_OWNER = "&7You are not the owner.";
    public static String PARTY_ALREADY_OPENED = "&7Party is already opened.";
    public static String PARTY_OPENED = "&7Party open for public.";
    public static String PARTY_ALREADY_CLOSED = "&7Party is already closed.";
    public static String PARTY_CLOSED = "&7Party is no longer public.";

    public static String PARTY_CREATED_SUCCESSFULLY = "&7Party created.";
    public static String YOU_HAVE_INVITE = "&7%s invites you to join their party.!";
    public static String WELCOME_JOIN_MASSAGE = "&7You are join %s's party.";
    public static String NEW_MEMBER_JOIN = "&7%s is join party.";
    public static String YOU_ARE_LEFT = "&7You are leaving from the party.";
    public static String MEMBER_LEFT = "&7%s is left the party.";
    public static String WORLD_PLAYER_PARTY_PUBLIC = "&7%s party is open for public.";
    public static String OWNER_OPEN_PARTY = "&7Party is now public.";
    public static String OWNER_CLOSED_PARTY = "&7Party is now private.";
    public static String YOU_ARE_KICKED = "&7You are kicked from the party.";
    public static String PLAYER_KICKED = "&7%s is kicked from the party.";
    public static String PLAYER_PROMOTE = "&7%s is promoted in the party.";
    public static String PLAYER_PROMOTE_AS_NEW_OWNER = "&7You promoted as owner in the party.";
    public static String DISBAND_PARTY = "&7Party was disband.";
    public static String THERE_ARE_NO_PARTY = "&7Currently there are no party here.";
    public static String SEND_INVITATION = "&7You are invite to %s.";

    public static String CLICKABLE_JOIN = "[Join]";
    public static String CLICKABLE_DENY = "[Deny]";


    public static String HOVER_ABLE_ACCEPT_MASSAGE = "Click to accept %s's party invitation";
    public static String HOVER_ABLE_DENY_MASSAGE = "Click to deny %s's party invitation";

    public static String LINE = "&a&m&l-------------------------------------";
    public static String PARTY_LIST = "&a&m&l--------------&a&lParty-List&a&m&l--------------";
    public static String MEMBERS_LIST = "&a&m&l------------&a&lMembers-List&a&m&l--------------";


    public static String[] HELP = {
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
            "   &7/party members - show members",
            "   &7/party list - get party list",
            "   &7/party chat - toggle party chat",
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

    public static List<String> sort(List<String> unsorted_list) {
        // this command sort list with alphabetically and return all.
        final List<String> completions = new ArrayList<>(unsorted_list);
        Collections.sort(completions);
        return completions;
    }

    public static List<String> sort(List<String> unsorted_list, String match_with) {
        return sort(unsorted_list, match_with, false);
    }

    public static List<String> sort(List<String> unsorted_list, String match_with, boolean return_all) {
        // this function return copy of sorted list match with givenLetter.
        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(match_with, unsorted_list, completions);
        return completions;
    }
}
