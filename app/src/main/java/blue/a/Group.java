package blue.a;

import blue.Tools;
import blue.luminosity.database;
import blue.luminosity.executables;
import blue.luminosity.perchat.PerChat;
import lynx.blue.net.LoggingOutputStream;
import lynx.blue.net.communicator.XmppSocketV2;

public class Group {
    public static String AddID;
    public static String GroupID;

    public static void annoyingNiggersAreRektHere(String xmpp) {
        String groupJID = Tools.getGroupJID(xmpp);
        if (Tools.adminCheck(groupJID)) {
            String string = database.getString(PerChat.changeKeyIfEnabled("blue.antiTS", Tools.getGroupJID(xmpp)), "Off");
            if (!string.equals("Off") && xmpp.contains("<body>") && moddedTS(Tools.getTimestamp(xmpp))) {
                removeReason(Tools.getGroupJID(xmpp), "Modded timestamp detected, removing...");
                if (string.equals("Remove")) {
                    remove(xmpp);
                }
                if (string.equals("Ban")) {
                    ban(xmpp);
                }
            }
            if (database.getBoolean(PerChat.changeKeyIfEnabled("blue.ragebot2", Tools.getGroupJID(xmpp)), false)) {
                boolean contains = xmpp.contains("<body>");
                if (contains && xmpp.contains("<preview>") && Tools.isBot(xmpp) && !Tools.getDisplayName(Tools.getAllTypes(xmpp)).equals("Rage bot")) {
                    remove(xmpp);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    removeReason(groupJID, "Bot detected");
                }
                if (contains) {
                    String allTypes = Tools.getAllTypes(xmpp);
                    if (checkAntiBot(allTypes)) {
                        if (Tools.isBotPhrase(Tools.getBody(xmpp))) {
                            remove(xmpp);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            removeReason(groupJID, "Bot detected");
                        }
                        setAntiBot(allTypes, false);
                    }
                }
            }
        }
    }

    public static void antiSpam(String xmpp) {
        if (!database.getString(PerChat.changeKeyIfEnabled("blue.antispam", Tools.getGroupJID(xmpp)), "Off").equals("Off") && !xmpp.contains("app-id=\"") && xmpp.contains("<body>") && setAndCheck(xmpp, Tools.getAllTypes(xmpp), Tools.getUnixTime()) && Tools.adminCheck(Tools.getGroupJID(xmpp))) {
            removeReason(Tools.getGroupJID(xmpp), "Removing for spamming...");
            String string = database.getString(PerChat.changeKeyIfEnabled("blue.antispam", Tools.getGroupJID(xmpp)), "Off");
            if (string.equals("Remove")) {
                remove(xmpp);
            }
            if (string.equals("Ban")) {
                ban(xmpp);
            }
        }
    }

    public static void autoAdd(String xmpp) {
        String str = "Off";
        if (!database.getString("blue.autoadd", str).equals(str)) {
            StringBuilder sb = new StringBuilder();
            sb.append("<iq type=\"set\" id=\"");
            String randomUUID = Tools.getRandomUUID();
            sb.append(randomUUID);
            AddID = randomUUID;
            sb.append("\"><query xmlns=\"kik:iq:friend\"><add jid=\"");
            sb.append(Tools.getJoinJID(xmpp));
            sb.append("\" /></query></iq>");
            byte[] bytes = sb.toString().getBytes();
            LoggingOutputStream loggingOutputStream = XmppSocketV2.KikNetSock;
            loggingOutputStream.write(bytes);
            loggingOutputStream.flush();
            GroupID = Tools.getSysJID(xmpp);
        }
    }

    public static void autoAddResult(String xmpp) {
        if (!database.getString("blue.autoadd", "Off").equals("Off")) {
            String str = AddID;
            if (str != null && xmpp.contains(str) && xmpp.startsWith("<iq") && xmpp.contains("type=\"result\"")) {
                String username = Tools.getUsername(xmpp);
                StringBuilder sb = new StringBuilder();
                sb.append("Hello, @");
                sb.append(username);
                String sb2 = sb.toString();
                String str2 = GroupID;
                if (database.getString("blue.autoadd", "Off").equals("Add Contact + Send In Group")) {
                    Tools.send(str2, sb2);
                }
                AddID = null;
                GroupID = null;
            }
        }
    }

    public static void botInit(String xmpp) {
        if (database.getBoolean(PerChat.changeKeyIfEnabled("blue.ragebot2", Tools.getSysJID(xmpp)), false) && Tools.adminCheck(Tools.getSysJID(xmpp)) && !xmpp.contains("invited by")) {
            setAntiBot(Tools.getJoinJID(xmpp), true);
        }
    }

