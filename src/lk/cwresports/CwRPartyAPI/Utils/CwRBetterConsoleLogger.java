package lk.cwresports.CwRPartyAPI.Utils;

import org.bukkit.Bukkit;

public class CwRBetterConsoleLogger {
    public static void log(String massage) {
        Bukkit.getServer().getConsoleSender().sendMessage(TextStrings.colorize(massage));
    }
}

