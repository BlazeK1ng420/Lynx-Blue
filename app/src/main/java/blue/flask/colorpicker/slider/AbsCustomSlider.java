package blue.flask.colorpicker.slider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;

public abstract class AbsCustomSlider extends View {
    protected Bitmap bar;
    protected Canvas barCanvas;
    protected int barHeight = 5;
    protected int barOffsetX;
    protected Bitmap bitmap;
    protected Canvas bitmapCanvas;
    protected int handleRadius = 20;
    protected OnValueChangedListener onValueChangedListener;
    protected float value = 1.0f;

    /* access modifiers changed from: protected */
    public abstract void drawBar(Canvas canvas);

    /* access modifiers changed from: protected */
    public abstract void drawHandle(Canvas canvas, float f, float f2);

    /* access modifiers changed from: protected */
    public abstract void onValueChanged(float f);

    public AbsCustomSlider(Context context) {
        super(context);
    }

    public AbsCustomSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsCustomSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void updateBar() {
        this.handleRadius = getDimension(2131165620);
        this.barHeight = getDimension(2131165617);
        this.barOffsetX = this.handleRadius;
        if (this.bar == null) {
            createBitmaps();
        }
        drawBar(this.barCanvas);
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void createBitmaps() {
        int width = getWidth();
        int height = getHeight();
        this.bar = Bitmap.createBitmap(width - (this.barOffsetX * 2), this.barHeight, Config.ARGB_8888);
        this.barCanvas = new Canvas(this.bar);
        if (this.bitmap == null || this.bitmap.getWidth() != width || this.bitmap.getHeight() != height) {
            if (this.bitmap != null) {
                this.bitmap.recycle();
            }
            this.bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            this.bitmapCanvas = new Canvas(this.bitmap);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.bar != null && this.bitmapCanvas != null) {
            this.bitmapCanvas.drawColor(0, Mode.CLEAR);
            this.bitmapCanvas.drawBitmap(this.bar, (float) this.barOffsetX, (float) ((getHeight() - this.bar.getHeight()) / 2), null);
            drawHandle(this.bitmapCanvas, ((float) this.handleRadius) + (this.value * ((float) (getWidth() - (this.handleRadius * 2)))), ((float) getHeight()) / 2.0f);
            canvas.drawBitmap(this.bitmap, 0.0f, 0.0f, null);
        }
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        updateBar();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = 0;
        if (widthMode == 0) {
            width = widthMeasureSpec;
        } else if (widthMode == Integer.MIN_VALUE) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else if (widthMode == 1073741824) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = 0;
        if (heightMode == 0) {
            height = heightMeasureSpec;
        } else if (heightMode == Integer.MIN_VALUE) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else if (heightMode == 1073741824) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        setMeasuredDimension(width, height);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
            case 2:
                this.value = (event.getX() - ((float) this.barOffsetX)) / ((float) this.bar.getWidth());
                this.value = Math.max(0.0f, Math.min(this.value, 1.0f));
                onValueChanged(this.value);
                invalidate();
                break;
            case 1:
                onValueChanged(this.value);
                if (this.onValueChangedListener != null) {
                    this.onValueChangedListener.onValueChanged(this.value);
                }
                invalidate();
                break;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public int getDimension(int id) {
        return getResources().getDimensionPixelSize(id);
    }

    public void setOnValueChangedListener(OnValueChangedListener onValueChangedListener2) {
        this.onValueChangedListener = onValueChangedListener2;
    }
}
