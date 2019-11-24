package blue;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import lynx.blue.widget.preferences.KikModalPreference;

public class clearCache extends KikModalPreference {
    public boolean onPreferenceClick(Preference preference) {
        Tools.clearCache();
        Toast.toast("Cache cleared");
        return true;
    }

    public clearCache(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, null);
    }
}
