package blue;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Process;
import android.provider.Settings.Secure;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.kik.ximodel.XiBareUserJid;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

import blue.activity.ExceptionHandler;
import blue.luminosity.database;
import blue.luminosity.executables;
import blue.luminosity.perchat.PerChat;
import kik.core.datatypes.Message;
import kik.core.datatypes.Message.MessageSource;
import kik.core.util.TimeUtils;
import lynx.blue.chat.KikApplication;
import lynx.blue.net.LoggingOutputStream;
import lynx.blue.net.communicator.XmppSocketV2;
import lynx.blue.widget.MessageTextView;

public class Tools {
    public static boolean adminCheck(String str) {
        try {
            Cursor rawQuery = getContext().openOrCreateDatabase(buildCoreID("kikDatabase.db"), 0, null).rawQuery("SELECT user_permission_level FROM KIKcontactsTable WHERE jid = '" + str + "';", null);
            rawQuery.moveToFirst();
            boolean contains = rawQuery.getString(0).contains("ADMIN");
            rawQuery.close();
            return contains;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String buildCoreID(String str) {
        return getCoreID() + "." + str;
    }

    public static void bypassBrick() {
        execSQL(buildCoreID("kikDatabase.db"), "DROP TABLE IF EXISTS messagesTable;");
    }

    public static String bypassDeviceBan() {
        String fakeID = executables.getFakeID();
        if (!fakeID.equals("null")) {
            return fakeID;
        }
        String generateDeviceID = generateDeviceID();
        database.setString("bypass.ban", generateDeviceID);
        return generateDeviceID;
    }

    public static boolean checkperm() {
        if (ContextCompat.checkSelfPermission(getContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            return true;
        }
        Toast.toast("Please grant the 'Storage' Permission first!");
        return false;
    }

    public static void close() {
        Process.killProcess(Process.myPid());
    }

    public static String dossier() {
        try {
            Cursor rawQuery = getContext().openOrCreateDatabase(buildCoreID("kikDatabase.db"), 0, null).rawQuery("SELECT photo_timestamp, photo_url, display_name FROM KIKcontactsTable WHERE jid = '" + KikTools.chatJID + "';", null);
            rawQuery.moveToNext();
            String string = rawQuery.getString(0);
            String string2 = rawQuery.getString(1);
            String string3 = rawQuery.getString(2);
            rawQuery.close();
            return dossier(string, string2, string3);
        } catch (Exception e) {
            return "unexpected error";
        }
    }

    public static String dossier(String str, String str2, String str3) {
        Date date = new Date(Long.valueOf(str).longValue());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        StringBuilder sb = new StringBuilder();
        sb.append("Profile info:\n\nName: ");
        sb.append(str3);
        String str4 = "\n\nProfile picture:\n\n";
        sb.append(str4);
        sb.append(str2);
        String str5 = "/orig.jpg";
        if (!str5.contains(str2)) {
            sb.append(str5);
        }
        if (!KikTools.chatJID.contains("@groups.kik.com")) {
            sb.append(str4);
            sb.append("\n\nBackground picture:\n\n");
            sb.append(str2);
            sb.append("/ppext_background_full.jpg");
        }
        sb.append("\n\nPicture last changed at:\n\n");
        sb.append(simpleDateFormat.format(date));
        sb.append("");
        return sb.toString();
    }

    public static boolean emoteCheck() {
        boolean z = false;
        try {
            Cursor rawQuery = getContext().openOrCreateDatabase(buildCoreID("smileyTable"), 0, null).rawQuery("SELECT * FROM smileyTable WHERE smiley_id = 'pie';", null);
            if (rawQuery.getCount() > 0) {
                z = true;
            }
            rawQuery.close();
            return z;
        } catch (Exception e) {
            return false;
        }
    }


    public static void execSQL(java.lang.String r4, java.lang.String r5) {
        try (SQLiteDatabase openDatabase = KikApplication.u.openOrCreateDatabase(r4, Context.MODE_PRIVATE, null)) {
            openDatabase.execSQL(r5);
        }
    }

    public static String generateDeviceID() {
        StringBuilder sb = new StringBuilder();
        String str = "12ab345cd67890ef";
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            sb.append(str.charAt(random.nextInt(str.length())));
        }
        return sb.toString();
    }

    public static String getAllTypes(String str) {
        if (!str.contains("<alias-sender>")) {
            return getJID(str);
        }
        String substring = str.substring(str.indexOf("<alias-sender>") + 14);
        String str2 = "";
        return substring.replace(substring.substring(substring.indexOf("</alias-sender>") + 15), str2).replace("</alias-sender>", str2);
    }

    public static String getBody(String str) {
        String substring = str.substring(str.indexOf("<body>") + 6);
        String str2 = "";
        return substring.replace(substring.substring(substring.indexOf("</body>") + 7), str2).replace("</body>", str2);
    }

    public static String getBothTypes(String str) {
        return str.contains("@groups.kik.com") ? getGroupJID(str) : getJID(str);
    }

    public static String getCoreID() {
        return KikApplication.kikID;
    }

    public static String getDeviceID() {
        return Secure.getString(getContext().getContentResolver(), "android_id");
    }

    public static String getDisplayName(String str) {
        try {
            Cursor rawQuery = getContext().openOrCreateDatabase(buildCoreID("kikDatabase.db"), 0, null).rawQuery("SELECT display_name FROM KIKcontactsTable WHERE jid = '" + str + "';", null);
            rawQuery.moveToNext();
            String string = rawQuery.getString(0);
            rawQuery.close();
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return "Fuck";
        }
    }

    public static String getGroupJID(String str) {
        String str2 = "<g jid=\"";
        try {
            if (!str.contains(str2)) {
                return getSysJID(str);
            }
            String substring = str.substring(str.indexOf(str2) + 8);
            String str3 = "";
            return substring.replace(substring.substring(substring.indexOf("_g@groups.kik.com") + 18), str3).replace("\"", str3);
        } catch (Exception e) {
            return "null";
        }
    }

    public static String getJID(String str) {
        if (!str.contains("@talk.kik.com")) {
            return "null";
        }
        String substring = str.substring(str.indexOf("from=\"") + 6);
        String str2 = "";
        return substring.replace(substring.substring(substring.indexOf("@talk.kik.com") + 13), str2).replace("\"", str2);
    }

    public static String getJIDFromUsername(String str) {
        try {
            Cursor rawQuery = getContext().openOrCreateDatabase(buildCoreID("kikDatabase.db"), 0, null).rawQuery("SELECT jid FROM KIKcontactsTable WHERE user_name = '" + str + "';", null);
            rawQuery.moveToNext();
            String string = rawQuery.getString(0);
            rawQuery.close();
            return string;
        } catch (Exception e) {
            return "Username not found! Remember, you have to start chatting with the user first before you can grab their JID\n\nThe username is also CASE SENSITIVE";
        }
    }

    public static String getJoinJID(String str) {
        String substring = str.substring(str.indexOf("status jid=\"") + 12);
        String str2 = "";
        return substring.replace(substring.substring(substring.indexOf("_a@talk.kik.com") + 15), str2).replace("\"", str2);
    }

    public static String getKikID() {
        return KikApplication.getDeviceId();
    }

    public static String getMSGID(String str) {
        String substring = str.substring(str.indexOf("id=\"") + 4);
        String str2 = "";
        return substring.replace(substring.substring(substring.indexOf("id=\"") + 36), str2).replace("\"", str2).trim();
    }

    public static boolean getNewFag(String str) {
        try {
            Context context = getContext();
            String buildCoreID = buildCoreID("kikDatabase.db");
            SQLiteDatabase openOrCreateDatabase = context.openOrCreateDatabase(buildCoreID, 0, null);
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT group_jid, jid FROM BlueTable WHERE has_sent_message = 'false' AND (");
            sb.append(getUnixTime());
            sb.append(" - join_time) > ");
            sb.append(str);
            String sb2 = sb.append(" ;").toString();
            Toast.toast(sb2);
            Log.e(buildCoreID, sb2);
            Cursor rawQuery = openOrCreateDatabase.rawQuery(sb2, null);
            rawQuery.moveToFirst();
            remove(rawQuery.getString(0), rawQuery.getString(1));
            rawQuery.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getOldFag(String str) {
        try {
            Context context = getContext();
            String buildCoreID = buildCoreID("kikDatabase.db");
            SQLiteDatabase openOrCreateDatabase = context.openOrCreateDatabase(buildCoreID, 0, null);
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT group_jid, jid FROM BlueTable WHERE has_sent_message = 'true' AND (");
            sb.append(getUnixTime());
            sb.append(" - last_activity) > ");
            sb.append(str);
            String sb2 = sb.append(" ;").toString();
            Toast.toast(sb2);
            Log.e(buildCoreID, sb2);
            Cursor rawQuery = openOrCreateDatabase.rawQuery(sb2, null);
            rawQuery.moveToFirst();
            remove(rawQuery.getString(0), rawQuery.getString(1));
            rawQuery.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getPersonalJID() {
        String kikString = database.getKikString("CredentialData.jid");
        return kikString != null ? kikString.replace("/null", "") : kikString;
    }

    public static String getRandomUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getSysJID(String str) {
        String substring = str.substring(str.indexOf("from=\"") + 6);
        String str2 = "";
        return substring.replace(substring.substring(substring.indexOf("_g@groups.kik.com") + 18), str2).replace("\"", str2);
    }

    public static String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public static String getUnixTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String getUsername(String str) {
        String substring = str.substring(str.indexOf("<username>") + 10);
        String str2 = "";
        return substring.replace(substring.substring(substring.indexOf("</username>") + 11), str2).replace("</username>", str2);
    }

    public static String getUsernameFromJID(String str) {
        return str.endsWith("@groups.kik.com") ? "GROUP" : str.contains("_") ? str.substring(0, str.lastIndexOf("_")) : str.substring(0, str.lastIndexOf("@"));
    }

    public static boolean isBot(String str) {
        String body = getBody(str);
        return (body.contains(":)") || body.contains(":*") || body.contains(":B") || body.contains(":(") || body.contains(";)") || body.contains(":P") || body.contains(":|") || body.contains(":D") || body.contains("&gt;:(") || body.contains(":X") || body.contains("&lt;3") || body.contains("&lt;/3") || body.contains(":'(") || body.contains(":O") || body.contains(":3") || body.contains(":S") || body.contains(":$") || body.contains("&gt;:)") || body.contains(":E") || (body.contains(":/") && (body.contains("://") ^ true)) || body.contains("D:")) ? str.contains("<ri/>") : body.codePointCount(0, body.length()) > 11 && !str.contains("...");
    }

    public static boolean isLeet(String str) {
        return str.equals("") || str.equals("") || str.equals("") || str.equals("") || str.equals("") || str.equals("") || str.equals("");
    }

    public static boolean isNameTooLong(String str) {
        String substring = str.substring(str.indexOf("status jid=\"") + 12);
        String str2 = "";
        return substring.replace(substring.substring(substring.indexOf("has joined") + 10), str2).replace("has joined", str2).length() + -70 >= 120;
    }

    public static boolean isPictureEmpty(String str) {
        try {
            Cursor rawQuery = getContext().openOrCreateDatabase(buildCoreID("kikDatabase.db"), 0, null).rawQuery("SELECT photo_timestamp FROM KIKcontactsTable WHERE jid = '" + str + "';", null);
            rawQuery.moveToFirst();
            boolean equals = rawQuery.getString(0).equals("0");
            rawQuery.close();
            return equals;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean ownerCheck(String str) {
        try {
            Cursor rawQuery = getContext().openOrCreateDatabase(buildCoreID("kikDatabase.db"), 0, null).rawQuery("SELECT user_permission_level FROM KIKcontactsTable WHERE jid = '" + str + "';", null);
            rawQuery.moveToFirst();
            boolean equals = rawQuery.getString(0).equals("SUPER_ADMIN");
            rawQuery.close();
            return equals;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean rageAdminCheck(String str) {
        try {
            Cursor rawQuery = getContext().openOrCreateDatabase(buildCoreID("kikDatabase.db"), 0, null).rawQuery("SELECT permission_level FROM memberTable WHERE member_jid = '" + getAllTypes(str) + "';", null);
            rawQuery.moveToFirst();
            boolean contains = rawQuery.getString(0).contains("ADMIN");
            rawQuery.close();
            return contains;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean rageTriggerCheck(String str) {
        boolean z;
        try {
            boolean z2 = database.getBoolean(PerChat.changeKeyIfEnabled("blue.rageadmin", getGroupJID(str)), false);
            if (z2) {
                return z2;
            }
            Cursor rawQuery = getContext().openOrCreateDatabase(buildCoreID("kikDatabase.db"), 0, null).rawQuery("SELECT permission_level FROM memberTable WHERE member_jid = '" + getAllTypes(str) + "';", null);
            rawQuery.moveToFirst();
            z = rawQuery.getString(0).contains("ADMIN");
            rawQuery.close();
            return z;
        } catch (Exception e) {
            e.printStackTrace();
            z = false;
        }
    }

    public static void remove(String str, String str2) {
        String randomUUID = getRandomUUID();
        LoggingOutputStream loggingOutputStream = XmppSocketV2.KikNetSock;
        String groupJID = getGroupJID(str);
        StringBuilder sb = new StringBuilder();
        sb.append("<iq type=\"set\" id=\"");
        sb.append(randomUUID);
        sb.append("\"><query xmlns=\"kik:groups:admin\"><g jid=\"");
        sb.append(str);
        sb.append("\"><m r=\"1\">");
        StringBuilder append = sb.append(str2);
        append.append("</m></g></query></iq>");
        loggingOutputStream.write(append.toString().getBytes());
        loggingOutputStream.flush();
    }

    public static void send(String str, String str2) {
        KikApplication.getUpDownManager().sendMessage(Message.outgoingTextMessage(str2, str, MessageSource.DEFAULT));
    }

    public static void setColors(MessageTextView messageTextView, String str) {
        if (database.getBoolean("blue.bubble", false)) {
            messageTextView.setBackgroundColor(Color.parseColor(ColorPicker.getValue("blue.bubblecolor")));
            messageTextView.setTextColor(Color.parseColor(ColorPicker.getValue("blue.textcolor")));
        }
    }

    public static void setExceptionHandler(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(context));
    }

    public static void setLinkColor(MessageTextView messageTextView) {
        if (database.getBoolean("blue.bubble", false)) {
            messageTextView.setLinkTextColor(Color.parseColor(ColorPicker.getValue("blue.linktextcolor")));
        }
    }

    public static void setOnStart() {
        database.setBoolean("is.BG.being.set", false);
        database.setBoolean("is.GIF.being.set", false);
        database.setBoolean("is.card.being.set", false);
    }

    public static void setWhenFirstRun() {
        String str = "null";
        if (database.getString("blue.bubblecolor", str).equals(str)) {
            database.setString("blue.bubblecolor", "#ff292c85");
            database.setString("blue.textcolor", "#ffffffff");
            database.setString("blue.linktextcolor", "#ff40deed");
            database.setString("blue.autoadd", "Off");
            database.setKikString("blue.autoreply", "Off");
            database.setKikString("blue.catfishv2", "Off");
            database.setKikString("blue.selectPresetBG", "2");
            database.setKikString("blue.bgType", "Off");
            database.setBoolean("blue.loggers", true);
            database.setBoolean("blue.theme", false);
            database.setBoolean("blue.toast", true);
            database.setBoolean("blue.gifs", true);
            database.setBoolean("blue.botvids", true);
            database.setBoolean("blue.pull", true);
            database.setBoolean("blue.bubble", true);
            database.setBoolean("blue.photos", true);
            database.setBoolean("blue.bios", true);
            database.setBoolean("blue.group", true);
            database.setBoolean("blue.admin", true);
            database.setBoolean("blue.enableBG", true);
            database.setBoolean("blue.reason", true);
            database.setBoolean("blue.bypassmedia", true);
            database.setBoolean("is.BG.being.set", false);
            database.setBoolean("is.GIF.being.set", false);
            database.setBoolean("is.card.being.set", false);
        }
    }

    public static boolean shouldReadSend(String str) {
        int i = database.getInt(PerChat.changeKeyIfEnabled("read.receipts", str), 0);
        return i == 0 || i == 3;
    }

    //Only byte dumps for this too lazy to finish this lol
    public static boolean triggerDBCheck(java.lang.String r6, java.lang.String r7) {
        /*
            r5 = 0
            r2 = 0
            android.content.Context r3 = getContext()     // Catch:{ Exception -> 0x003a }
            java.lang.String r4 = "kikDatabase.db"
            java.lang.String r4 = buildCoreID(r4)     // Catch:{ Exception -> 0x003a }
            android.database.sqlite.SQLiteDatabase r1 = r3.openOrCreateDatabase(r4, r2, r5)     // Catch:{ Exception -> 0x003a }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x003a }
            r3.<init>()     // Catch:{ Exception -> 0x003a }
            java.lang.String r4 = "SELECT response FROM BlueTable WHERE group_jid = '"
            r3.append(r4)     // Catch:{ Exception -> 0x003a }
            r3.append(r6)     // Catch:{ Exception -> 0x003a }
            java.lang.String r4 = "' AND trigger = '"
            r3.append(r4)     // Catch:{ Exception -> 0x003a }
            r3.append(r7)     // Catch:{ Exception -> 0x003a }
            java.lang.String r4 = "';"
            r3.append(r4)     // Catch:{ Exception -> 0x003a }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x003a }
            android.database.Cursor r0 = r1.rawQuery(r3, r5)     // Catch:{ Exception -> 0x003a }
            int r2 = r0.getCount()     // Catch:{ Exception -> 0x003a }
            r0.close()     // Catch:{ Exception -> 0x003a }
        L_0x0039:
            return r2
        L_0x003a:
            r1 = move-exception
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: blue.Tools.triggerDBCheck(java.lang.String, java.lang.String):boolean");
    }

    public static Context getContext() {
        return KikApplication.u.getApplicationContext();
    }

    public static boolean isBotPhrase(String body) {
        boolean z = false;
        if (body.contains("&lt;") && (!(body.contains("&gt;") & (!body.contains("&quot;"))))) {
            return false;
        }
        if (body.equals("omg") || body.equals("lol") || body.equals("neat") || body.equals("kind") || body.equals("friendly") || body.equals("relaxing") || body.equals("lmao") || body.equals("lovely") || body.equals("fantastic") || body.equals("let's talk") || body.equals("lmfao") || body.equals("lets chat")) {
            z = true;
        }
        return z;
    }

    public static void clearCache() {
        try {
            deleteDir(getContext().getCacheDir());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String file : children) {
                if (!deleteDir(new File(dir, file))) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir == null || !dir.isFile()) {
            return false;
        } else {
            return dir.delete();
        }
    }

    public static long daysOnKik(Date regDate) {
        return TimeUtils.daysBeforeToday(regDate.getTime());
    }

    public static String shouldDeliveredSend(String str) {
        if (database.getInt(PerChat.changeKeyIfEnabled("read.receipts", str), 0) > 1) {
            return "false";
        }
        return "true";
    }

    public static String getTimestamp(String please_kill_me_slowly) {
        String str = "";
        String str2 = "timestamp=\"";
        try {
            String please_kill_me_slowly2 = please_kill_me_slowly.substring(please_kill_me_slowly.indexOf(str2) + 11);
            return please_kill_me_slowly2.replace(please_kill_me_slowly2.substring(please_kill_me_slowly2.indexOf(str2) + 14), str).replace("\"", str).trim();
        } catch (StringIndexOutOfBoundsException e) {
            return String.valueOf(System.currentTimeMillis() - 1000);
        }
    }

    public static String getJIDfromXiphias(XiBareUserJid xiBareUserJid) {
        return xiBareUserJid.getLocalPart();
    }
}
