package blue.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

@SuppressLint("ResourceType")
public class PerChat {
    public static void perChatButton(View view, final FragmentActivity fragmentActivity) {
        view.findViewById(2131297483).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                fragmentActivity.startActivity(new Intent(fragmentActivity, PerChatActivity.class));
            }
        });
    }
}
