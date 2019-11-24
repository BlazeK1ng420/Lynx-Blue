package blue;

import android.graphics.drawable.Drawable;
import android.view.View;
import blue.luminosity.database;
import lynx.blue.chat.KikApplication;

public class Background {
    public static void a(View view) {
        String kikString = database.getKikString("blue.selectBG");
        if (kikString == null) {
            Toast.toast("You forget to set a gallery image!");
        } else {
            trySetBackground(view, Drawable.createFromPath(kikString));
        }
    }

    public static void b(View view) {
        String kikString = database.getKikString("blue.selectPresetBG");
        if (kikString != null) {
            int i = 2131231833;
            if (kikString.equals("2")) {
                i = 2131231834;
            } else if (kikString.equals("3")) {
                i = 2131231835;
            } else if (kikString.equals("4")) {
                i = 2131231836;
            } else if (kikString.equals("5")) {
                i = 2131231837;
            } else if (kikString.equals("6")) {
                i = 2131231838;
            } else if (kikString.equals("7")) {
                i = 2131231839;
            }
            trySetBackground(view, KikApplication.getDrawableFromResource(i));
        }
    }

    public static void set(View view) {
        if (Tools.isLeet(KikTools.chatJID)) {
            view.setBackground(KikApplication.getDrawableFromResource(2131231830));
            return;
        }
        String kikString = database.getKikString("blue.bgType");
        if (kikString == null) {
            return;
        }
        if (kikString.equals("Select from Gallery")) {
            a(view);
        } else if (kikString.equals("Select from Presets")) {
            b(view);
        }
    }

    private static void trySetBackground(View v, Drawable draw) {
        try {
            v.setBackground(draw);
        } catch (OutOfMemoryError e) {
            Toast.toast("Unable to set background, low on memory!");
        }
    }
}
