package blue.flask.colorpicker.renderer;

import blue.flask.colorpicker.ColorCircle;
import java.util.List;

public interface ColorWheelRenderer {
    public static final float GAP_PERCENTAGE = 0.025f;

    void draw();

    List<ColorCircle> getColorCircleList();

    ColorWheelRenderOption getRenderOption();

    void initWith(ColorWheelRenderOption colorWheelRenderOption);
}
