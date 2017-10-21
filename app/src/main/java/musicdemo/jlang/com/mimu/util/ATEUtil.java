package musicdemo.jlang.com.mimu.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import musicdemo.jlang.com.mimu.R;

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
