package lynx.blue.widget.preferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;

public class KikPreferenceScreen extends KikModalPreference{
    public KikPreferenceScreen(Context context) {
        super(context);
    }

    public KikPreferenceScreen(Context context, AttributeSet attributeSet, Object o) {
        super(context);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }
}
