package lk.cwresports.CwRPartyAPI.Commands;

import lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated.PlayerJoinPartyEvent;
import lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated.PlayerKickPartyEvent;
import lk.cwresports.CwRPartyAPI.APIs.Events.PlayerRelated.PlayerLeavePartyEvent;
import lk.cwresports.CwRPartyAPI.Core.Party;
import lk.cwresports.CwRPartyAPI.Core.PartyManager;
import lk.cwresports.CwRPartyAPI.Utils.CwRBetterConsoleLogger;
import lk.cwresports.CwRPartyAPI.Utils.TextStrings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class PartyCommand implements CommandExecutor {
    private static final String name = "party";

    public static final String CREATE = "create";
    public static final String JOIN = "join";
    public static final String LEAVE = "leave";
    public static final String HELP = "help";
    public static final String DISBAND = "disband";
    public static final String KICK = "kick";
    public static final String INVITE = "invite";
    public static final String PROMOTE = "promote";

    public static final String[] subCommands = {
            CREATE,
            JOIN,
            LEAVE,
            HELP,
            DISBAND,
            KICK,
            INVITE,
            PROMOTE
    };

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
            }
        }

        return true;
    }

    private void create(Player sender, String[] strings) {
        //party create <player> <player> <player> <player> ...
        if (!PartyManager.getInstance().isInAParty(sender)) {
            Party party = new Party(sender);
            if (strings.length > 1) {
                for (int i = 1; i < strings.length; i++) {
                    try {
                        Player player = Bukkit.getPlayer(strings[i]);
                        party.addPlayer(player);
                    } catch (Exception e) {
                        sender.sendMessage(TextStrings.colorize(TextStrings.PLAYER_NOT_ONLINE.formatted(strings[i])));
                    }
                }
            }
        } else {
            sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_ALREADY_IN_A_PARTY));
        }
    }

    private void invite(Player sender, String[] strings) {
        //party invite <player> <player> <player> ...
        if (PartyManager.getInstance().isInAParty(sender)) {
            Party party = PartyManager.getInstance().getPartyOf(sender);
            if (strings.length > 1) {
                for (int i = 1; i < strings.length; i++) {
                    try {
                        Player player = Bukkit.getPlayer(strings[i]);
                        party.invite(player);
                    } catch (Exception e) {
                        sender.sendMessage(TextStrings.colorize(TextStrings.PLAYER_NOT_ONLINE.formatted(strings[i])));
                    }
                }
            }
        } else {
            sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_NOT_IN_A_PARTY));
        }
    }

    private void join(Player sender, String[] strings) {
        //party join <player>
        Player player;
        try {
            player = Bukkit.getPlayer(strings[1]);
        } catch (Exception e) {
            sender.sendMessage(TextStrings.colorize(TextStrings.PLAYER_NOT_ONLINE.formatted(strings[1])));
            return;
        }

        try {
            if (PartyManager.getInstance().isInAParty(player)) {
                Party party = PartyManager.getInstance().getPartyOf(sender);
                if (party.hasInvite(player)) {
                    party.addPlayer(player);

                    // call event
                    Event event = new PlayerJoinPartyEvent(player, party);
                    Bukkit.getPluginManager().callEvent(event);
                } else {
                    sender.sendMessage(TextStrings.colorize(TextStrings.YOU_ARE_NOT_INVITED));
                }
            } else {
                sender.sendMessage(TextStrings.PLAYER_HAS_NO_PARTY.formatted(player.getName()));
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
                        party.removePlayer(player);

                        // call event
                        Event event = new PlayerKickPartyEvent(player, party);
                        Bukkit.getPluginManager().callEvent(event);
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

            // call event
            Event event = new PlayerLeavePartyEvent(sender, party);
            Bukkit.getServer().getPluginManager().callEvent(event);
            party.removePlayer(sender);
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
        }
    }


    private void help(Player sender, String[] strings) {
        //party help
        for (String massages : TextStrings.help) {
            sender.sendMessage(TextStrings.colorize(massages, false));
        }
    }

    public static String getName() {
        return name;
    }
}
