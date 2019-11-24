package blue.flask.colorpicker.slider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import blue.flask.colorpicker.ColorPickerView;
import blue.flask.colorpicker.Utils;
import blue.flask.colorpicker.builder.PaintBuilder;

public class LightnessSlider extends AbsCustomSlider {
    private Paint barPaint = PaintBuilder.newPaint().build();
    private Paint clearingStroke = PaintBuilder.newPaint().color(-1).xPerMode(Mode.CLEAR).build();
    private int color;
    private ColorPickerView colorPicker;
    private Paint solid = PaintBuilder.newPaint().build();

    public LightnessSlider(Context context) {
        super(context);
    }

    public LightnessSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LightnessSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void drawBar(Canvas barCanvas) {
        int width = barCanvas.getWidth();
        int height = barCanvas.getHeight();
        float[] hsv = new float[3];
        Color.colorToHSV(this.color, hsv);
        int l = Math.max(2, width / 256);
        for (int x = 0; x <= width; x += l) {
            hsv[2] = ((float) x) / ((float) (width - 1));
            this.barPaint.setColor(Color.HSVToColor(hsv));
            barCanvas.drawRect((float) x, 0.0f, (float) (x + l), (float) height, this.barPaint);
        }
    }

    /* access modifiers changed from: protected */
    public void onValueChanged(float value) {
        if (this.colorPicker != null) {
            this.colorPicker.setLightness(value);
        }
    }

    /* access modifiers changed from: protected */
    public void drawHandle(Canvas canvas, float x, float y) {
        this.solid.setColor(Utils.colorAtLightness(this.color, this.value));
        canvas.drawCircle(x, y, (float) this.handleRadius, this.clearingStroke);
        canvas.drawCircle(x, y, ((float) this.handleRadius) * 0.75f, this.solid);
    }

    public void setColorPicker(ColorPickerView colorPicker2) {
        this.colorPicker = colorPicker2;
    }

    public void setColor(int color2) {
        this.color = color2;
        this.value = Utils.lightnessOfColor(color2);
        if (this.bar != null) {
            updateBar();
            invalidate();
        }
    }
}
