package lk.cwresports.CwRPartyAPI;

import lk.cwresports.CwRPartyAPI.Commands.PartyChatCommand;
import lk.cwresports.CwRPartyAPI.Commands.PartyCommand;
import lk.cwresports.CwRPartyAPI.Listeners.PartyChatEventListener;
import lk.cwresports.CwRPartyAPI.Listeners.PartyEventListener;
import lk.cwresports.CwRPartyAPI.Tabs.PartyTabs;
import lk.cwresports.CwRPartyAPI.Utils.ConfigUtils;
import lk.cwresports.CwRPartyAPI.Utils.CwRBetterConsoleLogger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CwRPartyAPI extends JavaPlugin {
    public static String PREFIX = "&7[&fCwRPartyAPI&7]&r";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ConfigUtils.reload(this);

        // register commands
        getCommand(PartyCommand.getName()).setExecutor(new PartyCommand(this));
        getCommand(PartyChatCommand.getName()).setExecutor(new PartyChatCommand());

        // register tab competes
        getCommand(PartyCommand.getName()).setTabCompleter(new PartyTabs());

        // register listeners
        Bukkit.getPluginManager().registerEvents(new PartyEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PartyChatEventListener(), this);

        CwRBetterConsoleLogger.log("&6------------------------");
        CwRBetterConsoleLogger.log("&6------CwR-Party-API-----");
        CwRBetterConsoleLogger.log("&6------------------------");
    }
}
