package musicdemo.jlang.com.mimu.util;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Build;

/**
 * Created by JLang on 2017/10/16.
 */

public class ListenerUtil {
    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static Uri getAlbumArtUri(long paramInt) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), paramInt);
    }
}
