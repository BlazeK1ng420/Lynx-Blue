package blue.a;

import android.database.Cursor;
import blue.Toast;
import blue.Tools;
import blue.luminosity.database;
import blue.luminosity.executables;
import lynx.blue.net.LoggingOutputStream;
import lynx.blue.net.communicator.XmppSocketV2;
import org.xmlpull.v1.XmlPullParser;

public class Detect {
    public static String displayName;
    private static XmlPullParser parser;

    public static void fake(String attributeValue) {
        String kikString = database.getKikString("blue.catfishv2");
        if (!"Off".equals(kikString) && !attributeValue.contains("<body>") && attributeValue.contains("camera\"") && attributeValue.contains("<app-name>Gallery</app-name>")) {
            String displayName2 = Tools.getDisplayName(Tools.getAllTypes(attributeValue));
            StringBuilder sb = new StringBuilder();
            sb.append(displayName2);
            sb.append(" sent a fake camera message ");
            String str = "in a PM";
            if (attributeValue.contains("@groups.kik.com")) {
                str = "in a group";
            }
            sb.append(str);
            sb.append(" at:\n\n");
            sb.append(Tools.getTime());
            String sb2 = sb.toString();
            if ("Stealth Expose".equals(kikString)) {
                send(Tools.getPersonalJID(), sb2);
            }
            if ("Public Expose".equals(kikString)) {
                Tools.send(Tools.getBothTypes(attributeValue), sb2);
            }
        }
    }

    public static void friend(String attributeValue) {
        if (database.getBoolean("blue.friend", false) && attributeValue.endsWith("</friend-attribution></message>")) {
            String jid = Tools.getJID(attributeValue);
            if (attributeValue.contains("type=\"group")) {
                String buildCode = buildCode(attributeValue);
                if (!buildCode.equals("null")) {
                    Tools.send(jid, buildCode);
                } else {
                    return;
                }
            } else {
                Tools.send(jid, executables.getSCReply());
            }
            Thread.sleep(100);
            String randomUUID = Tools.getRandomUUID();
            LoggingOutputStream loggingOutputStream = XmppSocketV2.KikNetSock;
            StringBuilder sb = new StringBuilder();
            sb.append("<iq type=\"set\" id=\"");
            sb.append(randomUUID);
            sb.append("\"><query xmlns=\"kik:iq:friend\"><remove jid=\"");
            sb.append(jid);
            sb.append("\" /></query></iq>");
            loggingOutputStream.write(sb.toString().getBytes());
            loggingOutputStream.flush();
        }
    }

    public static void gaytrik(String xmpp) {
        if (isMatrik(xmpp)) {
            StringBuilder sb = new StringBuilder();
            String displayName2 = Tools.getDisplayName(Tools.getAllTypes(xmpp));
            if (!displayName2.equals(executables.getMatrikName())) {
                database.setString("matrik.name", displayName2);
                sb.append(displayName2);
                sb.append(" is using Matrik!");
                Toast.toast(sb.toString());
            }
        }
    }

    public static void parse(String attributeValue) {
        if (attributeValue.startsWith("<message")) {
            fake(attributeValue);
            friend(attributeValue);
            read(attributeValue);
            if (database.getBoolean("blue.detect", false)) {
                pikek(attributeValue);
                gaytrik(attributeValue);
            }
        }
    }

    public static void pikek(String xmpp) {
        if (isPikek(xmpp)) {
            StringBuilder sb = new StringBuilder();
            String displayName2 = Tools.getDisplayName(Tools.getAllTypes(xmpp));
            if (!displayName2.equals(executables.getPikekName())) {
                database.setString("pikek.name", displayName2);
                sb.append(displayName2);
                sb.append(" is using a Pikek based mod!");
                Toast.toast(sb.toString());
            }
        }
    }

    public static void read(String skid) {
        if (database.getBoolean("blue.expose", false) && skid.contains("type=\"read\"") && skid.contains("type=\"receipt\"")) {
            String displayName2 = Tools.getDisplayName(Tools.getAllTypes(skid));
            if (!displayName2.equals("Fuck")) {
                count(skid, displayName2);
            }
        }
    }

    public static void send(String jid, String body) {
        try {
            String randomUUID = Tools.getRandomUUID();
            LoggingOutputStream loggingOutputStream = XmppSocketV2.KikNetSock;
            String unixTime = Tools.getUnixTime();
            StringBuilder append = new StringBuilder().append("<message type=\"chat\" to=\"").append(jid).append("\" id=\"").append(randomUUID).append("\" cts=\"").append(unixTime).append("\"><body>").append(body);
            append.append("</body><preview>...</preview><kik push=\"true\" qos=\"true\" timestamp=\"");
            append.append(unixTime);
            loggingOutputStream.write(append.append("\" /><request xmlns=\"kik:message:receipt\" r=\"true\" d=\"true\" /><ri></ri></message>").toString().getBytes());
            loggingOutputStream.flush();
        } catch (Exception e) {
        }
    }

    public static void count(String xmpp, String displayName2) {
        int count = xmpp.split("<msgid id=\"", -1).length - 1;
        StringBuilder sb = new StringBuilder();
        sb.append(displayName2);
        sb.append(" has read your message");
        String message = sb.toString();
        if (count > 1) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(displayName2);
            sb2.append(" has read ");
            sb2.append(count);
            sb2.append(" of your messages");
            message = sb2.toString();
        }
        Toast.toast(message);
    }

    public static boolean isMatrik(String xmpp) {
        boolean z = false;
        if (!xmpp.contains("<body>") && !xmpp.contains("<preview>")) {
            return false;
        }
        if (xmpp.contains("</body><a") || xmpp.contains("</body><b") || xmpp.contains("</body><c") || xmpp.contains("</body><d") || xmpp.contains("</body><e") || xmpp.contains("</body><f")) {
            z = true;
        }
        return z;
    }

    public static boolean isPikek(String xmpp) {
        if (xmpp.contains("<body>") || xmpp.contains("<preview>")) {
            return xmpp.contains("mark=\"1\"");
        }
        return false;
    }

    public static String buildCode(String xmpp) {
        String substring = xmpp.substring(xmpp.indexOf("jid=\"") + 5);
        String str = "";
        try {
            Cursor rawQuery = Tools.getContext().openOrCreateDatabase(Tools.buildCoreID("kikDatabase.db"), 0, null).rawQuery("SELECT group_hashtag, display_name FROM KIKcontactsTable WHERE jid = '" + substring.replace(substring.substring(substring.indexOf("_g@groups.kik.com") + 18), str).replace("\"", str) + "';", null);
            rawQuery.moveToFirst();
            String string = rawQuery.getString(0);
            rawQuery.close();
            StringBuilder sb = new StringBuilder();
            sb.append("(from group ");
            sb.append(string);
            sb.append(")\n\n");
            sb.append(executables.getSCReply());
            return sb.toString();
        } catch (Exception unused) {
            return executables.getSCReply();
        }
    }
}
