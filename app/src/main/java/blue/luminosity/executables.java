package blue.luminosity;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.Color;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import blue.KikTools;
import blue.luminosity.perchat.PerChat;

public class executables {

    static class antiLongName implements OnClickListener {
        antiLongName() {
        }

        public void onClick(DialogInterface dialog, int which) {
            database.setInt("blue.antiLNPref", which);
            database.setString("blue.antiLN", executables.intToString(which));
        }
    }

    static class antiLongNamePerChat implements OnClickListener {
        antiLongNamePerChat() {
        }

        public void onClick(DialogInterface dialog, int which) {
            String str = KikTools.chatJID;
            database.setInt(PerChat.changeKey("blue.antiLNPref", str), which);
            database.setString(PerChat.changeKey("blue.antiLN", str), executables.intToString(which));
        }
    }

    static class antiSpam implements OnClickListener {
        antiSpam() {
        }

        public void onClick(DialogInterface dialog, int which) {
            database.setInt("blue.antispamPref", which);
            database.setString("blue.antispam", executables.intToString(which));
        }
    }

    static class antiSpamPerChat implements OnClickListener {
        antiSpamPerChat() {
        }

        public void onClick(DialogInterface dialog, int which) {
            String str = KikTools.chatJID;
            database.setInt(PerChat.changeKey("blue.antispamPref", str), which);
            database.setString(PerChat.changeKey("blue.antispam", str), executables.intToString(which));
        }
    }

    static class antiTS implements OnClickListener {
        antiTS() {
        }

        public void onClick(DialogInterface dialog, int which) {
            database.setInt("blue.antiTSPref", which);
            database.setString("blue.antiTS", executables.intToString(which));
        }
    }

    static class antiTSPerChat implements OnClickListener {
        antiTSPerChat() {
        }

        public void onClick(DialogInterface dialog, int which) {
            String str = KikTools.chatJID;
            database.setInt(PerChat.changeKey("blue.antiTSPref", str), which);
            database.setString(PerChat.changeKey("blue.antiTS", str), executables.intToString(which));
        }
    }

    static class autoAdd implements OnClickListener {
        autoAdd() {
        }

        public void onClick(DialogInterface dialog, int which) {
            database.setInt("blue.autoAddPref", which);
            database.setString("blue.autoadd", executables.intToStringA(which));
        }
    }

    static class perChat implements OnClickListener {
        perChat() {
        }

        public void onClick(DialogInterface dialog, int which) {
            database.setInt(PerChat.changeKey("read.receipts", KikTools.chatJID), which);
        }
    }

    public static void clearAntiSpamTemps(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("antispam1.");
        sb.append(str);
        database.setString(sb.toString(), "null");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("antispam2.");
        sb2.append(str);
        database.setString(sb2.toString(), "null");
        StringBuilder sb3 = new StringBuilder();
        sb3.append("antispam3.");
        sb3.append(str);
        database.setString(sb3.toString(), "null");
        StringBuilder sb4 = new StringBuilder();
        sb4.append("antispam4.");
        sb4.append(str);
        database.setString(sb4.toString(), "null");
        StringBuilder sb5 = new StringBuilder();
        sb5.append("antispam5.");
        sb5.append(str);
        database.setString(sb5.toString(), "null");
    }

    public static String getAntiSpamTemp1(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("antispam1.");
        sb.append(str);
        return database.getString(sb.toString(), "null");
    }

    public static String getAntiSpamTemp2(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("antispam2.");
        sb.append(str);
        return database.getString(sb.toString(), "null");
    }

    public static String getAntiSpamTemp3(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("antispam3.");
        sb.append(str);
        return database.getString(sb.toString(), "null");
    }

    public static String getAntiSpamTemp4(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("antispam4.");
        sb.append(str);
        return database.getString(sb.toString(), "null");
    }

    public static String getAntiSpamTemp5(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("antispam5.");
        sb.append(str);
        return database.getString(sb.toString(), "null");
    }

    public static String getArticleName() {
        return database.getString("blue.articletext", "ATTENTION: Arctik is kinda the shit");
    }

    public static String getCrashLog() {
        return database.getString("crash.log", "null");
    }

    public static void setAntiSpamTemp1(String str, String timestamp) {
        StringBuilder sb = new StringBuilder();
        sb.append("antispam1.");
        sb.append(str);
        database.setString(sb.toString(), timestamp);
    }

    public static void setAntiSpamTemp2(String str, String timestamp) {
        StringBuilder sb = new StringBuilder();
        sb.append("antispam2.");
        sb.append(str);
        database.setString(sb.toString(), timestamp);
    }

    public static void setAntiSpamTemp3(String str, String timestamp) {
        StringBuilder sb = new StringBuilder();
        sb.append("antispam3.");
        sb.append(str);
        database.setString(sb.toString(), timestamp);
    }

