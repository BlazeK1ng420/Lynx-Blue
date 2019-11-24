package blue.a;

import blue.Tools;
import blue.luminosity.database;
import blue.luminosity.executables;
import lynx.blue.chat.vm.sa;

public class arp {
    public static void chat(String xmpp) {
        if (xmpp.startsWith("<message") && xmpp.contains("type=\"chat\"")) {
            if (!database.getKikString("blue.autoreply").equals("Off") && xmpp.contains("xmlns=\"jabber:client\"")) {
                if (xmpp.contains("<body>")) {
                    reply(xmpp, executables.getReply());
                } else if (xmpp.contains("app-id=\"com.kik")) {
                    reply(xmpp, executables.getMediaReply());
                }
            }
        }
    }

    public static void groupReply(String xmpp, String body) {
        Tools.send(Tools.getGroupJID(xmpp), pickBody(xmpp, body));
    }

    public static void groupchat(String xmpp) {
        if (database.getBoolean("blue.autoreplygroup", false) && xmpp.startsWith("<message") && xmpp.contains("type=\"groupchat\"")) {
            if (xmpp.contains("<body>")) {
                groupReply(xmpp, executables.getReply());
            } else if (xmpp.contains("app-id=\"com.kik")) {
                groupReply(xmpp, executables.getMediaReply());
            }
        }
    }

    public static void pick(String xmpp) {
        if (xmpp.startsWith("<message")) {
            if (xmpp.contains("type=\"chat\"")) {
                chat(xmpp);
            } else if (xmpp.contains("type=\"groupchat\"")) {
                groupchat(xmpp);
            }
        }
    }

    public static String pickBody(String str, String str2) {
        if (!str.contains("<body>")) {
            return str2;
        }
        String body = Tools.getBody(str);
        String kikString = database.getKikString("blue.autoreply");
        if (kikString.equals("Custom Reply")) {
            return str2;
        }
        if (kikString.contains("Mock")) {
            return mock(body, kikString.contains("Reverse") ? 1 : 0);
        }
        if (!kikString.contains("Meme")) {
            return str2;
        }
        StringBuilder sb = new StringBuilder(body);
        if (kikString.contains("Reverse")) {
            sb = sb.reverse();
        }
        return sb.toString();
    }

    public static void reply(String xmpp, String body) {
        Tools.send(Tools.getJID(xmpp), pickBody(xmpp, body));
    }

    public static String mock(String body, int i) {
        char[] arr = body.toLowerCase().toCharArray();
        boolean makeUppercase = false;
        for (int i2 = 0; i2 < arr.length; i2++) {
            if (makeUppercase && Character.isLetter(arr[i2])) {
                arr[i2] = Character.toUpperCase(arr[i2]);
                makeUppercase = false;
            } else if (!makeUppercase && Character.isLetter(arr[i2])) {
                makeUppercase = true;
            }
        }
        StringBuilder sb = new StringBuilder(String.valueOf(arr));
        if (i != 0) {
            sb = sb.reverse();
        }
        return sb.toString();
    }
}
