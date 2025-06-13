package lk.cwresports.CwRPartyAPI;

import lk.cwresports.CwRPartyAPI.Commands.PartyCommand;
import lk.cwresports.CwRPartyAPI.Tabs.PartyTabs;
import lk.cwresports.CwRPartyAPI.Utils.CwRBetterConsoleLogger;
import org.bukkit.plugin.java.JavaPlugin;

public class CwRPartyAPI extends JavaPlugin {
    public static String PREFIX = "&7[&fCwRPartyAPI&7]&r";

    @Override
    public void onEnable() {
        // register commands
        getCommand(PartyCommand.getName()).setExecutor(new PartyCommand());

        // register tab competes
        getCommand(PartyCommand.getName()).setTabCompleter(new PartyTabs());

        CwRBetterConsoleLogger.log("&6------------------------");
        CwRBetterConsoleLogger.log("&6------CwR-Party-API-----");
        CwRBetterConsoleLogger.log("&6------------------------");
    }
}
