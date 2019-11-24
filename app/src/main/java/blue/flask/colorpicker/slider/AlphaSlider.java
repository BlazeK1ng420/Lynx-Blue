package blue.flask.colorpicker.slider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import blue.flask.colorpicker.ColorPickerView;
import blue.flask.colorpicker.Utils;
import blue.flask.colorpicker.builder.PaintBuilder;

public class AlphaSlider extends AbsCustomSlider {
    private Paint alphaPatternPaint = PaintBuilder.newPaint().build();
    private Paint barPaint = PaintBuilder.newPaint().build();
    private Paint clearingStroke = PaintBuilder.newPaint().color(-1).xPerMode(Mode.CLEAR).build();
    public int color;
    private ColorPickerView colorPicker;
    private Paint solid = PaintBuilder.newPaint().build();

    public AlphaSlider(Context context) {
        super(context);
    }

    public AlphaSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void createBitmaps() {
        super.createBitmaps();
        this.alphaPatternPaint.setShader(PaintBuilder.createAlphaPatternShader(this.barHeight / 2));
    }

    /* access modifiers changed from: protected */
    public void drawBar(Canvas barCanvas) {
        int width = barCanvas.getWidth();
        int height = barCanvas.getHeight();
        barCanvas.drawRect(0.0f, 0.0f, (float) width, (float) height, this.alphaPatternPaint);
        int l = Math.max(2, width / 256);
        for (int x = 0; x <= width; x += l) {
            float alpha = ((float) x) / ((float) (width - 1));
            this.barPaint.setColor(this.color);
            this.barPaint.setAlpha(Math.round(255.0f * alpha));
            barCanvas.drawRect((float) x, 0.0f, (float) (x + l), (float) height, this.barPaint);
        }
    }

    /* access modifiers changed from: protected */
    public void onValueChanged(float value) {
        if (this.colorPicker != null) {
            this.colorPicker.setAlphaValue(value);
        }
    }

    /* access modifiers changed from: protected */
    public void drawHandle(Canvas canvas, float x, float y) {
        this.solid.setColor(this.color);
        this.solid.setAlpha(Math.round(this.value * 255.0f));
        canvas.drawCircle(x, y, (float) this.handleRadius, this.clearingStroke);
        if (this.value < 1.0f) {
            canvas.drawCircle(x, y, ((float) this.handleRadius) * 0.75f, this.alphaPatternPaint);
        }
        canvas.drawCircle(x, y, ((float) this.handleRadius) * 0.75f, this.solid);
    }

    public void setColorPicker(ColorPickerView colorPicker2) {
        this.colorPicker = colorPicker2;
    }

    public void setColor(int color2) {
        this.color = color2;
        this.value = Utils.getAlphaPercent(color2);
        if (this.bar != null) {
            updateBar();
            invalidate();
        }
    }
}
