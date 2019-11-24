package blue.luminosity.preference;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import blue.luminosity.resourceRetriever;

public class DeveloperProfile extends Preference {
    private static final String ANDROIDNS = "http://schemas.android.com/apk/res/android";
    /* access modifiers changed from: private */
    public String alertMessage;
    /* access modifiers changed from: private */
    public String alertTitle;
    /* access modifiers changed from: private */
    public String proflie;

    public DeveloperProfile(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(resourceRetriever.getLayout("preference_dev_profile"));
        this.proflie = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "defaultValue");
        this.alertTitle = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "dialogTitle");
        this.alertMessage = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "dialogMessage");
    }

    /* access modifiers changed from: protected */
    public void onBindView(View view) {
        super.onBindView(view);
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse("http://kik.me/" + DeveloperProfile.this.proflie));
                DeveloperProfile.this.getContext().startActivity(intent);
            }
        });
        view.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                Builder aboutDev = new Builder(DeveloperProfile.this.getContext());
                aboutDev.setTitle(DeveloperProfile.this.alertTitle);
                aboutDev.setMessage(DeveloperProfile.this.alertMessage);
                aboutDev.setPositiveButton("View Profile", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setData(Uri.parse("http://kik.me/" + DeveloperProfile.this.proflie));
                        DeveloperProfile.this.getContext().startActivity(intent);
                    }
                });
                aboutDev.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                aboutDev.show();
                return false;
            }
        });
    }
}
