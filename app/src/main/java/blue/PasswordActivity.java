package blue;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import lynx.blue.chat.activity.IntroActivity;

public class PasswordActivity extends Activity {

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        startIntro();
    }

    public void checkPassword(View v) {
        startIntro();
    }

    public void startIntro() {
        startActivity(new Intent(this, IntroActivity.class));
    }
}
