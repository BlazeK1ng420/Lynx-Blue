package blue.luminosity.perchat;

import blue.luminosity.database;

public class PerChat {
    public static String changeKey(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        return sb.toString();
    }

    public static String changeKeyIfEnabled(String str, String str2) {
        return database.getBoolean(changeKey("blue.perchat", str2), false) ? changeKey(str, str2) : str;
    }
}
