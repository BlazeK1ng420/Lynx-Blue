package blue;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.text.TextUtils.TruncateAt;
import android.widget.ListView;
import android.widget.TextView;
import blue.luminosity.database;
import blue.luminosity.perchat.PerChat;
import lynx.blue.widget.ArcImageView;

public class IFuckedLinksAsshole {
    public static void fuckKikForMakingMeCodeThis(MediaRecorder mediaRecorder) {
        mediaRecorder.setMaxDuration(300000);
        mediaRecorder.setMaxFileSize(2147483647L);
    }

    //B1g sharedpreferences names (Not actual encryption just names) l0l
    public static String cloneView(String i) {
        String str = KikTools.chatJID;
        if (i.equals("com.kik.ext.gallery")) {
            return database.getBoolean(PerChat.changeKeyIfEnabled("lililii.ilili", str), false) ? "com.kik.ext.camera" : i;
        }
        if (!i.equals("com.kik.ext.video-gallery") || !database.getBoolean(PerChat.changeKeyIfEnabled("ililil.ililil", str), false)) {
            return i;
        }
        return "com.kik.ext.video-camera";
    }

    public static void extendDrawable(ArcImageView arcImageView, int i) {
        arcImageView.setSweepAngle((((float) i) / ((float) 300000)) * 360.0f);
    }

    public static void squarePictures(Drawable drawable, Canvas canvas, Paint paint) {
        Rect bounds = drawable.getBounds();
        if (database.getBoolean("blue.square", false)) {
            canvas.drawRect(bounds, paint);
        } else {
            canvas.drawCircle((float) (bounds.right / 2), (float) (bounds.bottom / 2), (float) (bounds.right / 2), paint);
        }
    }

    public static void setDivider(ListView view) {
        if (!database.getBoolean("blue.dividers", false)) {
            view.setDivider(new ColorDrawable(-2302756));
            view.setDividerHeight(1);
            return;
        }
        view.setDividerHeight(0);
    }

    public static void marqueeNames(TextView textView) {
        if (textView != null && database.getBoolean("blue.marquee", false)) {
            textView.setEllipsize(TruncateAt.MARQUEE);
            textView.setSelected(true);
            textView.setSingleLine(true);
            textView.setMarqueeRepeatLimit(-1);
        }
    }
}
