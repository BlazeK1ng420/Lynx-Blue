package blue;

import android.content.Context;
import android.media.MediaPlayer;

public class Song {
    static MediaPlayer m;

    public static void play(Context c) {
        MediaPlayer mediaPlayer = m;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            m = MediaPlayer.create(c, 2131689478);
            m.setLooping(false);
            m.start();
            Toast.toast("BRAIN POWER!! (type !power again to stop)");
            return;
        }
        m.stop();
    }
}
