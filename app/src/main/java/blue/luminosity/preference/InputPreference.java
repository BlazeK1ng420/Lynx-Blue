package blue.luminosity.preference;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import blue.luminosity.database;
import blue.luminosity.resourceRetriever;

public class InputPreference extends Preference {
    private static final String ANDROIDNS = "http://schemas.android.com/apk/res/android";
    /* access modifiers changed from: private */
    public String defaultValue;
    /* access modifiers changed from: private */
    public String key;
    /* access modifiers changed from: private */
    public String title;

    public InputPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(resourceRetriever.getLayout("preference_layout_modal"));
        this.key = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "key");
        this.defaultValue = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "defaultValue");
        this.title = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "title");
    }

    /* access modifiers changed from: protected */
    public void onBindView(View view) {
        super.onBindView(view);
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Builder editTextDialog = new Builder(InputPreference.this.getContext());
                final EditText userInput = new EditText(InputPreference.this.getContext());
                userInput.setHint(database.getString(InputPreference.this.key, InputPreference.this.defaultValue));
                editTextDialog.setTitle(InputPreference.this.title);
                editTextDialog.setView(userInput);
                editTextDialog.setPositiveButton("ENTER", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        database.setString(InputPreference.this.key, userInput.getText().toString());
                    }
                });
                editTextDialog.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                editTextDialog.show();
            }
        });
    }
}
