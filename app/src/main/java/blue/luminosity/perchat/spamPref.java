package blue.luminosity.perchat;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import blue.luminosity.executables;
import lynx.blue.widget.preferences.KikModalPreference;

public class spamPref extends KikModalPreference {
    public spamPref(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, null);
    }

    public boolean onPreferenceClick(Preference preference) {
        executables.antiSpamPerChat(getContext());
        return true;
    }
}
