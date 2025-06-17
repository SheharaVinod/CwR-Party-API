package lk.cwresports.CwRPartyAPI.Utils;

import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public class ConfigUtils {
    public static String PLAYER_NOT_ONLINE = "TextStrings.PLAYER_NOT_ONLINE";
    public static String PLAYER_HAS_NO_PARTY = "TextStrings.PLAYER_HAS_NO_PARTY";
    public static String YOU_ARE_NOT_IN_A_PARTY = "TextStrings.YOU_ARE_NOT_IN_A_PARTY";
    public static String YOU_ARE_ALREADY_IN_A_PARTY = "TextStrings.YOU_ARE_ALREADY_IN_A_PARTY";
    public static String YOU_ARE_NOT_INVITED = "TextStrings.YOU_ARE_NOT_INVITED";
    public static String YOU_ARE_NOT_THE_OWNER = "TextStrings.YOU_ARE_NOT_THE_OWNER";
    public static String PARTY_ALREADY_OPENED = "TextStrings.PARTY_ALREADY_OPENED";
    public static String PARTY_OPENED = "TextStrings.PARTY_OPENED";
    public static String PARTY_ALREADY_CLOSED = "TextStrings.PARTY_ALREADY_CLOSED";
    public static String PARTY_CLOSED = "TextStrings.PARTY_CLOSED";

    public static String PARTY_CREATED_SUCCESSFULLY = "TextStrings.PARTY_CREATED_SUCCESSFULLY";
    public static String YOU_HAVE_INVITE = "TextStrings.YOU_HAVE_INVITE";
    public static String WELCOME_JOIN_MASSAGE = "TextStrings.WELCOME_JOIN_MASSAGE";
    public static String NEW_MEMBER_JOIN = "TextStrings.NEW_MEMBER_JOIN";
    public static String YOU_ARE_LEFT = "TextStrings.YOU_ARE_LEFT";
    public static String MEMBER_LEFT = "TextStrings.MEMBER_LEFT";
    public static String WORLD_PLAYER_PARTY_PUBLIC = "TextStrings.WORLD_PLAYER_PARTY_PUBLIC";
    public static String OWNER_OPEN_PARTY = "TextStrings.OWNER_OPEN_PARTY";
    public static String OWNER_CLOSED_PARTY = "TextStrings.OWNER_CLOSED_PARTY";
    public static String YOU_ARE_KICKED = "TextStrings.YOU_ARE_KICKED";
    public static String PLAYER_KICKED = "TextStrings.PLAYER_KICKED";
    public static String PLAYER_PROMOTE = "TextStrings.PLAYER_PROMOTE";
    public static String PLAYER_PROMOTE_AS_NEW_OWNER = "TextStrings.PLAYER_PROMOTE_AS_NEW_OWNER";
    public static String DISBAND_PARTY = "TextStrings.DISBAND_PARTY";
    public static String THERE_ARE_NO_PARTY = "TextStrings.THERE_ARE_NO_PARTY";
    public static String SEND_INVITATION = "TextStrings.SEND_INVITATION";

    public static String CLICKABLE_JOIN = "TextStrings.CLICKABLE_JOIN";
    public static String CLICKABLE_DENY = "TextStrings.CLICKABLE_DENY";


    public static String HOVER_ABLE_ACCEPT_MASSAGE = "TextStrings.HOVER_ABLE_ACCEPT_MASSAGE";
    public static String HOVER_ABLE_DENY_MASSAGE = "TextStrings.HOVER_ABLE_DENY_MASSAGE";

    public static String LINE = "TextStrings.LINE";
    public static String PARTY_LIST = "TextStrings.PARTY_LIST";
    public static String MEMBERS_LIST = "TextStrings.MEMBERS_LIST";


    public static String HELP = "TextStrings.HELP";

    public static void reload(Plugin plugin) {
        for (Field configField : ConfigUtils.class.getDeclaredFields()) {
            if (!Modifier.isStatic(configField.getModifiers())) continue;
            if (!configField.getType().equals(String.class)) continue;

            String configPath;
            try {
                configPath = (String) configField.get(null);
            } catch (IllegalAccessException e) {
                continue;
            }

            Field textStringsField = null;
            for (Field f : TextStrings.class.getDeclaredFields()) {
                if (f.getName().equalsIgnoreCase(configField.getName())) {
                    textStringsField = f;
                    break;
                }
            }
            if (textStringsField == null) continue;

            try {
                textStringsField.setAccessible(true);
                Class<?> fieldType = textStringsField.getType();
                Object configValue = plugin.getConfig().get(configPath);

                if (configValue == null) continue;

                if (fieldType.isArray() && fieldType.getComponentType() == String.class) {
                    if (configValue instanceof List<?> list) {
                        String[] array = new String[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            array[i] = list.get(i).toString();
                        }
                        textStringsField.set(null, array);
                    }
                }

                else if (fieldType == String.class) {
                    textStringsField.set(null, configValue.toString());
                }
            } catch (IllegalAccessException ignored) {
            }
        }
    }
}
