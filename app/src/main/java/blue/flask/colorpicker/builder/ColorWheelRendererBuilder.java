package blue.flask.colorpicker.builder;

import blue.flask.colorpicker.ColorPickerView.WHEEL_TYPE;
import blue.flask.colorpicker.renderer.ColorWheelRenderer;
import blue.flask.colorpicker.renderer.FlowerColorWheelRenderer;
import blue.flask.colorpicker.renderer.SimpleColorWheelRenderer;

public class ColorWheelRendererBuilder {
    public static ColorWheelRenderer getRenderer(WHEEL_TYPE wheelType) {
        switch (wheelType) {
            case CIRCLE:
                return new SimpleColorWheelRenderer();
            case FLOWER:
                return new FlowerColorWheelRenderer();
            default:
                throw new IllegalArgumentException("wrong WHEEL_TYPE");
        }
    }
}
