package blue.flask.colorpicker.builder;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.ColorDrawable;
import android.text.InputFilter;
import android.text.InputFilter.AllCaps;
import android.text.InputFilter.LengthFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import blue.flask.colorpicker.ColorPickerView;
import blue.flask.colorpicker.ColorPickerView.WHEEL_TYPE;
import blue.flask.colorpicker.OnColorSelectedListener;
import blue.flask.colorpicker.slider.AlphaSlider;
import blue.flask.colorpicker.slider.LightnessSlider;

public class ColorPickerDialogBuilder {
    private AlphaSlider alphaSlider;
    private Builder builder;
    private EditText colorEdit;
    /* access modifiers changed from: private */
    public ColorPickerView colorPickerView;
    private LinearLayout colorPreview;
    private int defaultMargin = 0;
    private Integer[] initialColor = {null, null, null, null, null};
    private boolean isAlphaSliderEnabled = true;
    private boolean isColorEditEnabled = false;
    private boolean isLightnessSliderEnabled = true;
    private boolean isPreviewEnabled = false;
    private LightnessSlider lightnessSlider;
    private LinearLayout pickerContainer;
    private int pickerCount = 1;

    private ColorPickerDialogBuilder(Context context) {
        this.builder = new Builder(context);
        this.pickerContainer = new LinearLayout(context);
        this.pickerContainer.setOrientation(1);
        this.pickerContainer.setGravity(1);
        this.defaultMargin = getDimensionAsPx(context, 2131165618);
        LayoutParams layoutParamsForColorPickerView = new LayoutParams(-1, 0);
        layoutParamsForColorPickerView.weight = 1.0f;
        this.colorPickerView = new ColorPickerView(context);
        this.pickerContainer.addView(this.colorPickerView, layoutParamsForColorPickerView);
        this.builder.setView(this.pickerContainer);
    }

    public static ColorPickerDialogBuilder with(Context context) {
        return new ColorPickerDialogBuilder(context);
    }

    public ColorPickerDialogBuilder setTitle(String title) {
        this.builder.setTitle(title);
        return this;
    }

    public ColorPickerDialogBuilder initialColor(int initialColor2) {
        this.initialColor[0] = Integer.valueOf(initialColor2);
        return this;
    }

    public ColorPickerDialogBuilder initialColors(int[] initialColor2) {
        int i = 0;
        while (i < initialColor2.length && i < this.initialColor.length) {
            this.initialColor[i] = Integer.valueOf(initialColor2[i]);
            i++;
        }
        return this;
    }

    public ColorPickerDialogBuilder wheelType(WHEEL_TYPE wheelType) {
        this.colorPickerView.setRenderer(ColorWheelRendererBuilder.getRenderer(wheelType));
        return this;
    }

    public ColorPickerDialogBuilder density(int density) {
        this.colorPickerView.setDensity(density);
        return this;
    }

    public ColorPickerDialogBuilder setOnColorSelectedListener(OnColorSelectedListener onColorSelectedListener) {
        this.colorPickerView.addOnColorSelectedListener(onColorSelectedListener);
        return this;
    }

