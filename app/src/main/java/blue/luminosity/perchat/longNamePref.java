package blue.luminosity.perchat;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import blue.luminosity.executables;
import lynx.blue.widget.preferences.KikModalPreference;

public class longNamePref extends KikModalPreference {
    public longNamePref(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, null);
    }

    public boolean onPreferenceClick(Preference preference) {
        executables.antiLongNamePerChat(getContext());
        return true;
    }
}
