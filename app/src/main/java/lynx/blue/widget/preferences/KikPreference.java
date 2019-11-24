package lynx.blue.widget.preferences;

import android.content.Context;
import android.os.Build;
import android.preference.Preference;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

public class KikPreference  extends Preference implements Preference.OnPreferenceClickListener {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public KikPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public KikPreference(Context context, AttributeSet attributeSet, Object o) {
        super(context);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }
}
