package blue.luminosity;

import android.content.res.Resources;
import blue.Tools;

public class resourceRetriever {
    String credit = "this class was written by @.no_u on Kik";
    String explanation = "this class was created to programmatically get elements from res folder";

    private static Resources getRes() {
        return Tools.getContext().getResources();
    }

    public static String getPackageName() {
        return Tools.getContext().getPackageName();
    }

    public static int getLayout(String fileName) {
        return getRes().getIdentifier(fileName, "layout", getPackageName());
    }

    public static int getString(String stringName) {
        return getRes().getIdentifier(stringName, "string", getPackageName());
    }

    public static int getDrawable(String fileName) {
        return getRes().getIdentifier(fileName, "drawable", getPackageName());
    }

    public static int getMipmap(String fileName) {
        return getRes().getIdentifier(fileName, "mipmap", getPackageName());
    }

    public static int getStyle(String styleName) {
        return getRes().getIdentifier(styleName, "style", getPackageName());
    }

    public static int getColor(String colorName) {
        return getRes().getIdentifier(colorName, "color", getPackageName());
    }

    public static int getXml(String fileName) {
        return getRes().getIdentifier(fileName, "xml", getPackageName());
    }

    public static int getId(String idName) {
        return getRes().getIdentifier(idName, "id", getPackageName());
    }

    public static int getBool(String boolName) {
        return getRes().getIdentifier(boolName, "bools", getPackageName());
    }

    public static int getInteger(String intName) {
        return getRes().getIdentifier(intName, "integers", getPackageName());
    }
}
