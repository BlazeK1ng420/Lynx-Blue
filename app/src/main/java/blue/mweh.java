package blue;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import lynx.blue.widget.preferences.KikModalPreference;

public class mweh extends KikModalPreference {
    public boolean onPreferenceClick(Preference preference) {
        return true;
    }

    public mweh(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, null);
    }
}
