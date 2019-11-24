package blue.flask.colorpicker;

import android.graphics.Color;

public class Utils {
    public static float getAlphaPercent(int argb) {
        return ((float) Color.alpha(argb)) / 255.0f;
    }

    public static int alphaValueAsInt(float alpha) {
        return Math.round(255.0f * alpha);
    }

    public static int adjustAlpha(float alpha, int color) {
        return (alphaValueAsInt(alpha) << 24) | (16777215 & color);
    }

    public static int colorAtLightness(int color, float lightness) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] = lightness;
        return Color.HSVToColor(hsv);
    }

    public static float lightnessOfColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return hsv[2];
    }
}
