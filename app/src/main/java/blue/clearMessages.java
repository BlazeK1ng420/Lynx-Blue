package blue;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import blue.net.p;
import lynx.blue.widget.preferences.KikModalPreference;

public class clearMessages extends KikModalPreference {
    public boolean onPreferenceClick(Preference preference) {
        p.messagesDropped(getContext());
        return true;
    }

    public clearMessages(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, null);
    }
}