    public static boolean checkAntiBot(String str) {
        return database.getBoolean("botc." + str, false);
    }

    public static void dmd(String xmpp) {
        if (database.getBoolean("blue.dmd", false) && xmpp.contains("You have joined the public group") && !xmpp.contains("<body>") && xmpp.endsWith("</sysmsg></message>")) {
            String randomUUID = Tools.getRandomUUID();
            LoggingOutputStream loggingOutputStream = XmppSocketV2.KikNetSock;
            loggingOutputStream.write(("<iq type=\"set\" id=\"" + randomUUID + "\"><query xmlns=\"kik:groups:admin\"><g jid=\"" + Tools.getSysJID(xmpp) + "\"><m dmd=\"1\">" + Tools.getPersonalJID() + "/null</m></g></query></iq>").getBytes());
            loggingOutputStream.flush();
        }
    }

    public static void iRapeLittleKids(String xmpp) {
        if (Tools.adminCheck(Tools.getSysJID(xmpp))) {
            String string = database.getString(PerChat.changeKeyIfEnabled("blue.antiLN", Tools.getSysJID(xmpp)), "Off");
            if (string.equals("Off") || !Tools.isNameTooLong(xmpp)) {
                String kikString = database.getKikString("blue.antiempty");
                if (!kikString.equals("Off") && Tools.isPictureEmpty(Tools.getJoinJID(xmpp))) {
                    removeReason(Tools.getSysJID(xmpp), "Empty profile picture, removing...");
                    lock(xmpp, kikString);
                    return;
                }
                return;
            }
            removeReason(Tools.getSysJID(xmpp), "Name too long, removing...");
            lock(xmpp, string);
        }
    }

    public static void leave(String xmpp) {
        String buildLeaveXMPP = Builders.buildLeaveXMPP(xmpp);
        if (!"not set!".equals(buildLeaveXMPP)) {
            Tools.send(Tools.getSysJID(xmpp), buildLeaveXMPP);
        }
    }

    public static void pick(String xmpp, int i) {
        autoAddResult(xmpp);
        if (xmpp.startsWith("<message")) {
            if (xmpp.contains("type=\"groupchat\"")) {
                dmd(xmpp);
                annoyingNiggersAreRektHere(xmpp);
                rageBot(xmpp);
                if (i != 0) {
                    antiSpam(xmpp);
                }
                if (xmpp.contains("</status>")) {
                    if (xmpp.contains(" has joined")) {
                        welcome(xmpp);
                        autoAdd(xmpp);
                        lock(xmpp, Builders.buildLockXMPP(xmpp));
                        iRapeLittleKids(xmpp);
                        botInit(xmpp);
                        return;
                    }
                    if (xmpp.contains("You have added")) {
                        welcome(xmpp);
                    }
                    if (xmpp.contains(" has left")) {
                        leave(xmpp);
                        return;
                    }
                } else {
                    return;
                }
            }
            if (xmpp.contains(" has changed the group name to ")) {
                lockName(xmpp);
            }
        }
    }

