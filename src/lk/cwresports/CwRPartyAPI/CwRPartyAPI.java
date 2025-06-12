package lk.cwresports.CwRPartyAPI;

import lk.cwresports.CwRPartyAPI.Core.PartyManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CwRPartyAPI extends JavaPlugin {

    @Override
    public void onEnable() {

    }

    public static PartyManager getManager() {
        return PartyManager.getInstance();
    }
}
