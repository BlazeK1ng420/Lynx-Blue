package blue.flask.colorpicker.renderer;

import blue.flask.colorpicker.ColorCircle;
import java.util.ArrayList;
import java.util.List;

public abstract class AbsColorWheelRenderer implements ColorWheelRenderer {
    protected List<ColorCircle> colorCircleList;
    protected ColorWheelRenderOption colorWheelRenderOption;

    public void initWith(ColorWheelRenderOption colorWheelRenderOption2) {
        this.colorWheelRenderOption = colorWheelRenderOption2;
        this.colorCircleList = new ArrayList();
    }

    public ColorWheelRenderOption getRenderOption() {
        if (this.colorWheelRenderOption == null) {
            this.colorWheelRenderOption = new ColorWheelRenderOption();
        }
        return this.colorWheelRenderOption;
    }

    public List<ColorCircle> getColorCircleList() {
        return this.colorCircleList;
    }

    /* access modifiers changed from: protected */
    public int getAlphaValueAsInt() {
        return Math.round(this.colorWheelRenderOption.alpha * 255.0f);
    }

    /* access modifiers changed from: protected */
    public int calcTotalCount(float radius, float size) {
        return Math.max(1, (int) ((3.063052912151454d / Math.asin((double) (size / radius))) + 0.5d));
    }
}
