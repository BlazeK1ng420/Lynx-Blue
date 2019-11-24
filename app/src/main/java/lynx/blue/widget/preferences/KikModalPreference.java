package lynx.blue.widget.preferences;

import android.content.Context;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.AttributeSet;

public abstract class KikModalPreference extends Preference implements OnPreferenceClickListener {

    public KikModalPreference(Context context) {
        super(context);
    }

    public KikModalPreference(Context context, AttributeSet attributeSet, Object o) {
        super(context);
    }
}