    public static void rageBot(String xmpp) {
        if (database.getBoolean(PerChat.changeKeyIfEnabled("blue.rage", Tools.getGroupJID(xmpp)), false) && xmpp.contains("<body>")) {
            String trim = Tools.getBody(xmpp).toLowerCase().trim();
            if (Builders.getCensor(xmpp, trim) || Builders.getTrigger(xmpp, trim)) {
                return;
            }
            if (trim.equals("ping")) {
                Tools.send(Tools.getGroupJID(xmpp), "pong\n\nsay commands for help");
            } else if (trim.equals("commands")) {
                Tools.send(Tools.getGroupJID(xmpp), "~Admin Commands~\n\"Censor (word)\" removes a user if they say the censored word\n\"Lock\" prevents users from joining the group\n\"Delete (trigger)\" deletes a specific trigger or censor\n\"Delete all\" deletes trigger and censored word lists\n\n~Regular commands~\n\"Ping\" checks if the bot is online\n\"Settings\" lists current bot settings\n\"Trigger > Response\" when someone says Trigger, bot says Response\n\"Triggers\" shows trigger and censored word lists\n\"Rules\" shows welcome message\n\"Leave\" shows leave message\n\"urban (search term)\" searches urban dictionary");
            } else if (trim.equals("settings")) {
                Tools.send(Tools.getGroupJID(xmpp), "Bot settings for this chat:\n\nWelcome message - " + Builders.buildWelcomeRage(xmpp) + "\n\nLeave message - " + Builders.buildLeaveRage(xmpp) + "\n\nGroup lock status - " + Builders.buildLockRage(xmpp) + "\n\nGroup name lock status - " + Builders.buildLockNameRage(xmpp));
            } else if (trim.startsWith("urban ")) {
                Tools.send(Tools.getGroupJID(xmpp), udshit.parse(trim.replace("urban ", "!ud ")));
            } else if (trim.equals("rules")) {
                Tools.send(Tools.getGroupJID(xmpp), Builders.buildWelcomeRage(xmpp));
            } else if (trim.equals("leave")) {
                Tools.send(Tools.getGroupJID(xmpp), Builders.buildLeaveRage(xmpp));
            } else if (trim.startsWith("censor ")) {
                Tools.send(Tools.getGroupJID(xmpp), Builders.setCensorRage(xmpp, trim));
            } else if (trim.equals("triggers")) {
                StringBuilder sb = new StringBuilder();
                sb.append("~Triggers~\n");
                sb.append(Builders.getTriggerList(xmpp).replace("No triggers yet!", ""));
                sb.append("\n\n~Censors~\n");
                String censorList = Builders.getCensorList(xmpp);
                if (censorList != null) {
                    sb.append(censorList.replace(",", "\n-"));
                }
                Tools.send(Tools.getGroupJID(xmpp), sb.toString().replace("\nnull", ""));
            } else if (trim.equals("delete all")) {
                Builders.clearCensorList(xmpp);
                Tools.send(Tools.getGroupJID(xmpp), "All triggers cleared");
            } else if (trim.equals("lock") && Tools.rageAdminCheck(xmpp)) {
                String groupJID = Tools.getGroupJID(xmpp);
                String str = "Users can no longer join the group.\n\nSay \"Unlock\" to allow joining";
                if (!Builders.lockGroup(xmpp)) {
                    str = "Group already locked";
                }
                Tools.send(groupJID, str);
            } else if (trim.equals("unlock") && Tools.rageAdminCheck(xmpp)) {
                String groupJID2 = Tools.getGroupJID(xmpp);
                String str2 = "Users can now join the group";
                if (!Builders.unlockGroup(xmpp)) {
                    str2 = "Group already unlocked";
                }
                Tools.send(groupJID2, str2);
            } else if (trim.startsWith("delete ")) {
                Tools.send(Tools.getGroupJID(xmpp), Builders.deleteTrigger(xmpp, trim));
                if (Builders.deleteCensor(xmpp, trim)) {
                    Tools.send(Tools.getGroupJID(xmpp), "Censor deleted");
                }
            } else if (trim.contains(" &gt; ") && Tools.rageTriggerCheck(xmpp)) {
                Tools.send(Tools.getGroupJID(xmpp), Builders.addTrigger(xmpp));
            }
        }
    }

    public static void removeFriend(String str) {
        String randomUUID = Tools.getRandomUUID();
        LoggingOutputStream loggingOutputStream = XmppSocketV2.KikNetSock;
        StringBuilder sb = new StringBuilder();
        sb.append("<iq type=\"set\" id=\"");
        sb.append(randomUUID);
        sb.append("\"><query xmlns=\"kik:iq:friend\"><remove jid=\"");
        sb.append(str);
        sb.append("\" /></query></iq>");
        loggingOutputStream.write(sb.toString().getBytes());
        loggingOutputStream.flush();
    }

    public static void removeReason(String jid, String body) {
        if (database.getBoolean(PerChat.changeKeyIfEnabled("blue.reason", jid), false)) {
            Tools.send(jid, body);
        }
    }

    public static boolean setAndCheck(String xmpp, String jid, String timestamp) {
        if (executables.getAntiSpamTemp1(jid).equals("null")) {
            executables.setAntiSpamTemp1(jid, timestamp);
        } else if (executables.getAntiSpamTemp2(jid).equals("null")) {
            executables.setAntiSpamTemp2(jid, timestamp);
        } else if (executables.getAntiSpamTemp3(jid).equals("null")) {
            executables.setAntiSpamTemp3(jid, timestamp);
        } else if (executables.getAntiSpamTemp4(jid).equals("null")) {
            executables.setAntiSpamTemp4(jid, timestamp);
        } else {
            boolean finalCheck = finalCheck(executables.getAntiSpamTemp4(jid), executables.getAntiSpamTemp1(jid));
            executables.clearAntiSpamTemps(jid);
            return finalCheck;
        }
        return false;
    }

    public static void setAntiBot(String str, boolean z) {
        database.setBoolean("botc." + str, z);
    }

    public static void setJoinTimestamp(String str) {
        database.setString("botTS." + str, Tools.getUnixTime());
    }

