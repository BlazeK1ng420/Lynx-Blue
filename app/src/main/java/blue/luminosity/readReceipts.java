package blue.luminosity;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import lynx.blue.widget.preferences.KikModalPreference;

public class readReceipts extends KikModalPreference {
    public readReceipts(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, null);
    }

    public boolean onPreferenceClick(Preference preference) {
        executables.readReceipts(getContext());
        return true;
    }
}
