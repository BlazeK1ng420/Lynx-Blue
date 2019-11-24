package blue.flask.colorpicker;

import android.graphics.Color;

public class ColorCircle {
    private int color;
    private float[] hsv = new float[3];
    private float[] hsvClone;
    private float x;
    private float y;

    public ColorCircle(float x2, float y2, float[] hsv2) {
        set(x2, y2, hsv2);
    }

    public double sqDist(float x2, float y2) {
        double dx = (double) (this.x - x2);
        double dy = (double) (this.y - y2);
        return (dx * dx) + (dy * dy);
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float[] getHsv() {
        return this.hsv;
    }

    public float[] getHsvWithLightness(float lightness) {
        if (this.hsvClone == null) {
            this.hsvClone = (float[]) this.hsv.clone();
        }
        this.hsvClone[0] = this.hsv[0];
        this.hsvClone[1] = this.hsv[1];
        this.hsvClone[2] = lightness;
        return this.hsvClone;
    }

    public void set(float x2, float y2, float[] hsv2) {
        this.x = x2;
        this.y = y2;
        this.hsv[0] = hsv2[0];
        this.hsv[1] = hsv2[1];
        this.hsv[2] = hsv2[2];
        this.color = Color.HSVToColor(this.hsv);
    }

    public int getColor() {
        return this.color;
    }
}
