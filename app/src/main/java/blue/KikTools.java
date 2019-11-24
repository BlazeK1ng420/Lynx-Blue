package blue;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import blue.luminosity.database;
import com.kik.core.network.xmpp.jid.BareJid;
import com.kik.entity.mobile.EntityService.GetUsersByAliasPayload;
import com.kik.entity.mobile.EntityService.PublicGroupMemberProfile;
import com.kik.util.Base64;
import java.util.Date;
import kik.core.chat.profile.NetworkProfileRepository;
import kik.core.datatypes.Message;
import kik.core.interfaces.IConversation;
import kik.core.interfaces.IProfile;
import kik.core.util.TimeUtils;
import lynx.blue.chat.KikApplication;
import lynx.blue.net.LoggingOutputStream;
import lynx.blue.net.communicator.XmppSocketV2;

public class KikTools {
    public static String chatJID;

    public static void clearContacts() {
        Cursor rawQuery = openKikDatabase().rawQuery("SELECT jid FROM KikContactsTable WHERE user_name IS NOT NULL AND user_name != 'Username unavailable' AND user_name != '';", null);
        if (rawQuery.getCount() == 0) {
            Toast.toast("Your contact list is empty.");
            return;
        }
        while (rawQuery.moveToNext()) {
            IProfile iProfile = KikApplication.u.C;
            iProfile.requestRemoveContact(iProfile.getContact(rawQuery.getString(0), true).getJid());
        }
        Toast.toast("Contacts list cleared");
    }

    public static void daysOnKik(String str) {
        if (str.startsWith("<iq") && str.contains("type=\"result\"") && str.contains("method=\"GetUsersByAlias\"") && str.contains("<body>")) {
            PublicGroupMemberProfile publicGroupMemberProfile = GetUsersByAliasPayload.parseFrom(Base64.decode(Tools.getBody(str), 16)).getPublicGroupMemberProfile();
            if (!publicGroupMemberProfile.hasRegistrationElement()) {
                Toast.toast("Does not have registration element.");
            } else if (!publicGroupMemberProfile.getRegistrationElement().hasCreationDate()) {
                Toast.toast("Has registration element but not creation date.");
            } else {
                daysOnKikToString(TimeUtils.timestampToDate(publicGroupMemberProfile.getRegistrationElement().getCreationDate()));
            }
        }
    }

    public static void demoteAll() {
        if (!Tools.ownerCheck(chatJID)) {
            Toast.toast("You're not the owner!");
            return;
        }
        SQLiteDatabase openKikDatabase = openKikDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT member_jid FROM memberTable WHERE group_id = '");
        sb.append(chatJID);
        sb.append("' AND permission_level = 'REGULAR_ADMIN' AND is_banned = '0';");
        Cursor rawQuery = openKikDatabase.rawQuery(sb.toString(), null);
        if (rawQuery.getCount() == 0) {
            Toast.toast("There are no members to demote.");
            return;
        }
        while (rawQuery.moveToNext()) {
            KikApplication.u.D.removeUserAsAdmin(rawQuery.getString(0), chatJID);
        }
        Toast.toast("All demoted");
    }

    public static void doNotDisturb(Message message) {
        if (database.getBoolean("vince.disturb", false)) {
            String sysMessage = message.getSysMessage();
            if (sysMessage != null) {
                String binId = message.getBinId();
                if (binId != null && Tools.getUsernameFromJID(binId).equals("GROUP")) {
                    if (sysMessage.endsWith(" has added you to the chat") || sysMessage.endsWith(" has added you to the group")) {
                        leaveGroup(binId);
                        Tools.send(binId, "Anti group add made by Blue Kik");
                    }
                }
            }
        }
    }

    public static String encodeRequest(String str) {
        String joinJID = Tools.getJoinJID(str);
        StringBuilder sb = new StringBuilder();
        sb.append("\n:\n8\n");
        sb.append(joinJID.replace("@talk.kik.com", ""));
        return Base64.encodeBytes(sb.toString().getBytes(), 16);
    }

