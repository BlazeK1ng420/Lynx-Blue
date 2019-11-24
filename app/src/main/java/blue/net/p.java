package blue.net;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.Context;

@SuppressLint("ResourceType")
public class p {
    public static void color1(Context context) {
    }

    public static void bypassHelp(Context context) {
        Builder builder = new Builder(context);
        builder.setTitle("How To Use");
        builder.setIcon(2131231820);
        builder.setMessage("NOTE: You must grant the 'Storage' Permission for this to work!!\n\n24 Hour Bypass:\nTap 'select bypass image' and choose any image to send and it will send to yourself automatically. Forward the image into any groupchat!\n\nCustom Image:\nThis allows you to send a custom card to yourself, allowing you to change the text that appears below it (for example, camera pics say 'Camera' below them)\n\nNOTE: If 24h bypass doesn't work, log out / back in or restart the app and send it again. Kik tends to block new-ish accounts from sending GIFs in group chats.");
        builder.setPositiveButton("OKAY", new dc());
        builder.show();
    }

    public static void contactsDropped(Context context) {
        Builder builder = new Builder(context);
        builder.setTitle("Clear All Contacts");
        builder.setIcon(2131231820);
        builder.setMessage("Are you sure you want to clear your contacts? NOTE: This can take a moment.");
        builder.setNegativeButton("NO", new cancel());
        builder.setPositiveButton("YES", new cc());
        builder.show();
    }

    public static void emote(Context context) {
        Builder builder = new Builder(context);
        builder.setTitle("Emoticons");
        builder.setIcon(2131231820);
        builder.setMessage("Successfully unlocked all emoticons!");
        builder.setPositiveButton("OKAY", new dc());
        builder.show();
    }

    public static void emoteAlreadySet(Context context) {
        Builder builder = new Builder(context);
        builder.setTitle("Emoticons");
        builder.setIcon(2131231820);
        builder.setMessage("You have already unlocked them all!");
        builder.setPositiveButton("OKAY", new dc());
        builder.show();
    }


    public static void galleryDropped(Context context) {
        Builder builder = new Builder(context);
        builder.setTitle("Clear Gallery Messages");
        builder.setIcon(2131231820);
        builder.setMessage("Are you sure you want to clear your media messages?\n\nNOTE: It will restart after you clear them");
        builder.setNegativeButton("NO", new cancel());
        builder.setPositiveButton("YES", new cMedia());
        builder.show();
    }

    public static void messagesDropped(Context context) {
        Builder builder = new Builder(context);
        builder.setTitle("Clear Messages");
        builder.setIcon(2131231820);
        builder.setMessage("Are you sure you want to clear your messages?\n\nNOTE: It will restart after you clear them");
        builder.setNegativeButton("NO", new cancel());
        builder.setPositiveButton("YES", new cMessages());
        builder.show();
    }

    public static void rageHelp(Context context) {
        Builder builder = new Builder(context);
        builder.setTitle("Rage Premium (BETA)");
        builder.setIcon(2131231820);
        builder.setMessage("Type !bot to use from your own account\n\nTo use from another account: \n\n~Admin Commands~\n\"Censor (word)\" removes a user if they say the censored word\n\"Lock\" prevents users from joining the group\n\"Delete (trigger)\" deletes a specific trigger or censor\nDelete all\" deletes trigger and censored word lists\n\n~Regular commands~\n\"Ping\" checks if the bot is online\n\"Settings\" lists current bot settings\n\"Trigger > Response\" when someone says Trigger, bot says Response\n\"Triggers\" shows trigger and censored word lists\n\"Rules\" shows welcome message\n\"Leave\" shows leave message\n\"urban (search term)\" searches urban dictionary");
        builder.setPositiveButton("OKAY", new dc());
        builder.show();
    }

    public static void setArticle(Context context) {
        Builder builder = new Builder(context);
        builder.setTitle("Blue Is Leet");
        builder.setIcon(2131231820);
        builder.setMessage("Article Sent");
        builder.setPositiveButton("OKAY", new dc());
        builder.show();
    }

    public static void t(Context context) {
        Builder builder = new Builder(context);
        builder.setTitle("Patch Notes");
        builder.setIcon(2131231820);
        builder.setMessage(u.c("https://pastebin.com/raw/ub2s3GCs"));
        builder.setPositiveButton("OKAY", new dc());
        builder.show();
    }

    public static void z(Context context) {
        Builder builder = new Builder(context);
        builder.setTitle("About Blue Kik");
        builder.setIcon(2131231820);
        builder.setMessage(u.c("https://pastebin.com/raw/mADEw7d9"));
        builder.setPositiveButton("OKAY", new dc());
        builder.show();
    }
}