    public static void welcome(String xmpp) {
        StringBuilder sb = new StringBuilder();
        sb.append("welcome_");
        sb.append(Tools.getSysJID(xmpp));
        if (database.getBoolean(sb.toString(), false)) {
            String buildWelcomeXMPP = Builders.buildWelcomeXMPP(xmpp);
            if (!"not set!".equals(buildWelcomeXMPP)) {
                Tools.send(Tools.getSysJID(xmpp), buildWelcomeXMPP);
            }
        }
    }

    public static boolean isCensored(String body, String censoredWordList) {
        if (censoredWordList != null) {
            String str = ",";
            if (!censoredWordList.contains(str)) {
                return body.contains(censoredWordList);
            }
            String[] parts = censoredWordList.trim().split(str);
            for (String contains : parts) {
                if (body.contains(contains)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean strictSpam(String xmpp) {
        boolean z = false;
        if (!xmpp.contains("<body>")) {
            return false;
        }
        String body = Tools.getBody(xmpp);
        int a = body.length();
        int b = body.split("\n").length;
        if (a > 1750 || b > 20) {
            z = true;
        }
        return z;
    }

    public static boolean finalCheck(String strTemp4, String strTemp1) {
        if (Long.valueOf(strTemp4).longValue() - Long.valueOf(strTemp1).longValue() < 10000) {
            return true;
        }
        return false;
    }

    public static boolean moddedTS(String stamp) {
        long check = Long.parseLong(Tools.getUnixTime()) - Long.valueOf(stamp).longValue();
        StringBuilder sb = new StringBuilder();
        sb.append("Difference: ");
        sb.append(check);
        String sb2 = sb.toString();
        if (check <= 86400000 && check >= -86400000) {
            return false;
        }
        return true;
    }

    public static void ban(String xmpp) {
        String randomUUID = Tools.getRandomUUID();
        LoggingOutputStream loggingOutputStream = XmppSocketV2.KikNetSock;
        loggingOutputStream.write(("<iq type=\"set\" id=\"" + randomUUID + "\"><query xmlns=\"kik:groups:admin\"><g jid=\"" + Tools.getGroupJID(xmpp) + "\"><b>" + Tools.getAllTypes(xmpp) + "</b></g></query></iq>").getBytes());
        loggingOutputStream.flush();
    }

    public static void lock(String xmpp, String lockValue) {
        String lockValue2 = lockValue.toLowerCase();
        if (lockValue2.contains("remove")) {
            String randomUUID = Tools.getRandomUUID();
            LoggingOutputStream loggingOutputStream = XmppSocketV2.KikNetSock;
            loggingOutputStream.write(("<iq type=\"set\" id=\"" + randomUUID + "\"><query xmlns=\"kik:groups:admin\"><g jid=\"" + Tools.getSysJID(xmpp) + "\"><m r=\"1\">" + Tools.getJoinJID(xmpp) + "</m></g></query></iq>").getBytes());
            loggingOutputStream.flush();
        } else if (lockValue2.contains("ban")) {
            String randomUUID2 = Tools.getRandomUUID();
            LoggingOutputStream loggingOutputStream2 = XmppSocketV2.KikNetSock;
            loggingOutputStream2.write(("<iq type=\"set\" id=\"" + randomUUID2 + "\"><query xmlns=\"kik:groups:admin\"><g jid=\"" + Tools.getSysJID(xmpp) + "\"><b>" + Tools.getJoinJID(xmpp) + "</b></g></query></iq>").getBytes());
            loggingOutputStream2.flush();
        }
    }

    public static void lockName(String xmpp) {
        String buildLockNameXMPP = Builders.buildLockNameXMPP(xmpp);
        if (!"off".equals(buildLockNameXMPP)) {
            String randomUUID = Tools.getRandomUUID();
            LoggingOutputStream loggingOutputStream = XmppSocketV2.KikNetSock;
            loggingOutputStream.write(("<iq type=\"set\" id=\"" + randomUUID + "\"><query xmlns=\"kik:groups:admin\"><g jid=\"" + Tools.getSysJID(xmpp) + "\"><n>" + buildLockNameXMPP + "</n></g></query></iq>").getBytes());
            loggingOutputStream.flush();
        }
    }

    public static void remove(String xmpp) {
        String randomUUID = Tools.getRandomUUID();
        LoggingOutputStream loggingOutputStream = XmppSocketV2.KikNetSock;
        loggingOutputStream.write(("<iq type=\"set\" id=\"" + randomUUID + "\"><query xmlns=\"kik:groups:admin\"><g jid=\"" + Tools.getGroupJID(xmpp) + "\"><m r=\"1\">" + Tools.getAllTypes(xmpp) + "</m></g></query></iq>").getBytes());
        loggingOutputStream.flush();
    }
}
