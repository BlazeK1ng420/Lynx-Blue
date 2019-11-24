package blue.flask.colorpicker.builder;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;

public class PaintBuilder {

    public static class PaintHolder {
        private Paint paint;

        private PaintHolder() {
            this.paint = new Paint(1);
        }

        public PaintHolder color(int color) {
            this.paint.setColor(color);
            return this;
        }

        public PaintHolder antiAlias(boolean flag) {
            this.paint.setAntiAlias(flag);
            return this;
        }

        public PaintHolder style(Style style) {
            this.paint.setStyle(style);
            return this;
        }

        public PaintHolder mode(Mode mode) {
            this.paint.setXfermode(new PorterDuffXfermode(mode));
            return this;
        }

        public PaintHolder stroke(float width) {
            this.paint.setStrokeWidth(width);
            return this;
        }

        public PaintHolder xPerMode(Mode mode) {
            this.paint.setXfermode(new PorterDuffXfermode(mode));
            return this;
        }

        public PaintHolder shader(Shader shader) {
            this.paint.setShader(shader);
            return this;
        }

        public Paint build() {
            return this.paint;
        }
    }

    public static PaintHolder newPaint() {
        return new PaintHolder();
    }

    public static Shader createAlphaPatternShader(int size) {
        return new BitmapShader(createAlphaBackgroundPattern(Math.max(8, (size / 2) * 2)), TileMode.REPEAT, TileMode.REPEAT);
    }

    private static Bitmap createAlphaBackgroundPattern(int size) {
        Paint alphaPatternPaint = newPaint().build();
        Bitmap bm = Bitmap.createBitmap(size, size, Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        int s = Math.round(((float) size) / 2.0f);
        int j = 0;
        while (true) {
            int i = j;
            if (i >= 2) {
                return bm;
            }
            int j2 = 0;
            while (true) {
                int j3 = j2;
                if (j3 >= 2) {
                    break;
                }
                if ((i + j3) % 2 == 0) {
                    alphaPatternPaint.setColor(-1);
                } else {
                    alphaPatternPaint.setColor(-3092272);
                }
                c.drawRect((float) (i * s), (float) (j3 * s), (float) ((i + 1) * s), (float) ((j3 + 1) * s), alphaPatternPaint);
                j2 = j3 + 1;
            }
            j = i + 1;
        }
    }
}
