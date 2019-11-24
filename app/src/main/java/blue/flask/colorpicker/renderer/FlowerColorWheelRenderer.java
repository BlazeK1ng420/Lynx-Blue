package blue.flask.colorpicker.renderer;

import android.graphics.Color;
import android.graphics.Paint;
import blue.flask.colorpicker.ColorCircle;
import blue.flask.colorpicker.builder.PaintBuilder;

public class FlowerColorWheelRenderer extends AbsColorWheelRenderer {
    private float[] hsv = new float[3];
    private Paint selectorFill = PaintBuilder.newPaint().build();
    private float sizeJitter = 1.2f;

    public void draw() {
        int setSize = this.colorCircleList.size();
        float f = 2.0f;
        float half = ((float) this.colorWheelRenderOption.targetCanvas.getWidth()) / 2.0f;
        int density = this.colorWheelRenderOption.density;
        float strokeWidth = this.colorWheelRenderOption.strokeWidth;
        float maxRadius = this.colorWheelRenderOption.maxRadius;
        float cSize = this.colorWheelRenderOption.cSize;
        int currentCount = 0;
        int i = 0;
        while (i < density) {
            float p = ((float) i) / ((float) (density - 1));
            float jitter = (((float) i) - (((float) density) / f)) / ((float) density);
            float radius = maxRadius * p;
            float size = Math.max(1.5f + strokeWidth, (i == 0 ? 0.0f : this.sizeJitter * cSize * jitter) + cSize);
            int total = Math.min(calcTotalCount(radius, size), density * 2);
            int currentCount2 = currentCount;
            int j = 0;
            while (j < total) {
                float p2 = p;
                float jitter2 = jitter;
                int total2 = total;
                int density2 = density;
                double angle = ((6.283185307179586d * ((double) j)) / ((double) total)) + ((3.141592653589793d / ((double) total)) * ((double) ((i + 1) % 2)));
                float x = ((float) (((double) radius) * Math.cos(angle))) + half;
                float y = ((float) (((double) radius) * Math.sin(angle))) + half;
                float half2 = half;
                double d = angle;
                this.hsv[0] = (float) ((180.0d * angle) / 3.141592653589793d);
                this.hsv[1] = radius / maxRadius;
                this.hsv[2] = this.colorWheelRenderOption.lightness;
                this.selectorFill.setColor(Color.HSVToColor(this.hsv));
                this.selectorFill.setAlpha(getAlphaValueAsInt());
                this.colorWheelRenderOption.targetCanvas.drawCircle(x, y, size - strokeWidth, this.selectorFill);
                if (currentCount2 >= setSize) {
                    this.colorCircleList.add(new ColorCircle(x, y, this.hsv));
                } else {
                    ((ColorCircle) this.colorCircleList.get(currentCount2)).set(x, y, this.hsv);
                }
                currentCount2++;
                j++;
                p = p2;
                jitter = jitter2;
                density = density2;
                total = total2;
                half = half2;
            }
            int i2 = density;
            i++;
            currentCount = currentCount2;
            f = 2.0f;
        }
        int i3 = density;
    }
}
