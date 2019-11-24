package blue;

import blue.luminosity.database;
import kik.core.datatypes.messageExtensions.ContentMessage;

public class Media {
    public static boolean bypass() {
        return false;
    }

    public static boolean bypassForward(int i) {
        return true;
    }

    public static String forward() {
        return database.getBoolean("blue.forward", false) ? "false" : "true";
    }

    public static String loop() {
        return database.getBoolean("blue.loop", false) ? "true" : "false";
    }

    public static String mute() {
        return database.getBoolean("blue.mute", false) ? "true" : "false";
    }

    public static String play() {
        return database.getBoolean("blue.play", false) ? "true" : "false";
    }

    public static String save() {
        return database.getBoolean("blue.save", false) ? "true" : "false";
    }

    public static void setMedia(ContentMessage contentMessage) {
        contentMessage.setSaveDisallowed(save());
        contentMessage.setIsAutoplayVideo(play());
        contentMessage.setIsLoopingVideo(loop());
        contentMessage.setVideoShouldBeMuted(mute());
    }
}
