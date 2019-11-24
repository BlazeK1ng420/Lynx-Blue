package blue.a;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;
import blue.Toast;
import blue.Tools;
import blue.luminosity.database;
import blue.luminosity.executables;
import java.io.ByteArrayOutputStream;
import lynx.blue.net.LoggingOutputStream;
import lynx.blue.net.communicator.XmppSocketV2;

public class Bypass24 {
    public static void KikAintSlick(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("from=\"");
        String personalJID = Tools.getPersonalJID();
        if (personalJID != null) {
            sb.append(personalJID);
            if (str.contains(sb.toString()) && str.contains("v=\"2\"")) {
                LoggingOutputStream loggingOutputStream = XmppSocketV2.KikNetSock;
                loggingOutputStream.write("</k>".getBytes());
                loggingOutputStream.flush();
            }
        }
    }

    public static void send(String str) {
        if (database.getBoolean("should.gif.send", false)) {
            database.setBoolean("should.gif.send", false);
            String randomUUID = Tools.getRandomUUID();
            String randomUUID2 = Tools.getRandomUUID();
            LoggingOutputStream loggingOutputStream = XmppSocketV2.KikNetSock;
            String unixTime = Tools.getUnixTime();
            String kikString = database.getKikString("blue.selectGIF");
            if (kikString == null) {
                Toast.toast("No Image Set!");
            } else {
                String convert = convert(kikString);
                if (convert.contains("Error")) {
                    Toast.toast("Please grant 'Storage' Permission!!");
                } else {
                    loggingOutputStream.write(("<message type=\"chat\" to=\"" + Tools.getPersonalJID() + "\" id=\"" + randomUUID + "\" cts=\"" + unixTime + "\"><pb></pb><kik push=\"true\" qos=\"true\" timestamp=\"" + unixTime + "\"/><request xmlns=\"kik:message:receipt\" r=\"true\" d=\"true\" /><content id=\"" + randomUUID2 + "\" app-id=\"com.kik.ext.stickers\" v=\"2\"><strings><app-name>" + "Stickers" + "</app-name><layout>photo</layout><allow-forward>true</allow-forward><video-should-autoplay>true</video-should-autoplay><video-should-loop>true</video-should-loop><disallow-save>true</disallow-save><video-should-be-muted>true</video-should-be-muted></strings><extras /><hashes /><images><icon>aVZCT1J3MEtHZ29BQUFBTlNVaEVVZ0FBQURBQUFBQXdDQVlBQUFCWEF2bUhBQUFBQVhOU1IwSUFyczRjNlFBQUFBUnpRa2xVQ0FnSUNId0laSWdBQUFNM1NVUkJWR2lCN1poTlNGUlJGTWQvOTg1N2t6WTJpbG5aOTRlV3RXaWhSQkdCTFdyUnhveHNGeEcwcUZYYndLVkV1Q2lJQ0lJMjJUNkVyR1cxTUtKYUJaWlpZMEtmaHZZeE5wV2Y4Mlp1aStQZ0ZEclBlWk0rcCtZSGJ6SHZuWGZ1K1o5N3pybVBVY2FndU45VWplTTBZMmdFeWxuWVJERjBFTFJhcVcvdlU2YXphVE54NXpaUTQzZGtXUkxCdGhvMGp0Tk0vZ1VQVUlQak5Pdkpzc2xQREkyYWhWL3ptU2pYZmtlUUt3VUJmbU81V3F6WUMxdE9RTEIwSHNKSll5SUd2ZGRnc0RPam1mc08rQkU4eUpwYlRyaWF1UXZ3SS9nczFzNzdIaWdJOEp1Q0FMOHBDUEFiOTVNNEU0a3hpUFhDenpjUS93N0ZsVkN5SGtMcklSQVVHMmNZWWhGQVFlbFdVRURzSlNRVDAvc01sc0tTYWxCcWpnVk14T0RWZGZqMEVJd0RKZ2txQUlGRlVMRUR0cDBHYmNId0IzaDJIclFOdFMwU1dQZEZjRWFuOTd1MEZyYWZBVFc3MEx3SlNNYWgrd0lNZFlNVmdvcGRZSmZDU0Q5OGZRS0REeVNUMWNmRjNqaGdGR0RBQUNZQkpDRmNCWUhpMzMwWExadDE5cjBMR09pRWI4OGwyelVuWWNVZXliNHpBdTl2d2VzYjhPVUpyRDJZd1ltQ1RVY2h2UG4zMjlvV1gzTW1JREVCbng5THlTeXBnc3I2TkcrTFllVitHUHM2dXhKd1JpRCtZK3EzdHNSSEZuZ1FNQXJqUTRDV2VrMFJpMGhEQTRUV1NSUHI0TXgrakFPUnE1THhGSFlZNnM1bUpjSkRDUmt3Y2FuVDlJVmV0Y2wwU2FFc0NLM0p2Qk1tQWNtMGVrL00wTmdaeUY2QURvSmRCdVlkREwrYnVyL2hzT3hDWWx4NlpLWXBrMEpaVUhNS1NqYW0rUTZBVlR6ek85T0ZrNVUxUUtBSVN0WUJDdnJ2d01oSHVWK3hFNnFPd2ZMZGsxUEdCYVZrNG9SV1QxM0ZsZUozVGdVb0RSdU9RRkdGTkhMUEpZZytoZEVCK1BRSWVpNjdaLzh2NG0yTUJzdGsrMTlja2NidE9pZk5tQmdUZ1haWVR1QVVKdjFsSTVjeGZ6N3doUGVUZUdrZDFMWEEyNXNRN1pMRGJmRXFXSE5BbW52Z3ZnaEZpNjIyUkJnS3ltdGxDdG5obkFVb2M3Y3hjeHIyM2N4NWtaeTRkeWpqNDd6L0dpMEk4SnVDQUw4cENQQWJkd0VUc1hrSXcvdmE3Z0o2ci9rakl2WDN1Z3Z1Si9FQzV6L29nUVhPUHlFZzZuY1FPUkRWS0RyOGpzSXpoZzZOWmJVQ0ViOWo4VUNFb05XcXFXL3Z3N1lhVUxTUkgrVVVSZEdHYlRWUTM5NzNDOEtXNUc0V2l4cTZBQUFBQUVsRlRrU3VRbUND</icon><png-preview>" + convert + "</png-preview></images><uris /></content></message>").getBytes());
                    loggingOutputStream.flush();
                }
            }
        }
        card();
        article();
    }

