package blue.flask.colorpicker;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.ColorDrawable;
import blue.flask.colorpicker.builder.PaintBuilder;

public class CircleColorDrawable extends ColorDrawable {
    private Paint fillBackPaint = PaintBuilder.newPaint().shader(PaintBuilder.createAlphaPatternShader(16)).build();
    private Paint fillPaint = PaintBuilder.newPaint().style(Style.FILL).color(0).build();
    private Paint strokePaint = PaintBuilder.newPaint().style(Style.STROKE).stroke(this.strokeWidth).color(-1).build();
    private float strokeWidth;

    public CircleColorDrawable() {
    }

    public CircleColorDrawable(int color) {
        super(color);
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(0);
        float radius = ((float) canvas.getWidth()) / 2.0f;
        this.strokeWidth = radius / 12.0f;
        this.strokePaint.setStrokeWidth(this.strokeWidth);
        this.fillPaint.setColor(getColor());
        canvas.drawCircle(radius, radius, radius - (this.strokeWidth * 1.5f), this.fillBackPaint);
        canvas.drawCircle(radius, radius, radius - (this.strokeWidth * 1.5f), this.fillPaint);
        canvas.drawCircle(radius, radius, radius - this.strokeWidth, this.strokePaint);
    }

    public void setColor(int color) {
        super.setColor(color);
        invalidateSelf();
    }
}