    public ColorPickerDialogBuilder setPositiveButton(CharSequence text, final ColorPickerClickListener onClickListener) {
        this.builder.setPositiveButton(text, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onClickListener.onClick(dialog, ColorPickerDialogBuilder.this.colorPickerView.getSelectedColor(), ColorPickerDialogBuilder.this.colorPickerView.getAllColors());
            }
        });
        return this;
    }

    public ColorPickerDialogBuilder setNegativeButton(CharSequence text, OnClickListener onClickListener) {
        this.builder.setNegativeButton(text, onClickListener);
        return this;
    }

    public ColorPickerDialogBuilder noSliders() {
        this.isLightnessSliderEnabled = false;
        this.isAlphaSliderEnabled = false;
        return this;
    }

    public ColorPickerDialogBuilder alphaSliderOnly() {
        this.isLightnessSliderEnabled = false;
        this.isAlphaSliderEnabled = true;
        return this;
    }

    public ColorPickerDialogBuilder lightnessSliderOnly() {
        this.isLightnessSliderEnabled = true;
        this.isAlphaSliderEnabled = false;
        return this;
    }

    public ColorPickerDialogBuilder showAlphaSlider(boolean showAlpha) {
        this.isAlphaSliderEnabled = showAlpha;
        return this;
    }

    public ColorPickerDialogBuilder showLightnessSlider(boolean showLightness) {
        this.isLightnessSliderEnabled = showLightness;
        return this;
    }

    public ColorPickerDialogBuilder showColorEdit(boolean showEdit) {
        this.isColorEditEnabled = showEdit;
        return this;
    }

    public ColorPickerDialogBuilder showColorPreview(boolean showPreview) {
        this.isPreviewEnabled = showPreview;
        if (!showPreview) {
            this.pickerCount = 1;
        }
        return this;
    }

    public ColorPickerDialogBuilder setPickerCount(int pickerCount2) throws IndexOutOfBoundsException {
        if (pickerCount2 < 1 || pickerCount2 > 5) {
            throw new IndexOutOfBoundsException("Picker Can Only Support 1-5 Colors");
        }
        this.pickerCount = pickerCount2;
        if (this.pickerCount > 1) {
            this.isPreviewEnabled = true;
        }
        return this;
    }

    public AlertDialog build() {
        Context context = this.builder.getContext();
        this.colorPickerView.setInitialColors(this.initialColor, getStartOffset(this.initialColor).intValue());
        if (this.isLightnessSliderEnabled) {
            LayoutParams layoutParamsForLightnessBar = new LayoutParams(-1, getDimensionAsPx(context, 2131165617));
            layoutParamsForLightnessBar.setMargins(this.defaultMargin, 0, this.defaultMargin, 0);
            this.lightnessSlider = new LightnessSlider(context);
            this.lightnessSlider.setLayoutParams(layoutParamsForLightnessBar);
            this.pickerContainer.addView(this.lightnessSlider);
            this.colorPickerView.setLightnessSlider(this.lightnessSlider);
            this.lightnessSlider.setColor(getStartColor(this.initialColor));
        }
        if (this.isAlphaSliderEnabled) {
            LayoutParams layoutParamsForAlphaBar = new LayoutParams(-1, getDimensionAsPx(context, 2131165617));
            layoutParamsForAlphaBar.setMargins(this.defaultMargin, 0, this.defaultMargin, 0);
            this.alphaSlider = new AlphaSlider(context);
            this.alphaSlider.setLayoutParams(layoutParamsForAlphaBar);
            this.pickerContainer.addView(this.alphaSlider);
            this.colorPickerView.setAlphaSlider(this.alphaSlider);
            this.alphaSlider.setColor(getStartColor(this.initialColor));
        }
        if (this.isColorEditEnabled) {
            LayoutParams layoutParamsForColorEdit = new LayoutParams(-2, -2);
            int padSide = getDimensionAsPx(context, 2131165618);
            layoutParamsForColorEdit.leftMargin = padSide;
            layoutParamsForColorEdit.rightMargin = padSide;
            this.colorEdit = (EditText) View.inflate(context, 2131493309, null);
            this.colorEdit.setFilters(new InputFilter[]{new AllCaps()});
            this.colorEdit.setSingleLine();
            this.colorEdit.setVisibility(8);
            this.colorEdit.setFilters(new InputFilter[]{new LengthFilter(9)});
            this.pickerContainer.addView(this.colorEdit, layoutParamsForColorEdit);
            EditText editText = this.colorEdit;
            StringBuilder sb = new StringBuilder();
            sb.append("#");
            sb.append(Integer.toHexString(getStartColor(this.initialColor)).toUpperCase());
            editText.setText(sb.toString());
            this.colorPickerView.setColorEdit(this.colorEdit);
        }
        if (this.isPreviewEnabled) {
            this.colorPreview = (LinearLayout) View.inflate(context, 2131493306, null);
            this.colorPreview.setVisibility(8);
            this.pickerContainer.addView(this.colorPreview);
            if (this.initialColor.length == 0) {
                ((ImageView) View.inflate(context, 2131493307, null)).setImageDrawable(new ColorDrawable(-1));
            } else {
                int i = 0;
                while (i < this.initialColor.length && i < this.pickerCount && this.initialColor[i] != null) {
                    LinearLayout colorLayout = (LinearLayout) View.inflate(context, 2131493307, null);
                    ((ImageView) colorLayout.findViewById(2131165255)).setImageDrawable(new ColorDrawable(this.initialColor[i].intValue()));
                    this.colorPreview.addView(colorLayout);
                    i++;
                }
            }
            this.colorPreview.setVisibility(0);
            this.colorPickerView.setColorPreview(this.colorPreview, getStartOffset(this.initialColor));
        }
        return this.builder.create();
    }

    private Integer getStartOffset(Integer[] colors) {
        int i = 0;
        Integer start = Integer.valueOf(0);
        while (i < colors.length && colors[i] != null) {
            start = Integer.valueOf((i + 1) / 2);
            i++;
        }
        return start;
    }

    private int getStartColor(Integer[] colors) {
        Integer startColor = getStartOffset(colors);
        if (startColor == null) {
            return -1;
        }
        return colors[startColor.intValue()].intValue();
    }

    private static int getDimensionAsPx(Context context, int rid) {
        return (int) (context.getResources().getDimension(rid) + 0.5f);
    }
}
