package musicdemo.jlang.com.mimu.dataloader;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import musicdemo.jlang.com.mimu.bean.Album;
import musicdemo.jlang.com.mimu.util.PreferencesUtility;

/**
 * Created by JLang on 2017/10/16.
 */

public class AlbumLoader {
    private static Observable<List<Album>> getAlbumsForCursor(final Cursor cursor) {
        return Observable.create(new ObservableOnSubscribe<List<Album>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Album>> e) throws Exception {
                List<Album> arrayList = new ArrayList<Album>();
                if ((cursor != null) && (cursor.moveToFirst()))
                    do {
                        String artistName = cursor.getString(2);
                        if (artistName.equals(MediaStore.UNKNOWN_STRING)) {
                            continue;
                        }
                        arrayList.add(new Album(cursor.getLong(0), cursor.getString(1), artistName, cursor.getLong(3), cursor.getInt(4), cursor.getInt(5)));
                    }
                    while (cursor.moveToNext());
                if (cursor != null) {
                    cursor.close();
                }
                e.onNext(arrayList);
                e.onComplete();
            }
        });
    }

    public static Observable<List<Album>> getAllAlbums(Context context) {
        return getAlbumsForCursor(makeAlbumCursor(context, null, null));
    }

    private static Cursor makeAlbumCursor(Context context, String selection, String[] paramArrayOfString) {
        final String albumSortOrder = PreferencesUtility.getInstance(context).getAlbumSortOrder();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, new String[]{"_id", "album", "artist", "artist_id", "numsongs", "minyear"}, selection, paramArrayOfString, albumSortOrder);

        return cursor;
    }
}
