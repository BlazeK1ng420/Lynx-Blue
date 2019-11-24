package lynx.blue.widget.preferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;

public class KikEmptyPreference extends KikModalPreference {
    public KikEmptyPreference(Context context, AttributeSet attributeSet) {
        super(context);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }
}
