package blue;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import blue.net.p;
import lynx.blue.widget.preferences.KikModalPreference;

public class About extends KikModalPreference {
    public About(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, null);
    }

    public boolean onPreferenceClick(Preference preference) {
        p.z(getContext());
        return true;
    }
}
