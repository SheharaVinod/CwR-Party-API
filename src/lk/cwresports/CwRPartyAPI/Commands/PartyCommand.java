package lk.cwresports.CwRPartyAPI.Commands;

import lk.cwresports.CwRPartyAPI.APIs.Mechanics.InvitationTypes.JoinToPartyInvitationType;
import lk.cwresports.CwRPartyAPI.APIs.Mechanics.PlayerRemovingTypes.KickPlayer;
import lk.cwresports.CwRPartyAPI.APIs.Mechanics.PlayerRemovingTypes.LeftPlayer;
import lk.cwresports.CwRPartyAPI.Core.Party;
import lk.cwresports.CwRPartyAPI.Core.PartyManager;
import lk.cwresports.CwRPartyAPI.Utils.CwRBetterConsoleLogger;
import lk.cwresports.CwRPartyAPI.Utils.TextStrings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class PartyCommand implements CommandExecutor {
    private final Plugin plugin;
    private static final String name = "party";

    public static final String CREATE = "create";
    public static final String JOIN = "join";
    public static final String LEAVE = "leave";
    public static final String HELP = "help";
    public static final String DISBAND = "disband";
    public static final String KICK = "kick";
    public static final String INVITE = "invite";
    public static final String PROMOTE = "promote";
    public static final String OPEN = "open";
    public static final String CLOSE = "close";
    public static final String MEMBERS = "members";
    public static final String LIST = "list";

    public static final String[] subCommands = {
            CREATE,
            JOIN,
            LEAVE,
            HELP,
            DISBAND,
            KICK,
            INVITE,
            PROMOTE,
            OPEN,
            CLOSE,
            MEMBERS,
            LIST
    };

    public PartyCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player sender)) {
            CwRBetterConsoleLogger.log("This command can be only executed by a player.");
            return true;
        }

        if (strings.length == 0) {
            help(sender, strings);
        }

        if (strings.length > 0) {
            if (strings[0].equalsIgnoreCase(CREATE)) {
                create(sender, strings);
            } else if (strings[0].equalsIgnoreCase(JOIN)) {
                join(sender, strings);
            } else if (strings[0].equalsIgnoreCase(LEAVE)) {
                leave(sender, strings);
            } else if (strings[0].equalsIgnoreCase(KICK)) {
                kick(sender, strings);
            } else if (strings[0].equalsIgnoreCase(INVITE)) {
                invite(sender, strings);
            } else if (strings[0].equalsIgnoreCase(PROMOTE)) {
                promote(sender, strings);
            } else if (strings[0].equalsIgnoreCase(DISBAND)) {
                disband(sender, strings);
            } else if (strings[0].equalsIgnoreCase(HELP)) {
                help(sender, strings);
            } else if (strings[0].equalsIgnoreCase(OPEN)) {
                open(sender, strings);
            } else if (strings[0].equalsIgnoreCase(CLOSE)) {
                close(sender, strings);
            } else if (strings[0].equalsIgnoreCase(MEMBERS)) {
                members(sender, strings);
            } else if (strings[0].equalsIgnoreCase(LIST)) {
                list(sender, strings);
            }
        }

        return true;
    }

    private void create(Player sender, String[] strings) {
        //party create <player> <player> <player> <player> ...
        if (!PartyManager.getInstance().isInAParty(sender)) {
            Party party = new Party(sender, plugin);
            invite(strings, sender, party);
        } else {
            sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_ALREADY_IN_A_PARTY));
        }
    }

    private void invite(Player sender, String[] strings) {
        //party invite <player> <player> <player> ...
        if (PartyManager.getInstance().isInAParty(sender)) {
            Party party = PartyManager.getInstance().getPartyOf(sender);
            invite(strings, sender, party);
        } else {
            sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_NOT_IN_A_PARTY));
        }
    }

    private void invite(String[] strings, Player owner, Party party) {
        if (strings.length > 0) {
            for (int i = 1; i < strings.length; i++) {
                try {
                    Player player = Bukkit.getPlayer(strings[i]);

                    if (player == null) {
                        owner.sendMessage(TextStrings.colorize(TextStrings.PLAYER_NOT_ONLINE.formatted(strings[i])));
                        continue;
                    }

                    party.invite(player, new JoinToPartyInvitationType(party, player, 20 * 60));
                } catch (Exception e) {
                    owner.sendMessage(TextStrings.colorize(TextStrings.PLAYER_NOT_ONLINE.formatted(strings[i])));
                }
            }
        }
    }

    private void join(Player sender, String[] strings) {
        //party join <partyOwner>
        if (PartyManager.getInstance().isInAParty(sender)) {
            sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_ALREADY_IN_A_PARTY));
            return;
        }

        Player partyOwner;
        try {
            partyOwner = Bukkit.getPlayer(strings[1]);
        } catch (Exception e) {
            sender.sendMessage(TextStrings.colorize(TextStrings.PLAYER_NOT_ONLINE.formatted(strings[1])));
            return;
        }

        try {
            if (PartyManager.getInstance().isInAParty(partyOwner)) {
                Party party = PartyManager.getInstance().getPartyOf(partyOwner);

                if (party.hasInvite(sender, JoinToPartyInvitationType.class)) {
                    party.addPlayer(sender);
                } else {
                    sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_NOT_INVITED));
                }
            } else {
                sender.sendMessage(TextStrings.colorize(TextStrings.PLAYER_HAS_NO_PARTY.formatted(partyOwner.getName())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void kick(Player sender, String[] strings) {
        //party kick <player> <player> <player>
        if (PartyManager.getInstance().isInAParty(sender)) {
            Party party = PartyManager.getInstance().getPartyOf(sender);
            if (strings.length > 1) {
                for (int i = 1; i < strings.length; i++) {
                    try {
                        Player player = Bukkit.getPlayer(strings[i]);
                        party.removePlayer(new KickPlayer(player, party.getMembers()));

                    } catch (Exception e) {
                        sender.sendMessage(TextStrings.colorize(TextStrings.PLAYER_NOT_ONLINE.formatted(strings[i])));
                    }
                }
            }
        } else {
            sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_NOT_IN_A_PARTY));
        }
    }

    private void promote(Player sender, String[] strings) {
        //party promote <player>
        if (PartyManager.getInstance().isInAParty(sender)) {
            Party party = PartyManager.getInstance().getPartyOf(sender);
            if (strings.length > 1) {
                Player newOwner = null;
                try {
                    newOwner = Bukkit.getPlayer(strings[1]);
                } catch (Exception e) {
                    sender.sendMessage(TextStrings.colorize(TextStrings.PLAYER_NOT_ONLINE.formatted(strings[1])));
                }
                if (newOwner != null) {
                    party.promote(newOwner);
                }
            }
        } else {
            sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_NOT_IN_A_PARTY));
        }
    }

    private void merge(Player sender, String[] strings) {
        //party merge <player>

    }

    private void accept(Player sender, String[] strings) {
        //party accept

    }

    private void denied(Player sender, String[] strings) {
        //party denied

    }

    private void disband(Player sender, String[] strings) {
        //party disband
        if (PartyManager.getInstance().isInAParty(sender)) {
            Party party = PartyManager.getInstance().getPartyOf(sender);
            if (party.isOwner(sender)) {
                party.disbandParty();
            }
        } else {
            sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_NOT_IN_A_PARTY));
        }
    }


    private void leave(Player sender, String[] strings) {
        //party leave
        if (PartyManager.getInstance().isInAParty(sender)) {
            Party party = PartyManager.getInstance().getPartyOf(sender);
            if (party.isOwner(sender)) {
                party.disbandParty();
                return;
            }

            party.removePlayer(new LeftPlayer(sender, party.getMembers()));
        } else {
            sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_NOT_IN_A_PARTY));
        }
    }

    private void open(Player sender, String[] strings) {
        //party open
        if (PartyManager.getInstance().isInAParty(sender)) {
            Party party = PartyManager.getInstance().getPartyOf(sender);
            if (!party.isOwner(sender)) {
                sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_NOT_THE_OWNER));
                return;
            }

            if (party.isOpened()) {
                sender.sendMessage(TextStrings.colorize(TextStrings.PARTY_ALREADY_OPENED));
                return;
            }

            party.open();
            sender.sendMessage(TextStrings.colorize(TextStrings.PARTY_OPENED));
        } else {
            sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_NOT_IN_A_PARTY));
        }
    }


    private void close(Player sender, String[] strings) {
        //party close
        if (PartyManager.getInstance().isInAParty(sender)) {
            Party party = PartyManager.getInstance().getPartyOf(sender);
            if (!party.isOwner(sender)) {
                sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_NOT_THE_OWNER));
                return;
            }

            if (!party.isOpened()) {
                sender.sendMessage(TextStrings.colorize(TextStrings.PARTY_ALREADY_CLOSED));
                return;
            }

            party.closed();
            sender.sendMessage(TextStrings.colorize(TextStrings.PARTY_CLOSED));
        } else {
            sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_NOT_IN_A_PARTY));
        }
    }


    private void help(Player sender, String[] strings) {
        //party help
        for (String massages : TextStrings.HELP) {
            sender.sendMessage(TextStrings.colorize(massages, false));
        }
    }

    public void members(Player sender, String[] strings) {
        //party members
        PartyManager manager = PartyManager.getInstance();
        boolean inAParty = manager.isInAParty(sender);
        if (inAParty) {
            Party party = manager.getPartyOf(sender);
            List<String> allMembers = manager.getAllMembersOf(party);

            sender.sendMessage(TextStrings.colorize(TextStrings.MEMBERS_LIST, false));

            for (String member : allMembers) {
                sender.sendMessage(TextStrings.colorize("  &6- &7" + member, false));
            }
            sender.sendMessage(TextStrings.colorize(TextStrings.LINE, false));
        } else {
            sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_NOT_IN_A_PARTY));
        }
    }


    public void list(Player sender, String[] strings) {
        //party list
        PartyManager manager = PartyManager.getInstance();
        List<String> partyList = manager.getPartyList();

        sender.sendMessage(TextStrings.colorize(TextStrings.PARTY_LIST, false));


        if (partyList.isEmpty()) {
            sender.sendMessage(TextStrings.colorize(TextStrings.THERE_ARE_NO_PARTY));
            sender.sendMessage(TextStrings.colorize(TextStrings.LINE, false));
            return;
        }

        for (String member : partyList) {
            sender.sendMessage(TextStrings.colorize(member));
        }
        sender.sendMessage(TextStrings.colorize(TextStrings.LINE, false));
    }

    public static String getName() {
        return name;
    }
}
