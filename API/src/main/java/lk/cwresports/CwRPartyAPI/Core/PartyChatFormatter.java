package lk.cwresports.CwRPartyAPI.Core;

import org.bukkit.ChatColor;

public class PartyChatFormatter {
    public static final String DEFAULT_FORMAT = "&a%player_name% &f&l-&r &e{message}";
    private static String currentFormat = DEFAULT_FORMAT;

    private String format;

    public PartyChatFormatter(String format) {
        this.format = format;
    }

    public PartyChatFormatter() {
        this(currentFormat);
    }

    public static void setCurrentFormat(String format) {
        currentFormat = format;
    }

    public String format(String playerName, String message) {
        String stripped = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message));
        return ChatColor.translateAlternateColorCodes('&',
                format.replace("%player_name%", playerName).replace("{message}", stripped));
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
