package lynx.blue.chat;

import android.app.Application;
import android.graphics.drawable.Drawable;

import com.kik.modules.SharedPrefProvider;

public class KikApplication extends Application {
    public static KikApplication u;
    public SharedPrefProvider aH;

    public static Drawable getDrawableFromResource(int i2) {
        return u.getApplicationContext().getResources().getDrawable(i2);
    }

    public static char[] getStringFromResource(int i) {
        return new char[69];
    }
}