    public static String convert(String path) {
        try {
            Bitmap bm = BitmapFactory.decodeFile(path);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bm.compress(CompressFormat.JPEG, 25, outputStream);
            return Base64.encodeToString(outputStream.toByteArray(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public static void article() {
        if (database.getBoolean("should.article.send", false)) {
            database.setBoolean("should.article.send", false);
            String randomUUID = Tools.getRandomUUID();
            String randomUUID2 = Tools.getRandomUUID();
            LoggingOutputStream loggingOutputStream = XmppSocketV2.KikNetSock;
            String unixTime = Tools.getUnixTime();
            String kikString = database.getKikString("blue.selectArticle");
            if (kikString == null) {
                Toast.toast("No Image Set!");
                return;
            }
            String convert = convert(kikString);
            if (convert.contains("Error")) {
                Toast.toast("Please grant 'Storage' Permission!!");
                return;
            }
            StringBuilder append = new StringBuilder().append("<message type=\"chat\" to=\"").append(Tools.getPersonalJID()).append("\" id=\"").append(randomUUID);
            append.append("\" cts=\"");
            append.append(unixTime);
            append.append("\"><pb></pb><kik push=\"true\" qos=\"true\" timestamp=\"");
            append.append(unixTime);
            append.append("\" /><request xmlns=\"kik:message:receipt\" r=\"true\" d=\"true\" /><content id=\"");
            append.append(randomUUID2);
            append.append("\" app-id=\"com.kik.bots.web\" v=\"2\"><strings><app-name>");
            append.append(executables.getImageAppName());
            StringBuilder append2 = append.append("</app-name><bot-jid>kikteam@talk.kik.com</bot-jid><bot-username>kikteam</bot-username><bot-display-name>Kik Team</bot-display-name><title>");
            append2.append(executables.getArticleName());
            append2.append("</title><allow-forward>true</allow-forward><layout>article</layout></strings><extras><item><key>open-popup</key><val>true</val></item></extras><hashes /><images><icon>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</icon><preview>");
            append2.append(convert);
            append2.append("</preview></images><uris /></content></message>");
            loggingOutputStream.write(append2.toString().getBytes());
            loggingOutputStream.flush();
        }
    }

    public static void card() {
        if (database.getBoolean("should.card.send", false)) {
            database.setBoolean("should.card.send", false);
            String randomUUID = Tools.getRandomUUID();
            String randomUUID2 = Tools.getRandomUUID();
            LoggingOutputStream loggingOutputStream = XmppSocketV2.KikNetSock;
            String unixTime = Tools.getUnixTime();
            String kikString = database.getKikString("blue.selectCard");
            if (kikString == null) {
                Toast.toast("No Image Set!");
                return;
            }
            String convert = convert(kikString);
            if (convert.contains("Error")) {
                Toast.toast("Please grant 'Storage' Permission!!");
                return;
            }
            loggingOutputStream.write(("<message type=\"chat\" to=\"" + Tools.getPersonalJID() + "\" id=\"" + randomUUID + "\" cts=\"" + unixTime + "\"><pb></pb><kik push=\"true\" qos=\"true\" timestamp=\"" + unixTime + "\"/><request xmlns=\"kik:message:receipt\" r=\"true\" d=\"true\" /><content id=\"" + randomUUID2 + "\" app-id=\"com.kik.ext.stickers\" v=\"2\"><strings><app-name>" + executables.getImageAppName() + "</app-name><layout>photo</layout><allow-forward>true</allow-forward></strings><extras /><hashes /><images><icon>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</icon><preview>" + convert + "</preview></images><uris /></content></message>").getBytes());
            loggingOutputStream.flush();
        }
    }
}
