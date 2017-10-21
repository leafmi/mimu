package musicdemo.jlang.com.musicdemo.dataloader;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import musicdemo.jlang.com.musicdemo.bean.Artist;
import musicdemo.jlang.com.musicdemo.util.PreferencesUtility;

/**
 * Created by JLang on 2017/10/16.
 */

public class ArtistLoader {

    private static Observable<List<Artist>> getArtistsForCursor(final Cursor cursor) {
        return Observable.create(new ObservableOnSubscribe<List<Artist>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Artist>> e) throws Exception {
                List<Artist> arrayList = new ArrayList<Artist>();
                if ((cursor != null) && (cursor.moveToFirst()))
                    do {
                        String artistName = cursor.getString(1);
                        if (artistName.equals(MediaStore.UNKNOWN_STRING)) {
                            continue;
                        }
                        arrayList.add(new Artist(cursor.getLong(0), artistName, cursor.getInt(2), cursor.getInt(3)));
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

    public static Observable<List<Artist>> getAllArtists(Context context) {
        return getArtistsForCursor(makeArtistCursor(context, null, null));
    }

    private static Cursor makeArtistCursor(Context context, String selection, String[] paramArrayOfString) {
        final String artistSortOrder = PreferencesUtility.getInstance(context).getArtistSortOrder();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, new String[]{"_id", "artist", "number_of_albums", "number_of_tracks"}, selection, paramArrayOfString, artistSortOrder);
        return cursor;
    }
}
