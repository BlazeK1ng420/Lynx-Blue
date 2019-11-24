package blue.a;

import blue.Tools;
import org.xmlpull.v1.XmlPullParser;

public class ar {
    private static XmlPullParser parser;

    public static void parse(String str) {
        String trim = str.trim();
        if (!handleWarehouse(trim)) {
            Bypass24.send(trim);
            Bypass24.KikAintSlick(trim);
            arp.pick(trim);
            Group.pick(trim, 1);
            Detect.parse(trim);
        }
    }

    public static boolean handleWarehouse(String xmpp) {
        boolean startsWith;
        String str = "<msg";
        if (!(xmpp.startsWith("<iq") & xmpp.contains("from=\"warehouse@talk.kik.com\"")) || !xmpp.contains(str)) {
            return false;
        }
        String[] parts = xmpp.split(str);
        for (int i = 1; i < parts.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("<message");
            sb.append(parts[i]);
            String replace = sb.toString().replace("</msg>", "</message>");
            String timestamp = Tools.getTimestamp(replace);
            if (timestamp != null && !tooOld(timestamp)) {
                arp.pick(replace);
                Group.pick(replace, 0);
                Detect.parse(replace);
            }
        }
        return true;
    }

    public static boolean tooOld(String stamp) {
        if (!stamp.matches("[0-9]+") || stamp.length() <= 2) {
            return false;
        }
        long check = Long.valueOf(Tools.getUnixTime()) - Long.valueOf(stamp);
        boolean z = true;
        if (check > 3600015) {
            return true;
        }
        if (check >= -3600015) {
            z = false;
        }
        return z;
    }
}
