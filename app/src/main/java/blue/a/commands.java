package blue.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

import blue.KikTools;
import blue.Song;
import blue.Toast;
import blue.Tools;
import blue.activity.BotSystem;
import blue.luminosity.database;
import blue.luminosity.executables;

public class commands {
    public static void addGroupLock(String str) {
        database.setString(buildLockName(), str);
    }

    public static void addLeave(String str) {
        database.setString(buildLeave(), str);
    }

    public static void addWelcome(String str) {
        database.setString(buildWelcome(), str);
    }

    public static void botComs(String str) {
        if (str.equals("!bot")) {
            BotSystem.init();
        } else if (str.equals("!power")) {
            Song.play(Tools.getContext());
        } else {
            if ("not group".equals(checkGroupJID())) {
                str = "This command only works in group chats!";
            } else if (str.equals("!exit")) {
                KikTools.leaveGroup(KikTools.chatJID);
                return;
            }
            if (str != null) {
                Toast.toast(str);
            }
        }
    }

    public static String buildLeave() {
        return "leave." + checkGroupJID();
    }

    public static String buildLock() {
        return "lock." + checkGroupJID();
    }

    public static String buildLockName() {
        return "gn." + checkGroupJID();
    }

    public static String buildSpam() {
        return "spam." + KikTools.chatJID;
    }

    public static String buildWelcome() {
        return "welcome." + checkGroupJID();
    }

    public static String checkGroupJID() {
        String str = KikTools.chatJID;
        return !str.endsWith("_g@groups.kik.com") ? "not group" : str;
    }