    public static void setAntiSpamTemp4(String str, String timestamp) {
        StringBuilder sb = new StringBuilder();
        sb.append("antispam4.");
        sb.append(str);
        database.setString(sb.toString(), timestamp);
    }

    public static void setAntiSpamTemp5(String str, String timestamp) {
        StringBuilder sb = new StringBuilder();
        sb.append("antispam5.");
        sb.append(str);
        database.setString(sb.toString(), timestamp);
    }

    public static String intToString(int which) {
        String str = "Off";
        if (which == 0) {
            return "Off";
        }
        if (which == 1) {
            return "Remove";
        }
        if (which != 2) {
            return str;
        }
        return "Ban";
    }

    public static String intToStringA(int which) {
        String str = "Off";
        if (which == 0) {
            return "Off";
        }
        if (which == 1) {
            return "Add Contact Only";
        }
        if (which != 2) {
            return str;
        }
        return "Add Contact + Send In Group";
    }

    public static void spin(ImageView imageView) {
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(4500);
        anim.setRepeatCount(-1);
        imageView.startAnimation(anim);
        imageView.setImageResource(resourceRetriever.getDrawable("ic_settings"));
        imageView.setColorFilter(Color.parseColor("#ffffff"));
    }

    public static void readReceipts(Context c) {
        Builder read = new Builder(c);
        String[] options = {"Normal", "Delivered", "Stealth", "Super Stealth"};
        read.setTitle("Read Receipts");
        read.setSingleChoiceItems(options, database.getInt("read.receipts", 0), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                database.setInt("read.receipts", which);
            }
        });
        dismissButton(read, "CLOSE", true);
        read.show();
    }

    public static void readReceiptsPerChat(Context c) {
        Builder read = new Builder(c);
        String[] options = {"Normal", "Delivered", "Stealth", "Super Stealth"};
        read.setTitle("Read Receipts");
        read.setSingleChoiceItems(options, database.getInt(PerChat.changeKey("read.receipts", KikTools.chatJID), 0), new perChat());
        dismissButton(read, "CLOSE", true);
        read.show();
    }

    public static void antiLongName(Context context) {
        Builder builder = new Builder(context);
        String[] strArr = {"Off", "Remove", "Ban"};
        builder.setTitle("Anti Long Name");
        builder.setSingleChoiceItems(strArr, database.getInt("blue.antiLNPref", 0), new antiLongName());
        dismissButton(builder, "CLOSE", true);
        builder.show();
    }

    public static void antiLongNamePerChat(Context context) {
        Builder builder = new Builder(context);
        String[] strArr = {"Off", "Remove", "Ban"};
        builder.setTitle("Anti Long Name");
        builder.setSingleChoiceItems(strArr, database.getInt(PerChat.changeKey("blue.antiLNPref", KikTools.chatJID), 0), new antiLongNamePerChat());
        dismissButton(builder, "CLOSE", true);
        builder.show();
    }

    public static void antiSpam(Context context) {
        Builder builder = new Builder(context);
        String[] strArr = {"Off", "Remove", "Ban"};
        builder.setTitle("Anti Spam");
        builder.setSingleChoiceItems(strArr, database.getInt("blue.antispamPref", 0), new antiSpam());
        dismissButton(builder, "CLOSE", true);
        builder.show();
    }

    public static void antiSpamPerChat(Context context) {
        Builder builder = new Builder(context);
        String[] strArr = {"Off", "Remove", "Ban"};
        builder.setTitle("Anti Spam");
        builder.setSingleChoiceItems(strArr, database.getInt(PerChat.changeKey("blue.antispamPref", KikTools.chatJID), 0), new antiSpamPerChat());
        dismissButton(builder, "CLOSE", true);
        builder.show();
    }

    public static void antiTimestamp(Context context) {
        Builder builder = new Builder(context);
        String[] strArr = {"Off", "Remove", "Ban"};
        builder.setTitle("Anti Timestamp");
        builder.setSingleChoiceItems(strArr, database.getInt("blue.antiTSPref", 0), new antiTS());
        dismissButton(builder, "CLOSE", true);
        builder.show();
    }

    public static void antiTimestampPerChat(Context context) {
        Builder builder = new Builder(context);
        String[] strArr = {"Off", "Remove", "Ban"};
        builder.setTitle("Modded Timestamps");
        builder.setSingleChoiceItems(strArr, database.getInt(PerChat.changeKey("blue.antiTSPref", KikTools.chatJID), 0), new antiTSPerChat());
        dismissButton(builder, "CLOSE", true);
        builder.show();
    }

    public static void autoAdd(Context context) {
        Builder builder = new Builder(context);
        String[] strArr = {"Off", "Add Contact Only", "Add Contact + Send In Group"};
        builder.setTitle("Auto Add");
        builder.setSingleChoiceItems(strArr, database.getInt("blue.autoAddPref", 0), new autoAdd());
        dismissButton(builder, "CLOSE", true);
        builder.show();
    }

    public static String getCoreID() {
        return database.getString("core.id", "null");
    }

    public static String getFakeID() {
        return database.getString("bypass.ban", "null");
    }

    public static String getHint() {
        return database.getString("custom.hint", "Type !bot");
    }

    public static String getMatrikName() {
        return database.getString("matrik.name", "null");
    }

    public static String getPikekName() {
        return database.getString("pikek.name", "null");
    }

    public static String getAdminAllTarget() {
        return database.getString("adminall.target", "null");
    }

    public static String getBlocked() {
        return database.getString("blue.balled", "***Blocked Message***");
    }

    public static String getBrickTarget() {
        return database.getString("brick.target", "null");
    }

    public static String getImageAppName() {
        return database.getString("blue.giftext", "Stickers");
    }

    public static String getLeave(String str) {
        return database.getString(str, "not set!");
    }

    public static String getLock(String str) {
        return database.getString(str, "off");
    }

    public static String getLockName(String str) {
        return database.getString(str, "off");
    }

    public static String getPurgeTarget() {
        return database.getString("purge.target", "null");
    }

    public static String getSpamBody() {
        return database.getString("spam.body", "null");
    }

    public static String getSpamTarget() {
        return database.getString("spam.target", "null");
    }

    public static String getSplashPassword() {
        return database.getString("blue.passkey", "");
    }

    public static String getToast() {
        return database.getString("blue.toastText", "Welcome To Blue Kik 💙");
    }

    public static String getTyping() {
        return database.getString("custom.typing", "is typing...");
    }

    public static String getWelcome(String str) {
        return database.getString(str, "not set!");
    }

    public static void fakeCameraDialog(Context c) {
        Builder fake = new Builder(c);
        fake.setTitle("Fake Camera");
        String[] strArr = {"Pictures", "Videos"};
        final String support = support(0);
        final String support2 = support(1);
        fake.setMultiChoiceItems(strArr, new boolean[]{database.getBoolean(support2, false), database.getBoolean(support, false)}, new OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                switch (which) {
                    case 0:
                        database.setBoolean(support, isChecked);
                        return;
                    case 1:
                        database.setBoolean(support2, isChecked);
                        return;
                    default:
                        return;
                }
            }
        });
        dismissButton(fake, "CLOSE", true);
        fake.show();
    }

    public static void fakeCameraDialogPerChat(Context c) {
        Builder fake = new Builder(c);
        fake.setTitle("Fake Camera");
        String[] strArr = {"Pictures", "Videos"};
        String str = KikTools.chatJID;
        final String changeKey = PerChat.changeKey(support(0), str);
        final String changeKey2 = PerChat.changeKey(support(1), str);
        fake.setMultiChoiceItems(strArr, new boolean[]{database.getBoolean(changeKey2, false), database.getBoolean(changeKey, false)}, new OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                switch (which) {
                    case 0:
                        database.setBoolean(changeKey2, isChecked);
                        return;
                    case 1:
                        database.setBoolean(changeKey, isChecked);
                        return;
                    default:
                        return;
                }
            }
        });
        dismissButton(fake, "CLOSE", true);
        fake.show();
    }

    public static String getBubble() {
        return database.getString("blue.bubblechoice", "#6666ff");
    }

    public static String getMediaReply() {
        return database.getString("blue.replymedia", "Nice meme");
    }

    public static String getNigger() {
        return database.getString("pasty.boi", "Error");
    }

    public static String getReply() {
        return database.getString("blue.replytext", "Auto Replied");
    }

    public static String getSCReply() {
        String str = "Hello, thanks for adding me!";
        String string = database.getString("blue.screply", str);
        return string == null ? str : string;
    }

    public static String getWebsite() {
        return database.getString("blue.website", "https://google.com");
    }

    private static String support(int i) {
        int[] t;
        if (i == 0) {
            t = new int[]{105, 108, 105, 108, 105, 108, 46, 105, 108, 105, 108, 105, 108};
        } else if (i == 1) {
            t = new int[]{108, 105, 108, 105, 108, 105, 105, 46, 105, 108, 105, 108, 105};
        } else {
            t = new int[0];
        }
        StringBuilder w = new StringBuilder();
        for (int h : t) {
            w.append(Character.toString((char) h));
        }
        return w.toString();
    }

    private static void dismissButton(Builder dialog, String buttonText, boolean isButtonNegative) {
        if (isButtonNegative) {
            dialog.setNeutralButton(buttonText, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            dialog.setPositiveButton(buttonText, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
    }
}
