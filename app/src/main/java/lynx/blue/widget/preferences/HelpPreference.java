package lynx.blue.widget.preferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;

public class HelpPreference extends KikModalPreference {
    public HelpPreference(Context context, AttributeSet attributeSet) {
        super(context);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }
}
