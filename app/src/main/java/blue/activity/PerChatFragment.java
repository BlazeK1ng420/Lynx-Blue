package blue.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import blue.KikTools;

@SuppressLint("ResourceType")
public class PerChatFragment extends PreferenceFragment {
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(2131951671);
        String str = KikTools.chatJID;
        if (str != null && str.endsWith("_g@groups.kik.com")) {
            addPreferencesFromResource(2131951672);
        }
        addPreferencesFromResource(2131951673);
    }
}
