package blue.luminosity.preference;

import android.content.Context;
import android.preference.SwitchPreference;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import blue.luminosity.database;
import blue.luminosity.resourceRetriever;

public class Toggle extends SwitchPreference {
    private static final String ANDROIDNS = "http://schemas.android.com/apk/res/android";
    /* access modifiers changed from: private */
    public boolean enabled;
    /* access modifiers changed from: private */
    public String key;

    public Toggle(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(resourceRetriever.getLayout("preference_switch_layout"));
        this.key = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "key");
        if (getKey().equals("blue.spin")) {
            this.enabled = database.getBoolean(this.key, true);
        } else {
            this.enabled = database.getBoolean(this.key, false);
        }
    }

    /* access modifiers changed from: protected */
    public void onBindView(View view) {
        super.onBindView(view);
        final SwitchCompat toggleSwitch = (SwitchCompat) view.findViewById(resourceRetriever.getId("kik_switch"));
        toggleSwitch.setChecked(this.enabled);
        toggleSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                database.setBoolean(Toggle.this.key, isChecked);
                Toggle.this.enabled = !Toggle.this.enabled;
            }
        });
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                toggleSwitch.setChecked(!Toggle.this.enabled);
            }
        });
    }
}
