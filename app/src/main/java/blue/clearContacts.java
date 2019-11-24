package blue;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import blue.net.p;
import lynx.blue.widget.preferences.KikModalPreference;

public class clearContacts extends KikModalPreference {
    public boolean onPreferenceClick(Preference preference) {
        p.contactsDropped(getContext());
        return true;
    }

    public clearContacts(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, null);
    }
}
