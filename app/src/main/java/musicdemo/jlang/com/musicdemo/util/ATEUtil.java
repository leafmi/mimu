package musicdemo.jlang.com.musicdemo.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import musicdemo.jlang.com.musicdemo.R;

/**
 * Created by JLang on 2017/10/16.
 */

public class ATEUtil {
    public static Drawable getDefaultAlbumDrawable(Context context) {
        return context.getResources().getDrawable(R.drawable.icon_album_default);
    }

    public static Drawable getDefaultArtistDrawable(Context context) {
        return context.getResources().getDrawable(R.drawable.icon_album_default);
    }
}
