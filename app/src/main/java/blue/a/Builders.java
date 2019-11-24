package blue.a;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import blue.KikTools;
import blue.Toast;
import blue.Tools;
import blue.luminosity.database;
import blue.luminosity.executables;

public class Builders {
    public static String temp;

    public static void addCensorRageCommand(String str) {
        String str2;
        if (!Tools.adminCheck(KikTools.chatJID)) {
            str2 = "I have to be admin in order to censor a word";
        } else {
            String lowerCase = str.toLowerCase();
            if (lowerCase.contains(",")) {
                str2 = "Commas are not allowed when setting a censored word";
            } else if (lowerCase.contains("\n")) {
                str2 = "Phrase too long to censor";
            } else if (isWhitelisted(lowerCase)) {
                str2 = "You cannot censor default commands";
            } else if (lowerCase.codePointCount(0, lowerCase.length()) > 32) {
                str2 = "Phrase too long to censor";
            } else {
                String censorListCommand = getCensorListCommand();
                if (Group.isCensored(lowerCase, censorListCommand)) {
                    str2 = "Word already on censor list";
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(censorListCommand);
                    sb.append(",");
                    sb.append(lowerCase);
                    setCensorCommand(sb.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("'");
                    sb2.append(lowerCase);
                    sb2.append("' added to censor list");
                    str2 = sb2.toString();
                }
            }
        }
        Toast.toast(str2);
    }

    public static String addTrigger(String xmpp) {
        String[] split = Tools.getBody(xmpp).split("&gt;");
        if (split.length > 2) {
            return "You cannot add multiple triggers at once";
        }
        String lowerCase = split[0].trim().toLowerCase();
        if (isWhitelisted(lowerCase)) {
            return "You cannot use default commands as triggers";
        }
        if (Tools.triggerDBCheck(Tools.getGroupJID(xmpp), lowerCase)) {
            return "Trigger already exists";
        }
        String trim = split[1].trim();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO BlueTable (group_jid, trigger, response) VALUES ('");
        sb.append(Tools.getGroupJID(xmpp));
        sb.append("', '");
        sb.append(lowerCase);
        sb.append("', '");
        sb.append(trim);
        sb.append("');");
        Tools.execSQL(Tools.buildCoreID("kikDatabase.db"), sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("You say ");
        sb2.append(lowerCase);
        sb2.append(", I say ");
        sb2.append(trim);
        return sb2.toString();
    }

    public static void addTriggerCommand(String xmpp) {
        String str;
        if (!xmpp.contains(" > ")) {
            str = "Incorrect format. Add triggers by using Trigger > Response";
        } else {
            String[] split = xmpp.split(">");
            if (split.length > 2) {
                str = "You cannot add multiple triggers at once";
            } else {
                String lowerCase = split[0].trim().toLowerCase();
                if (isWhitelisted(lowerCase)) {
                    str = "You cannot use default commands as triggers";
                } else if (Tools.triggerDBCheck(KikTools.chatJID, lowerCase)) {
                    str = "Trigger already exists";
                } else {
                    String trim = split[1].trim();
                    StringBuilder sb = new StringBuilder();
                    sb.append("INSERT INTO BlueTable (group_jid, trigger, response) VALUES ('");
                    sb.append(KikTools.chatJID);
                    sb.append("', '");
                    sb.append(lowerCase);
                    sb.append("', '");
                    sb.append(trim);
                    sb.append("');");
                    Tools.execSQL(Tools.buildCoreID("kikDatabase.db"), sb.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("You say ");
                    sb2.append(lowerCase);
                    sb2.append(", bot says ");
                    sb2.append(trim);
                    str = sb2.toString();
                }
            }
        }
        Toast.toast(str);
    }

    public static String buildCensor(String str) {
        return "censor." + Tools.getGroupJID(str);
    }

    public static String buildLeaveRage(String str) {
        return executables.getLeave("leave." + Tools.getGroupJID(str));
    }

    public static String buildLeaveXMPP(String str) {
        return executables.getLeave("leave." + Tools.getSysJID(str));
    }

    public static String buildLockNameRage(String str) {
        return executables.getLockName("gn." + Tools.getGroupJID(str));
    }

    public static String buildLockNameXMPP(String str) {
        return executables.getLockName("gn." + Tools.getSysJID(str));
    }

    public static String buildLockRage(String str) {
        return executables.getLock("lock." + Tools.getGroupJID(str));
    }

    public static String buildLockXMPP(String str) {
        return executables.getLock("lock." + Tools.getSysJID(str));
    }

    public static String buildWelcomeRage(String str) {
        return executables.getWelcome("welcome." + Tools.getGroupJID(str));
    }

    public static String buildWelcomeXMPP(String str) {
        return executables.getWelcome("welcome." + Tools.getSysJID(str));
    }

    public static void clearCensorList(String str) {
        if (!Tools.rageAdminCheck(str)) {
            String str2 = "Only admins can delete all triggers";
            return;
        }
        database.setString("censor." + Tools.getGroupJID(str), null);
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM BlueTable WHERE group_jid = '");
        sb.append(Tools.getGroupJID(str));
        sb.append("';");
        Tools.execSQL(Tools.buildCoreID("kikDatabase.db"), sb.toString());
    }

    public static void clearCensorListCommand() {
        database.setString("censor." + KikTools.chatJID, null);
    }

    public static void clearTriggerListCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM BlueTable WHERE group_jid = '");
        sb.append(KikTools.chatJID);
        sb.append("';");
        Tools.execSQL(Tools.buildCoreID("kikDatabase.db"), sb.toString());
    }

    public static boolean deleteCensor(String xmpp, String body) {
        if (!Group.isCensored(body, getCensorList(xmpp))) {
            return false;
        }
        database.setString(buildCensor(xmpp), removeCensorFromList(body, getCensorList(xmpp)));
        return true;
    }

    public static void deleteCensorCommand(String body) {
        String str;
        String body2 = body.toLowerCase();
        if (!Group.isCensored(body2, getCensorListCommand())) {
            str = "Censor doesn't exist";
        } else {
            setCensorCommand(removeCensorFromList(body2, getCensorListCommand()));
            str = "Censor deleted";
        }
        Toast.toast(str);
    }

    public static String deleteTrigger(String xmpp, String str) {
        String xmpp2 = xmpp.toLowerCase();
        if (!Tools.rageAdminCheck(xmpp2)) {
            return "Only admins can delete triggers";
        }
        String trim = str.substring(7).trim();
        if (!Tools.triggerDBCheck(Tools.getGroupJID(xmpp2), trim)) {
            return "Trigger doesn't exist";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM BlueTable WHERE group_jid = '");
        sb.append(Tools.getGroupJID(xmpp2));
        sb.append("' AND trigger = '");
        sb.append(trim);
        sb.append("';");
        Tools.execSQL(Tools.buildCoreID("kikDatabase.db"), sb.toString());
        return "Ok";
    }

    public static void deleteTriggerCommand(String xmpp) {
        String str;
        String xmpp2 = xmpp.trim().toLowerCase();
        if (!Tools.triggerDBCheck(KikTools.chatJID, xmpp2)) {
            str = "Trigger doesn't exist";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM BlueTable WHERE group_jid = '");
            sb.append(KikTools.chatJID);
            sb.append("' AND trigger = '");
            sb.append(xmpp2);
            sb.append("';");
            Tools.execSQL(Tools.buildCoreID("kikDatabase.db"), sb.toString());
            str = "Trigger deleted";
        }
        Toast.toast(str);
    }

    public static boolean getCensor(String str, String str2) {
        if (!Group.isCensored(str2, getCensorList(str))) {
            return false;
        }
        String groupJID = Tools.getGroupJID(str);
        if (!Tools.adminCheck(groupJID) || Tools.rageAdminCheck(str)) {
            return false;
        }
        Tools.send(groupJID, "Removing for saying a censored word");
        Group.remove(str);
        return true;
    }

    public static String getCensorList(String str) {
        return database.getString("censor." + Tools.getGroupJID(str), null);
    }

    public static String getCensorListCommand() {
        return database.getString("censor." + KikTools.chatJID, null);
    }

    public static boolean lockGroup(String xmpp) {
        String str = "lock." + Tools.getGroupJID(xmpp);
        if (!"off".contains(executables.getLock(str))) {
            return false;
        }
        database.setString(str, "On (remove)");
        return true;
    }

    public static void setCensorCommand(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("censor.");
        sb.append(KikTools.chatJID);
        database.setString(sb.toString(), str);
    }

    public static String setCensorRage(String str, String str2) {
        if (!Tools.adminCheck(Tools.getGroupJID(str))) {
            return "I have to be admin in order to censor a word";
        }
        if (!Tools.rageAdminCheck(str)) {
            return "Only admins can add censored words";
        }
        String lowerCase = str2.substring(7).toLowerCase();
        if (lowerCase.contains(",")) {
            return "Commas are not allowed when setting a censored word";
        }
        if (lowerCase.contains("\n")) {
            return "Phrase too long to censor";
        }
        if (isWhitelisted(lowerCase)) {
            return "You cannot censor default commands";
        }
        if (str.codePointCount(0, lowerCase.length()) > 32) {
            return "Phrase too long to censor";
        }
        String censorList = getCensorList(str);
        if (Group.isCensored(lowerCase, censorList)) {
            return "Word already on censor list";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(censorList);
        sb.append(",");
        sb.append(lowerCase);
        database.setString(buildCensor(str), sb.toString());
        return "'" + lowerCase + "' added to censor list";
    }

    public static boolean unlockGroup(String xmpp) {
        String str = "lock." + Tools.getGroupJID(xmpp);
        if ("off".contains(executables.getLock(str))) {
            return false;
        }
        database.setString(str, "off");
        return true;
    }

    public static boolean getTrigger(String xmpp, String str) {
        try {
            String groupJID = Tools.getGroupJID(xmpp);
            SQLiteDatabase openOrCreateDatabase = Tools.getContext().openOrCreateDatabase(Tools.buildCoreID("kikDatabase.db"), 0, null);
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT response FROM BlueTable WHERE group_jid = '");
            sb.append(groupJID);
            sb.append("' AND trigger = '");
            sb.append(str);
            sb.append("';");
            Cursor rawQuery = openOrCreateDatabase.rawQuery(sb.toString(), null);
            rawQuery.moveToFirst();
            Tools.send(groupJID, rawQuery.getString(0));
            rawQuery.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isWhitelisted(String body) {
        return body.contains("ping") || body.contains("triggers") || body.contains("null") || body.contains("setting") || body.contains("urban") || body.contains("censor") || body.contains("->") || body.contains("-") || body.contains("delete") || body.contains("rules") || body.contains("leave") || body.contains("welcome");
    }

    public static String getTriggerList(String xmpp) {
        try {
            Cursor rawQuery = Tools.getContext().openOrCreateDatabase(Tools.buildCoreID("kikDatabase.db"), 0, null).rawQuery("SELECT trigger FROM BlueTable WHERE group_jid = '" + Tools.getGroupJID(xmpp) + "';", null);
            StringBuilder sb = new StringBuilder();
            int count = rawQuery.getCount();
            if (count == 0) {
                return "No triggers yet!";
            }
            for (int i = 0; i < count; i++) {
                rawQuery.moveToNext();
                sb.append("\n-");
                sb.append(rawQuery.getString(0));
            }
            String sb2 = sb.toString();
            rawQuery.close();
            return sb2;
        } catch (Exception e) {
            return "error";
        }
    }

    public static String getTriggerListCommand() {
        try {
            Cursor rawQuery = Tools.getContext().openOrCreateDatabase(Tools.buildCoreID("kikDatabase.db"), 0, null).rawQuery("SELECT trigger FROM BlueTable WHERE group_jid = '" + KikTools.chatJID + "';", null);
            StringBuilder sb = new StringBuilder();
            int count = rawQuery.getCount();
            if (count == 0) {
                return "No triggers yet!";
            }
            for (int i = 0; i < count; i++) {
                rawQuery.moveToNext();
                sb.append("\n-");
                sb.append(rawQuery.getString(0));
            }
            String sb2 = sb.toString();
            rawQuery.close();
            return sb2;
        } catch (Exception e) {
            return "error";
        }
    }

    public static String removeCensorFromList(String deleteWord, String censorList) {
        if (deleteWord.startsWith("delete ")) {
            deleteWord = deleteWord.substring(7);
        }
        String str = ",";
        String result = censorList.replace(deleteWord, "").replace(",,", str);
        if (result.startsWith(str)) {
            result = result.substring(1);
        }
        if (result.endsWith(str)) {
            return result.substring(0, result.length() - 1);
        }
        return result;
    }
}
