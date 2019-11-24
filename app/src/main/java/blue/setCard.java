package blue;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import blue.luminosity.database;
import lynx.blue.chat.activity.FragmentWrapperActivity;
import lynx.blue.widget.preferences.KikPreference;


@SuppressLint("ResourceType")
public class setCard extends KikPreference {
    public setCard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, null);
    }

    protected void onBindView(View view) {
        super.onBindView(view);
         View findViewById = view.findViewById(2131297018);
    }

    public boolean onPreferenceClick(Preference preference) {
        if (Tools.checkperm()) {
            database.setBoolean("is.card.being.set", true);
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            FragmentWrapperActivity.fragment.startActivityForResult(Intent.createChooser(intent, "Select custom picture..."), 420);
        }
        return true;
    }
}
