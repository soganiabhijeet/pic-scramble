package soganiabhijeet.com.picscramble.utils;

/**
 * Created by abhijeetsogani on 6/18/16.
 */
public class Constants {
    public static final String PIC_SCRAMBLE = "Pic_Scramble";
    public static final String CACHE_DIR = "Pic_Scramble Cache";
    public static final int COUNT_ZERO = 0;
    public static final int COUNT_ONE = 1;
    public static final int COUNT_EIGHT = 8;
    public static final int COUNT_NINE = 9;
    public static final int DELAY_SECONDS = 15;
    public static final String SERVICE_ENDPOINT = "https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1";

    public static final class GridStates {
        public static final int TIMER_RUNNING = 1;
        public static final int USER_PLAYING = 2;
        public static final int USER_WON = 3;
    }
}
