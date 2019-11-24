package blue.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

@SuppressLint("ResourceType")
public class PerChatActivity extends FragmentActivity {
    public void onBackPressed() {
        finish();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131493303);
        getFragmentManager().beginTransaction().replace(16908290, new PerChatFragment()).commit();
    }
}
