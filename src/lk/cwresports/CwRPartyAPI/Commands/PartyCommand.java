package lk.cwresports.CwRPartyAPI.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return true;
    }

    private void create(Player sender, String[] strings) {
        //party create <player> <player> <player> <player> ...

    }

    private void join(Player sender, String[] strings) {
        //party join <player>
    }

    private void kick(Player sender, String[] strings) {
        //party kick <player>
    }

    private void promote(Player sender, String[] strings) {
        //party promote <player>

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

    }


    private void leave(Player sender, String[] strings) {
        //party leave

    }

    private void open(Player sender, String[] strings) {
        //party open

    }


    private void close(Player sender, String[] strings) {
        //party close

    }


    private void help(Player sender, String[] strings) {
        //party help

    }


}
