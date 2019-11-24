package blue;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import lynx.blue.widget.preferences.KikModalPreference;

public class playThatShit extends KikModalPreference {
    public boolean onPreferenceClick(Preference preference) {
        Song.play(Tools.getContext());
        return true;
    }

    public playThatShit(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, null);
    }
}
