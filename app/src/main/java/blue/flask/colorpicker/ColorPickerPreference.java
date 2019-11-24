package blue.flask.colorpicker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import blue.ColorPicker;
import blue.flask.colorpicker.ColorPickerView.WHEEL_TYPE;
import blue.flask.colorpicker.builder.ColorPickerClickListener;
import blue.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class ColorPickerPreference extends Preference {
    protected boolean alphaSlider;
    protected ImageView colorIndicator;
    protected int density;
    protected boolean lightSlider;
    private String pickerButtonCancel;
    private String pickerButtonOk;
    private String pickerTitle;
    /* access modifiers changed from: protected */
    public int selectedColor = 0;
    protected WHEEL_TYPE wheelType;

    public ColorPickerPreference(Context context) {
        super(context);
    }

    public ColorPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWith(context, attrs);
    }

    public ColorPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWith(context, attrs);
    }

    /* JADX INFO: finally extract failed */
    private void initWith(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, ColorPicker.getColorPickerPrefs());
        try {
            this.alphaSlider = typedArray.getBoolean(0, false);
            this.lightSlider = typedArray.getBoolean(4, false);
            this.density = typedArray.getInt(2, 10);
            this.wheelType = WHEEL_TYPE.indexOf(typedArray.getInt(9, 0));
            this.selectedColor = typedArray.getInt(3, -1);
            this.pickerTitle = typedArray.getString(8);
            if (this.pickerTitle == null) {
                this.pickerTitle = "Choose color";
            }
            this.pickerButtonCancel = typedArray.getString(6);
            if (this.pickerButtonCancel == null) {
                this.pickerButtonCancel = "cancel";
            }
            this.pickerButtonOk = typedArray.getString(7);
            if (this.pickerButtonOk == null) {
                this.pickerButtonOk = "ok";
            }
            typedArray.recycle();
            setWidgetLayoutResource(2131493308);
        } catch (Throwable th) {
            typedArray.recycle();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void onBindView(View view) {
        int tmpColor;
        super.onBindView(view);
        Resources res = view.getContext().getResources();
        GradientDrawable colorChoiceDrawable = null;
        this.colorIndicator = (ImageView) view.findViewById(2131297499);
        Drawable currentDrawable = this.colorIndicator.getDrawable();
        if (currentDrawable != null && (currentDrawable instanceof GradientDrawable)) {
            colorChoiceDrawable = (GradientDrawable) currentDrawable;
        }
        if (colorChoiceDrawable == null) {
            colorChoiceDrawable = new GradientDrawable();
            colorChoiceDrawable.setShape(1);
        }
        if (isEnabled()) {
            tmpColor = this.selectedColor;
        } else {
            tmpColor = darken(this.selectedColor, 0.5f);
        }
        colorChoiceDrawable.setColor(tmpColor);
        colorChoiceDrawable.setStroke((int) TypedValue.applyDimension(1, 1.0f, res.getDisplayMetrics()), darken(tmpColor, 0.8f));
        this.colorIndicator.setImageDrawable(colorChoiceDrawable);
    }

    public void setValue(int value) {
        if (callChangeListener(Integer.valueOf(value))) {
            this.selectedColor = value;
        }
    }

    /* access modifiers changed from: protected */
    public void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        setValue(restoreValue ? getPersistedInt(0) : ((Integer) defaultValue).intValue());
    }

    /* access modifiers changed from: protected */
    public void onClick() {
        ColorPickerDialogBuilder builder = ColorPickerDialogBuilder.with(getContext()).setTitle(this.pickerTitle).initialColor(this.selectedColor).wheelType(this.wheelType).density(this.density).setPositiveButton(this.pickerButtonOk, new ColorPickerClickListener() {
            public void onClick(DialogInterface dialog, int selectedColorFromPicker, Integer[] allColors) {
                ColorPickerPreference.this.setValue(selectedColorFromPicker);
            }
        }).setNegativeButton(this.pickerButtonCancel, null);
        if (!this.alphaSlider && !this.lightSlider) {
            builder.noSliders();
        } else if (!this.alphaSlider) {
            builder.lightnessSliderOnly();
        } else if (!this.lightSlider) {
            builder.alphaSliderOnly();
        }
        builder.build().show();
    }

    public static int darken(int color, float factor) {
        return Color.argb(Color.alpha(color), Math.max((int) (((float) Color.red(color)) * factor), 0), Math.max((int) (((float) Color.green(color)) * factor), 0), Math.max((int) (((float) Color.blue(color)) * factor), 0));
    }
}
