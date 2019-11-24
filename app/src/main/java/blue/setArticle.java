package blue;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.util.AttributeSet;
import blue.luminosity.database;
import lynx.blue.chat.activity.FragmentWrapperActivity;
import lynx.blue.widget.preferences.KikModalPreference;

public class setArticle extends KikModalPreference {
    public boolean onPreferenceClick(Preference preference) {
        if (Tools.checkperm()) {
            database.setBoolean("is.article.being.set", true);
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            FragmentWrapperActivity.fragment.startActivityForResult(Intent.createChooser(intent, "Select article image..."), 420);
        }
        return true;
    }

    public setArticle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, null);
    }
}
