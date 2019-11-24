package blue.flask.colorpicker.renderer;

import android.graphics.Color;
import android.graphics.Paint;
import blue.flask.colorpicker.ColorCircle;
import blue.flask.colorpicker.builder.PaintBuilder;

public class SimpleColorWheelRenderer extends AbsColorWheelRenderer {
    private float[] hsv = new float[3];
    private Paint selectorFill = PaintBuilder.newPaint().build();

    public void draw() {
        int setSize = this.colorCircleList.size();
        float half = ((float) this.colorWheelRenderOption.targetCanvas.getWidth()) / 2.0f;
        int density = this.colorWheelRenderOption.density;
        float maxRadius = this.colorWheelRenderOption.maxRadius;
        int currentCount = 0;
        int i = 0;
        while (i < density) {
            float p = ((float) i) / ((float) (density - 1));
            float radius = maxRadius * p;
            float size = this.colorWheelRenderOption.cSize;
            int total = calcTotalCount(radius, size);
            int currentCount2 = currentCount;
            int currentCount3 = 0;
            while (currentCount3 < total) {
                int density2 = density;
                float maxRadius2 = maxRadius;
                int j = currentCount3;
                double angle = ((6.283185307179586d * ((double) currentCount3)) / ((double) total)) + ((3.141592653589793d / ((double) total)) * ((double) ((i + 1) % 2)));
                float x = ((float) (((double) radius) * Math.cos(angle))) + half;
                float y = ((float) (((double) radius) * Math.sin(angle))) + half;
                float p2 = p;
                this.hsv[0] = (float) ((180.0d * angle) / 3.141592653589793d);
                this.hsv[1] = radius / maxRadius2;
                this.hsv[2] = this.colorWheelRenderOption.lightness;
                this.selectorFill.setColor(Color.HSVToColor(this.hsv));
                this.selectorFill.setAlpha(getAlphaValueAsInt());
                this.colorWheelRenderOption.targetCanvas.drawCircle(x, y, size - this.colorWheelRenderOption.strokeWidth, this.selectorFill);
                if (currentCount2 >= setSize) {
                    this.colorCircleList.add(new ColorCircle(x, y, this.hsv));
                } else {
                    ((ColorCircle) this.colorCircleList.get(currentCount2)).set(x, y, this.hsv);
                }
                currentCount2++;
                currentCount3 = j + 1;
                density = density2;
                maxRadius = maxRadius2;
                p = p2;
            }
            float f = maxRadius;
            i++;
            currentCount = currentCount2;
        }
        float f2 = maxRadius;
    }
}
