package blue;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import blue.flask.colorpicker.ColorPickerPreference;
import blue.flask.colorpicker.builder.ColorPickerClickListener;
import blue.flask.colorpicker.builder.ColorPickerDialogBuilder;
import blue.luminosity.database;

public class ColorPicker extends ColorPickerPreference {
    private static final String ANDROIDNS = "http://schemas.android.com/apk/res/android";
    /* access modifiers changed from: private */
    public String key;
    private String title;

    public static String getValue(String str) {
        return database.getString(str, "#FFFFFF");
    }

    public static int[] getColorPickerPrefs() {
        return new int[]{2131230768, 2131230769, 2131230824, 2131230858, 2131230958, 2131230959, 2131230987, 2131230988, 2131230989, 2131231071};
    }

    public ColorPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.key = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "key");
        if (this.key.equals("blue.bubblecolor")) {
            this.title = "Set Bubble Color";
        } else if (this.key.equals("blue.textcolor")) {
            this.title = "Set Text Color";
        } else if (this.key.equals("blue.linktextcolor")) {
            this.title = "Set Link Text Color";
        }
        String value = getValue(this.key);
        setPersistent(false);
        if (value != null) {
            setDefaultValue(Integer.valueOf(Color.parseColor(value)));
        }
    }

    public static void setColorByKey(int color, String key2) {
        database.setString(key2, "#" + String.format("%08X", new Object[]{Integer.valueOf(color)}));
    }

    /* access modifiers changed from: protected */
    public void onBindView(View view) {
        super.onBindView(view);
        TextView textView = (TextView) view.findViewById(16908310);
        TextView textView2 = (TextView) view.findViewById(16908304);
        makeSelectable(view, Color.parseColor("#00000000"));
    }

    /* access modifiers changed from: protected */
    public void onClick() {
        ColorPickerDialogBuilder.with(getContext()).setTitle(this.title).initialColor(this.selectedColor).wheelType(this.wheelType).density(this.density).setPositiveButton("Ok", new ColorPickerClickListener() {
            public void onClick(DialogInterface dialog, int selectedColorFromPicker, Integer[] allColors) {
                ColorPicker.this.setValue(selectedColorFromPicker);
                ColorPicker.setColorByKey(ColorPicker.this.selectedColor, ColorPicker.this.key);
            }
        }).setNegativeButton("Cancel", null).showColorEdit(true).build().show();
    }

    public static void makeSelectable(View v, int fgColor) {
        if (VERSION.SDK_INT >= 21) {
            v.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{fgColor}), null, null));
            return;
        }
        StateListDrawable slDrawable = new StateListDrawable();
        ColorDrawable selected = new ColorDrawable(fgColor);
        slDrawable.addState(new int[]{16842912}, selected);
        slDrawable.addState(new int[]{16842919}, selected);
        slDrawable.addState(new int[]{16842908}, selected);
        v.setBackground(slDrawable);
    }
}
