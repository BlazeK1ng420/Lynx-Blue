package blue.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import blue.Tools;
import blue.luminosity.database;
import java.util.Objects;

@SuppressLint("ResourceType")
public class OffReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "android.intent.action.SCREEN_OFF")) {
            if (database.getBoolean("clear.cache", false)) {
                Tools.clearCache();
            }
            if (database.getBoolean("blue.offlock", false)) {
                database.setBoolean("should.app.lock", true);
                Intent intent2 = new Intent("exit_screen");
                intent2.addCategory("android.intent.action.MAIN");
                intent2.setFlags(268435456);
                context.startActivity(intent2);
                System.exit(0);
            }
        }
    }
}
