package blue.flask.colorpicker;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import blue.ColorPicker;
import blue.flask.colorpicker.builder.ColorWheelRendererBuilder;
import blue.flask.colorpicker.builder.PaintBuilder;
import blue.flask.colorpicker.renderer.ColorWheelRenderOption;
import blue.flask.colorpicker.renderer.ColorWheelRenderer;
import blue.flask.colorpicker.slider.AlphaSlider;
import blue.flask.colorpicker.slider.LightnessSlider;
import java.util.ArrayList;
import java.util.Iterator;

public class ColorPickerView extends View {
    private static final float STROKE_RATIO = 2.0f;
    private float alpha = 1.0f;
    private Paint alphaPatternPaint = PaintBuilder.newPaint().build();
    private AlphaSlider alphaSlider;
    private int alphaSliderViewId;
    private int backgroundColor = 0;
    private EditText colorEdit;
    private LinearLayout colorPreview;
    private int colorSelection = 0;
    private TextWatcher colorTextChange = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                ColorPickerView.this.setColor(Color.parseColor(s.toString()), false);
            } catch (Exception e) {
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };
    private Bitmap colorWheel;
    private Canvas colorWheelCanvas;
    private Paint colorWheelFill = PaintBuilder.newPaint().color(0).build();
    private ColorCircle currentColorCircle;
    private int density = 10;
    private Integer initialColor;
    private Integer[] initialColors = {null, null, null, null, null};
    private float lightness = 1.0f;
    private LightnessSlider lightnessSlider;
    private int lightnessSliderViewId;
    private ArrayList<OnColorSelectedListener> listeners = new ArrayList<>();
    private ColorWheelRenderer renderer;
    private Paint selectorStroke1 = PaintBuilder.newPaint().color(-1).build();
    private Paint selectorStroke2 = PaintBuilder.newPaint().color(-16777216).build();

    public enum WHEEL_TYPE {
        FLOWER,
        CIRCLE;

        public static WHEEL_TYPE indexOf(int index) {
            switch (index) {
                case 0:
                    return FLOWER;
                case 1:
                    return CIRCLE;
                default:
                    return FLOWER;
            }
        }
    }

    public ColorPickerView(Context context) {
        super(context);
        initWith(context, null);
    }

    public ColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWith(context, attrs);
    }

    public ColorPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWith(context, attrs);
    }

    @TargetApi(21)
    public ColorPickerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs);
        initWith(context, attrs);
    }

    private void initWith(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, ColorPicker.getColorPickerPrefs());
        this.density = typedArray.getInt(2, 10);
        this.initialColor = Integer.valueOf(typedArray.getInt(3, -1));
        ColorWheelRenderer renderer2 = ColorWheelRendererBuilder.getRenderer(WHEEL_TYPE.indexOf(typedArray.getInt(9, 0)));
        this.alphaSliderViewId = typedArray.getResourceId(1, 0);
        this.lightnessSliderViewId = typedArray.getResourceId(5, 0);
        setRenderer(renderer2);
        setDensity(this.density);
        setInitialColor(this.initialColor.intValue(), true);
        typedArray.recycle();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.alphaSliderViewId != 0) {
            setAlphaSlider((AlphaSlider) getRootView().findViewById(this.alphaSliderViewId));
        }
        if (this.lightnessSliderViewId != 0) {
            setLightnessSlider((LightnessSlider) getRootView().findViewById(this.lightnessSliderViewId));
        }
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        updateColorWheel();
        this.currentColorCircle = findNearestByColor(this.initialColor.intValue());
    }

    private void updateColorWheel() {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (height < width) {
            width = height;
        }
        if (width > 0) {
            if (this.colorWheel == null) {
                this.colorWheel = Bitmap.createBitmap(width, width, Config.ARGB_8888);
                this.colorWheelCanvas = new Canvas(this.colorWheel);
                this.alphaPatternPaint.setShader(PaintBuilder.createAlphaPatternShader(8));
            }
            drawColorWheel();
            invalidate();
        }
    }

    private void drawColorWheel() {
        this.colorWheelCanvas.drawColor(0, Mode.CLEAR);
        if (this.renderer != null) {
            float half = ((float) this.colorWheelCanvas.getWidth()) / 2.0f;
            float maxRadius = (half - 2.05f) - (half / ((float) this.density));
            float cSize = (maxRadius / ((float) (this.density - 1))) / 2.0f;
            ColorWheelRenderOption colorWheelRenderOption = this.renderer.getRenderOption();
            colorWheelRenderOption.density = this.density;
            colorWheelRenderOption.maxRadius = maxRadius;
            colorWheelRenderOption.cSize = cSize;
            colorWheelRenderOption.strokeWidth = 2.05f;
            colorWheelRenderOption.alpha = this.alpha;
            colorWheelRenderOption.lightness = this.lightness;
            colorWheelRenderOption.targetCanvas = this.colorWheelCanvas;
            this.renderer.initWith(colorWheelRenderOption);
            this.renderer.draw();
        }
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
            height = widthMeasureSpec;
        } else if (heightMode == Integer.MIN_VALUE) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else if (widthMode == 1073741824) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        int squareDimen = width;
        if (height < width) {
            squareDimen = height;
        }
        setMeasuredDimension(squareDimen, squareDimen);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
            case 2:
                this.currentColorCircle = findNearestByPosition(event.getX(), event.getY());
                int selectedColor = getSelectedColor();
                this.initialColor = Integer.valueOf(selectedColor);
                setColorToSliders(selectedColor);
                invalidate();
                break;
            case 1:
                int selectedColor2 = getSelectedColor();
                if (this.listeners != null) {
                    Iterator it = this.listeners.iterator();
                    while (it.hasNext()) {
                        try {
                            ((OnColorSelectedListener) it.next()).onColorSelected(selectedColor2);
                        } catch (Exception e) {
                        }
                    }
                }
                setColorToSliders(selectedColor2);
                setColorText(selectedColor2);
                setColorPreviewColor(selectedColor2);
                invalidate();
                break;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(this.backgroundColor);
        if (this.colorWheel != null) {
            canvas.drawBitmap(this.colorWheel, 0.0f, 0.0f, null);
        }
        if (this.currentColorCircle != null) {
            float size = (((((float) canvas.getWidth()) / 2.0f) - 2.05f) / ((float) this.density)) / 2.0f;
            this.colorWheelFill.setColor(Color.HSVToColor(this.currentColorCircle.getHsvWithLightness(this.lightness)));
            this.colorWheelFill.setAlpha((int) (this.alpha * 255.0f));
            canvas.drawCircle(this.currentColorCircle.getX(), this.currentColorCircle.getY(), 2.0f * size, this.selectorStroke1);
            canvas.drawCircle(this.currentColorCircle.getX(), this.currentColorCircle.getY(), 1.5f * size, this.selectorStroke2);
            canvas.drawCircle(this.currentColorCircle.getX(), this.currentColorCircle.getY(), size, this.alphaPatternPaint);
            canvas.drawCircle(this.currentColorCircle.getX(), this.currentColorCircle.getY(), size, this.colorWheelFill);
        }
    }

    private ColorCircle findNearestByPosition(float x, float y) {
        ColorCircle near = null;
        double minDist = Double.MAX_VALUE;
        for (ColorCircle colorCircle : this.renderer.getColorCircleList()) {
            double dist = colorCircle.sqDist(x, y);
            if (minDist > dist) {
                minDist = dist;
                near = colorCircle;
            }
        }
        return near;
    }

    private ColorCircle findNearestByColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        ColorCircle near = null;
        double minDiff = Double.MAX_VALUE;
        char c = 0;
        double x = ((double) hsv[1]) * Math.cos((((double) hsv[0]) * 3.141592653589793d) / 180.0d);
        double y = ((double) hsv[1]) * Math.sin((((double) hsv[0]) * 3.141592653589793d) / 180.0d);
        for (ColorCircle colorCircle : this.renderer.getColorCircleList()) {
            float[] hsv1 = colorCircle.getHsv();
            float[] hsv2 = hsv;
            double y2 = y;
            double x1 = ((double) hsv1[1]) * Math.cos((((double) hsv1[c]) * 3.141592653589793d) / 180.0d);
            double dx = x - x1;
            double dy = y2 - (((double) hsv1[1]) * Math.sin((((double) hsv1[0]) * 3.141592653589793d) / 180.0d));
            double dist = (dx * dx) + (dy * dy);
            if (dist < minDiff) {
                minDiff = dist;
                near = colorCircle;
            }
            hsv = hsv2;
            y = y2;
            int i = color;
            c = 0;
        }
        double d = y;
        return near;
    }

    public int getSelectedColor() {
        int color = 0;
        if (this.currentColorCircle != null) {
            color = Color.HSVToColor(this.currentColorCircle.getHsvWithLightness(this.lightness));
        }
        return Utils.adjustAlpha(this.alpha, color);
    }

    public Integer[] getAllColors() {
        return this.initialColors;
    }

    public void setInitialColors(Integer[] colors, int selectedColor) {
        this.initialColors = colors;
        this.colorSelection = selectedColor;
        Integer initialColor2 = this.initialColors[this.colorSelection];
        if (initialColor2 == null) {
            initialColor2 = Integer.valueOf(-1);
        }
        setInitialColor(initialColor2.intValue(), true);
    }

    public void setInitialColor(int color, boolean updateText) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        this.alpha = Utils.getAlphaPercent(color);
        this.lightness = hsv[2];
        this.initialColors[this.colorSelection] = Integer.valueOf(color);
        this.initialColor = Integer.valueOf(color);
        setColorPreviewColor(color);
        setColorToSliders(color);
        if (this.colorEdit != null && updateText) {
            setColorText(color);
        }
        if (this.renderer.getColorCircleList() != null) {
            this.currentColorCircle = findNearestByColor(color);
        }
    }

    public void setLightness(float lightness2) {
        this.lightness = lightness2;
        this.initialColor = Integer.valueOf(Color.HSVToColor(Utils.alphaValueAsInt(this.alpha), this.currentColorCircle.getHsvWithLightness(lightness2)));
        if (this.colorEdit != null) {
            EditText editText = this.colorEdit;
            StringBuilder sb = new StringBuilder();
            sb.append("#");
            sb.append(Integer.toHexString(this.initialColor.intValue()).toUpperCase());
            editText.setText(sb.toString());
        }
        if (!(this.alphaSlider == null || this.initialColor == null)) {
            this.alphaSlider.setColor(this.initialColor.intValue());
        }
        updateColorWheel();
        invalidate();
    }

    public void setColor(int color, boolean updateText) {
        setInitialColor(color, updateText);
        updateColorWheel();
        invalidate();
    }

    public void setAlphaValue(float alpha2) {
        this.alpha = alpha2;
        this.initialColor = Integer.valueOf(Color.HSVToColor(Utils.alphaValueAsInt(this.alpha), this.currentColorCircle.getHsvWithLightness(this.lightness)));
        if (this.colorEdit != null) {
            EditText editText = this.colorEdit;
            StringBuilder sb = new StringBuilder();
            sb.append("#");
            sb.append(Integer.toHexString(this.initialColor.intValue()).toUpperCase());
            editText.setText(sb.toString());
        }
        if (!(this.lightnessSlider == null || this.initialColor == null)) {
            this.lightnessSlider.setColor(this.initialColor.intValue());
        }
        updateColorWheel();
        invalidate();
    }

    public void addOnColorSelectedListener(OnColorSelectedListener listener) {
        this.listeners.add(listener);
    }

    public void setLightnessSlider(LightnessSlider lightnessSlider2) {
        this.lightnessSlider = lightnessSlider2;
        if (lightnessSlider2 != null) {
            this.lightnessSlider.setColorPicker(this);
            this.lightnessSlider.setColor(getSelectedColor());
        }
    }

    public void setAlphaSlider(AlphaSlider alphaSlider2) {
        this.alphaSlider = alphaSlider2;
        if (alphaSlider2 != null) {
            this.alphaSlider.setColorPicker(this);
            this.alphaSlider.setColor(getSelectedColor());
        }
    }

    public void setColorEdit(EditText colorEdit2) {
        this.colorEdit = colorEdit2;
        if (this.colorEdit != null) {
            this.colorEdit.setVisibility(0);
            this.colorEdit.addTextChangedListener(this.colorTextChange);
        }
    }

    public void setDensity(int density2) {
        this.density = Math.max(2, density2);
        invalidate();
    }

    public void setRenderer(ColorWheelRenderer renderer2) {
        this.renderer = renderer2;
        invalidate();
    }

    public void setColorPreview(LinearLayout colorPreview2, Integer selectedColor) {
        if (colorPreview2 != null) {
            this.colorPreview = colorPreview2;
            if (selectedColor == null) {
                selectedColor = Integer.valueOf(0);
            }
            int children = colorPreview2.getChildCount();
            if (children != 0 && colorPreview2.getVisibility() == 0) {
                for (int i = 0; i < children; i++) {
                    View childView = colorPreview2.getChildAt(i);
                    if (childView instanceof LinearLayout) {
                        LinearLayout childLayout = (LinearLayout) childView;
                        if (i == selectedColor.intValue()) {
                            childLayout.setBackgroundColor(-1);
                        }
                        ImageView childImage = (ImageView) childLayout.findViewById(2131165255);
                        childImage.setClickable(true);
                        childImage.setTag(Integer.valueOf(i));
                        childImage.setOnClickListener(new OnClickListener() {
                            public void onClick(View v) {
                                if (v != null) {
                                    Object tag = v.getTag();
                                    if (tag != null && (tag instanceof Integer)) {
                                        ColorPickerView.this.setSelectedColor(((Integer) tag).intValue());
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    public void setSelectedColor(int previewNumber) {
        if (this.initialColors != null && this.initialColors.length >= previewNumber) {
            this.colorSelection = previewNumber;
            setHighlightedColor(previewNumber);
            Integer color = this.initialColors[previewNumber];
            if (color != null) {
                setColor(color.intValue(), true);
            }
        }
    }

    private void setHighlightedColor(int previewNumber) {
        int children = this.colorPreview.getChildCount();
        if (children != 0 && this.colorPreview.getVisibility() == 0) {
            for (int i = 0; i < children; i++) {
                View childView = this.colorPreview.getChildAt(i);
                if (childView instanceof LinearLayout) {
                    LinearLayout childLayout = (LinearLayout) childView;
                    if (i == previewNumber) {
                        childLayout.setBackgroundColor(-1);
                    } else {
                        childLayout.setBackgroundColor(0);
                    }
                }
            }
        }
    }

    private void setColorPreviewColor(int newColor) {
        if (this.colorPreview != null && this.initialColors != null && this.colorSelection <= this.initialColors.length && this.initialColors[this.colorSelection] != null && this.colorPreview.getChildCount() != 0 && this.colorPreview.getVisibility() == 0) {
            View childView = this.colorPreview.getChildAt(this.colorSelection);
            if (childView instanceof LinearLayout) {
                ((ImageView) ((LinearLayout) childView).findViewById(2131165255)).setImageDrawable(new CircleColorDrawable(newColor));
            }
        }
    }

    private void setColorText(int argb) {
        if (this.colorEdit != null) {
            EditText editText = this.colorEdit;
            StringBuilder sb = new StringBuilder();
            sb.append("#");
            sb.append(Integer.toHexString(argb));
            editText.setText(sb.toString());
        }
    }

    private void setColorToSliders(int selectedColor) {
        if (this.lightnessSlider != null) {
            this.lightnessSlider.setColor(selectedColor);
        }
        if (this.alphaSlider != null) {
            this.alphaSlider.setColor(selectedColor);
        }
    }
}
