package blue;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class Toast {
    public static Context context;
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void toast(String str) {
        final String str2 = str;
        handler.post(new Runnable() {
            public void run() {
                android.widget.Toast.makeText(Toast.context, str2, 1).show();
            }
        });
    }
}