    public static String customComs(String str) {
        if (isCommand(str)) {
            return command(str);
        }
        if (str.startsWith("!jidu ")) {
            return Tools.getJIDFromUsername(str.replace("!jidu ", ""));
        }
        String lowerCase = str.toLowerCase();
        if (lowerCase.equals("!update")) {
            return "https://lynxmods.com";
        }
        if (lowerCase.equals("!vince")) {
            return "Yikes";
        }
        if (lowerCase.equals("!credits")) {
            return "Made by Lynx";
        }
        if (lowerCase.equals("!bluekik")) {
            return "yikes";
        }
        if (lowerCase.equals("!crash")) {
            str = "Crash‏‏‏‏‏‏‏‏‏‏‏‏‏‏‏‏.me\n\nCrashed by Lynx Mods\nU MAD?? KEK";
        }
        if (lowerCase.equals("!blank")) {
            return "‍";
        }
        if (lowerCase.equals("!wipe")) {
            return "BlueKik Chat Wiper!\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n ";
        }
        if (lowerCase.startsWith("!ud ")) {
            String parse = null;
            try {
                parse = udshit.parse(str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            return parse + "\n\n" + "Made by Lynx Mods";
        } else if (lowerCase.equals("!lag")) {
            return "no";
        } else {
            if (lowerCase.equals("!id")) {
                return "ID coms\n------------------\n\n!deviceid (grabs your real android device ID)\n\n!generate (generates fake android ID)\n\n!get (shows your spoofed android ID)\n\n!kikid (grabs ID used for abtests)\n\n!coreid (grabs ID used for database files)";
            }
            if (lowerCase.equals("!kikid")) {
                return Tools.getKikID();
            }
            if (lowerCase.equals("!uuid")) {
                return Tools.getRandomUUID();
            }
            if (lowerCase.equals("!deviceid")) {
                return Tools.getDeviceID();
            }
            if (lowerCase.equals("!generate")) {
                return Tools.generateDeviceID();
            }
            if (lowerCase.equals("!get")) {
                return executables.getFakeID();
            }
            if (lowerCase.equals("!coreid")) {
                return Tools.getCoreID();
            }
            if (lowerCase.equals("!jiduhelp")) {
                return "To get someone's JID, you must be chatting with them. After that, type !jid (username here)\n\nExample:\n!jid kikteam\n\n**KEEP IN MIND THAT THE USERNAME IS CASE SENSITIVE**";
            }
            if (lowerCase.equals("!kiktheme")) {
                return "Download high quality images of Kik's chat themes here: https://drive.google.com/open?id=1t9tfznZcYl5tPO2DgMTGGe_PRprQXDEJ \n\nI didn't include these in the APK because it would double the size of the download\n\n~Blue.";
            }
            if (lowerCase.equals("!jid")) {
                return KikTools.chatJID;
            }
            if (lowerCase.equals("!ragebot")) {
                return "trigger > response\ncensor (trigger here)\ndelete (trigger here)\ndelete all";
            }
            if (lowerCase.equals("!settings")) {
                return getBotConfig();
            }
            if (lowerCase.equals("!profile")) {
                return Tools.dossier();
            }
            if (!str.equals("!triggers")) {
                return str;
            }
            String triggerList = getTriggerList();
            Tools.send(triggerList, KikTools.chatJID);
            return triggerList;
        }
    }

    public static String getBotConfig() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bot settings for this chat:\n\nWelcome message - ");
        sb.append(executables.getWelcome(buildWelcome()));
        sb.append("\n\nLeave message - ");
        sb.append(executables.getLeave(buildLeave()));
        sb.append("\n\nGroup lock status - ");
        sb.append(executables.getLock(buildLock()));
        sb.append("\n\nGroup name lock status - ");
        sb.append(executables.getLockName(buildLockName()));
        return sb.toString();
    }

    public static String getTriggerList() {
        StringBuilder sb = new StringBuilder();
        sb.append("~Triggers~\n");
        sb.append(Builders.getTriggerListCommand().replace("No triggers yet!", " "));
        sb.append("\n\n~Censors~\n");
        String censorListCommand = Builders.getCensorListCommand();
        if (censorListCommand != null) {
            sb.append(censorListCommand.replace(",", "\n-"));
        }
        return sb.toString().replace("\nnull", "");
    }

    public static void lockBan() {
        database.setString(buildLock(), "On (ban)");
        Toast.toast("Group locked! (ban)\n\n*PLEASE NOTE: Kik often bans / hides groups from search results that are ban locked*");
    }

    public static void lockRemove() {
        database.setString(buildLock(), "On (remove)");
        Toast.toast("Group locked! (remove)");
    }

    public static void resetBot() {
        database.setString(buildWelcome(), "not set!");
        database.setString(buildLeave(), "not set!");
        database.setString(buildLock(), "off");
        database.setString(buildLockName(), "off");
        String str = "null";
        database.setString("spam.body", str);
        database.setString("spam.target", str);
    }

    public static void spam(String str) {
        database.setString("spam.body", str);
        database.setString("spam.target", KikTools.chatJID);
    }

    public static void unlock() {
        database.setString(buildLock(), "off");
        database.setString(buildLockName(), "off");
        Toast.toast("Group unlocked");
    }

    public static boolean isBotCommand(String str) {
        if (!database.getBoolean("init.complete", true)) {
            return true;
        }
        boolean z = true;
        String lowerCase = str.toLowerCase();
        if (!lowerCase.equals("!bot") && !lowerCase.equals("!power") && !lowerCase.equals("!exit")) {
            z = false;
        }
        return z;
    }

    public static boolean isCommand(String str) {
        String lowerCase = str.toLowerCase();
        return lowerCase.startsWith("!addcom ") || lowerCase.startsWith("!delcom ") || lowerCase.equals("!delall") || lowerCase.startsWith("!c ") || lowerCase.equals("!commands") || lowerCase.equals("!customcom") || Tools.getContext().getSharedPreferences("CustomCommands", 0).getString(lowerCase, null) != null;
    }

    public static String command(String str) {
        int i = 0;
        Context context = Tools.getContext();
        String trim = str.trim();
        Pattern compile = Pattern.compile("^[!a-zA-Z0-9]*$");
        SharedPreferences sharedPreferences = context.getSharedPreferences("CustomCommands", 0);
        Editor edit = sharedPreferences.edit();
        if (trim.toLowerCase().startsWith("!addcom ") && trim.length() > 8) {
            String trim2 = trim.substring(8, trim.length()).trim();
            if (trim2.length() > 3 && trim2.contains(" ")) {
                String trim3 = trim2.substring(0, trim2.indexOf(" ")).toLowerCase().trim();
                String trim4 = trim2.substring(trim2.indexOf(" ") + 1, trim2.length()).trim();
                if (!compile.matcher(trim3).matches() || trim4.length() == 0 || trim3.length() == 0) {
                    return trim;
                }
                if (m12default(trim3)) {
                    return "Cannot add default command";
                }
                edit.putString(trim3, trim4).commit();
                return "Command was applied";
            }
        }
        if (trim.toLowerCase().startsWith("!delcom ") && trim.length() > 8) {
            String trim5 = trim.substring(8, trim.length()).toLowerCase().trim();
            if (m12default(trim5)) {
                return "Cannot delete default command";
            }
            if (sharedPreferences.getString(trim5, null) == null) {
                return "Command does not exist";
            }
            edit.remove(trim5).commit();
            return "Command was deleted";
        } else if (trim.equalsIgnoreCase("!delall")) {
            if (sharedPreferences.getAll().size() == 0) {
                return "Command list is empty";
            }
            edit.clear().commit();
            return "Command list was cleared";
        } else if (trim.toLowerCase().startsWith("!c ") && trim.length() > 3) {
            String trim6 = trim.substring(3, trim.length()).trim();
            Charset forName = Charset.forName("UTF-8");
            byte[] bytes = "🇦﻿".getBytes(forName);
            StringBuilder sb = new StringBuilder(trim6.length());
            String upperCase = trim6.toUpperCase();
            while (true) {
                int i2 = i;
                if (i2 >= trim6.length()) {
                    return sb.toString();
                }
                char charAt = upperCase.charAt(i2);
                if (charAt - 'A' < 0 || charAt - 'A' > 26) {
                    sb.append(charAt);
                } else {
                    byte[] bArr = (byte[]) bytes.clone();
                    bArr[3] = (byte) ((charAt - 'A') + bArr[3]);
                    sb.append(new String(bArr, forName));
                }
                i = i2 + 1;
            }
        } else if (trim.equalsIgnoreCase("!commands")) {
            String obj = sharedPreferences.getAll().keySet().toString();
            StringBuilder append = new StringBuilder().append("Commands List:\n\n!bot (opens menu)\n!settings\n!customcom\n!credits\n!update\n!ud (word to look up)\n!profile (grabs profile info)\n!exit (leaves group)\n!kiktheme\n\nTroll Commands:\n\n!jid\n!jidu (username)\n!crash\n!blank\n!wipe\n!lag");
            String replace = obj.replace("[", "").replace("]", "").replace(", ", "\n");
            if (replace.contains("!")) {
                append = append.append("\n\nCustom Commands:\n");
            }
            return append.append(replace).toString();
        } else if (trim.equalsIgnoreCase("!customcom")) {
            return "To add or edit an existing command, use:\n!addcom name text\n\nTo delete an existing command, use:\n!delcom name\n\nTo view a list of existing commands, use:\n!commands\n\nTo clear your commands, use:\n!delall\n\nNOTE: Command names can only have letters, numbers, and an exclamation point.";
        } else {
            return !trim.contains(" ") ? sharedPreferences.getString(trim.toLowerCase(), trim) : trim;
        }
    }

    /* renamed from: default reason: not valid java name */
    public static boolean m12default(String str) {
        return str.equals("!update") || str.equals("!vince") || str.equals("!credits") || str.equals("!bluekik") || str.equals("!lag") || str.equals("!crash") || str.equals("!blank") || str.equals("!wipe") || str.equals("!set") || str.equals("!customcom") || str.equals("!brick") || str.equals("!id") || str.equals("!kiktheme") || str.equals("!jidhelp") || str.equals("!jid") || str.equals("!delcom") || str.equals("!delall") || str.equals("!c") || str.equals("!commands") || str.equals("!bin");
    }
}
