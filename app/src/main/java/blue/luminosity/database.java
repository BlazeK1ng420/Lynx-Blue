package blue.luminosity;

import android.content.SharedPreferences;
import blue.Tools;
import lynx.blue.chat.KikApplication;

public class database {
    String credit = "written by Vince with methods added by Blue";
    String explanation = "stop skidding";

    public static String getKikString(String key) {
        return getKikSP().getString(key, "Off");
    }

    public static void setKikString(String key, String val) {
        getKikSP().edit().putString(key, val).commit();
    }

    public static SharedPreferences getKikSP() {
        return KikApplication.u.aH.getDefaultSharedPrefs();
    }

    private static SharedPreferences getLuminosityPreferences() {
        return Tools.getContext().getSharedPreferences("LuminosityPreferences", 0);
    }

    public static boolean getBoolean(String key, boolean defval) {
        return getLuminosityPreferences().getBoolean(key, defval);
    }

    public static int getInt(String key, int defval) {
        return getLuminosityPreferences().getInt(key, defval);
    }

    public static String getString(String key, String defval) {
        return getLuminosityPreferences().getString(key, defval);
    }

    public static float getFloat(String key, float defval) {
        return getLuminosityPreferences().getFloat(key, defval);
    }

    public static void setBoolean(String key, boolean value) {
        getLuminosityPreferences().edit().putBoolean(key, value).apply();
    }

    public static void setInt(String key, int value) {
        getLuminosityPreferences().edit().putInt(key, value).apply();
    }

    public static void setString(String key, String value) {
        getLuminosityPreferences().edit().putString(key, value).apply();
    }

    public static void setFloat(String key, float value) {
        getLuminosityPreferences().edit().putFloat(key, value).apply();
    }
}
