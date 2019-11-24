package blue;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import blue.net.p;
import lynx.blue.widget.preferences.KikModalPreference;

public class clearMedia extends KikModalPreference {
    public boolean onPreferenceClick(Preference preference) {
        p.galleryDropped(getContext());
        return true;
    }

    public clearMedia(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, null);
    }
}