    public static void getUserByAlias(String str) {
        Thread.sleep(1000);
        StringBuilder sb = new StringBuilder();
        sb.append("<iq type=\"set\" id=\"");
        sb.append(Tools.getRandomUUID());
        sb.append("\"><query xmlns=\"kik:iq:xiphias:bridge\" service=\"mobile.entity.v1.Entity\" method=\"GetUsersByAlias\"><body>");
        sb.append(encodeRequest(str));
        sb.append("</body></query></iq>");
        byte[] bytes = sb.toString().getBytes();
        LoggingOutputStream loggingOutputStream = XmppSocketV2.KikNetSock;
        loggingOutputStream.write(bytes);
        loggingOutputStream.flush();
    }

    public static void leaveGroup(String str) {
        IConversation iConversation = KikApplication.u.B;
        if (iConversation != null) {
            iConversation.leaveConversation(str);
        }
    }

    private static SQLiteDatabase openKikDatabase() {
        return Tools.getContext().openOrCreateDatabase(Tools.buildCoreID("kikDatabase.db"), 0, null);
    }

    public static void promoteAll() {
        if (!Tools.adminCheck(chatJID)) {
            Toast.toast("You're not admin!");
            return;
        }
        SQLiteDatabase openKikDatabase = openKikDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT member_jid FROM memberTable WHERE group_id = '");
        sb.append(chatJID);
        sb.append("' AND permission_level = 'BASIC' AND is_banned = '0';");
        Cursor rawQuery = openKikDatabase.rawQuery(sb.toString(), null);
        if (rawQuery.getCount() == 0) {
            Toast.toast("There are no members to promote.");
            return;
        }
        while (rawQuery.moveToNext()) {
            KikApplication.u.D.promoteUserToAdmin(rawQuery.getString(0), chatJID);
        }
        Toast.toast("All promoted");
    }

    public static void purge() {
        if (!Tools.adminCheck(chatJID)) {
            Toast.toast("You're not admin!");
            return;
        }
        SQLiteDatabase openKikDatabase = openKikDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT member_jid FROM memberTable WHERE group_id = '");
        sb.append(chatJID);
        sb.append("' AND permission_level = 'BASIC' AND is_banned = '0';");
        Cursor rawQuery = openKikDatabase.rawQuery(sb.toString(), null);
        if (rawQuery.getCount() == 0) {
            Toast.toast("There are no members to purge.");
            return;
        }
        while (rawQuery.moveToNext()) {
            KikApplication.u.D.kickBanUserFromGroup(rawQuery.getString(0), chatJID, true, false);
        }
        Toast.toast("Purge complete");
    }

    public static void purgeb() {
        if (!Tools.adminCheck(chatJID)) {
            Toast.toast("You're not admin!");
            return;
        }
        SQLiteDatabase openKikDatabase = openKikDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT member_jid FROM memberTable WHERE group_id = '");
        sb.append(chatJID);
        sb.append("' AND permission_level = 'BASIC' AND is_banned = '0';");
        Cursor rawQuery = openKikDatabase.rawQuery(sb.toString(), null);
        if (rawQuery.getCount() == 0) {
            Toast.toast("There are no members to purge.");
            return;
        }
        while (rawQuery.moveToNext()) {
            KikApplication.u.D.kickBanUserFromGroup(rawQuery.getString(0), chatJID, false, true);
        }
        Toast.toast("Purge complete");
    }

    public static void requestCreationDate(String str) {
        String joinJID = Tools.getJoinJID(str);
        NetworkProfileRepository.Repository.f.getUserProfilesByAlias(BareJid.fromString(joinJID));
        Log.e("Blue Kik", "Creation date attempted");
    }

    public static void unbanAll() {
        if (!Tools.adminCheck(chatJID)) {
            Toast.toast("You're not admin!");
            return;
        }
        SQLiteDatabase openKikDatabase = openKikDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT member_jid FROM memberTable WHERE group_id = '");
        sb.append(chatJID);
        sb.append("' AND permission_level = 'BASIC' AND is_banned = '1';");
        Cursor rawQuery = openKikDatabase.rawQuery(sb.toString(), null);
        if (rawQuery.getCount() == 0) {
            Toast.toast("There are no members to unban.");
            return;
        }
        while (rawQuery.moveToNext()) {
            KikApplication.u.D.kickBanUserFromGroup(rawQuery.getString(0), chatJID, false, false);
        }
        Toast.toast("All unbanned");
    }

    public static void daysOnKikToString(Date regDate) {
        Toast.toast(String.valueOf(TimeUtils.daysBeforeToday(regDate.getTime())));
    }
}
