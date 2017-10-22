package musicdemo.jlang.com.mimu.dataloader;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import musicdemo.jlang.com.mimu.bean.MusicInfo;
import musicdemo.jlang.com.mimu.util.PreferencesUtility;

/**
 * Created by JLang on 2017/10/16.
 */

public class SongLoader {

    public static Observable<List<MusicInfo>> getSongsForCursor(final Cursor cursor) {
        return Observable.create(new ObservableOnSubscribe<List<MusicInfo>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<MusicInfo>> e) throws Exception {
                List<MusicInfo> arrayList = new ArrayList<MusicInfo>();
                if ((cursor != null) && (cursor.moveToFirst()))
                    do {
                        long id = cursor.getLong(0);
                        String title = cursor.getString(1);
                        String artist = cursor.getString(2);
                        if (artist.equals(MediaStore.UNKNOWN_STRING)) {
                            continue;
                        }
                        String album = cursor.getString(3);
                        int duration = cursor.getInt(4);
                        int trackNumber = cursor.getInt(5);
                        long artistId = cursor.getInt(6);
                        long albumId = cursor.getLong(7);
                        String path = cursor.getString(8);
                        long size = cursor.getLong(9);
                        arrayList.add(new MusicInfo(id, albumId, artistId, title, artist, album, duration, trackNumber, path, size));
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

    public static Observable<List<MusicInfo>> getAllSongs(Context context) {
        return getSongsForCursor(makeSongCursor(context, null, null));
    }

    public static Observable<List<MusicInfo>> getSongListInFolder(Context context, String path) {
        String[] whereArgs = new String[]{path + "%"};
        return getSongsForCursor(makeSongCursor(context, MediaStore.Audio.Media.DATA + " LIKE ?", whereArgs, null));
    }


    public static Cursor makeSongCursor(Context context, String selection, String[] paramArrayOfString) {
        final String songSortOrder = PreferencesUtility.getInstance(context).getSongSortOrder();
        return makeSongCursor(context, selection, paramArrayOfString, songSortOrder);
    }

    private static Cursor makeSongCursor(Context context, String selection, String[] paramArrayOfString, String sortOrder) {
        String selectionStatement = "is_music=1 AND title != ''";

        if (!TextUtils.isEmpty(selection)) {
            selectionStatement = selectionStatement + " AND " + selection;
        }
        return context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{"_id", "title", "artist", "album", "duration", "track", "artist_id", "album_id", MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.SIZE},
                selectionStatement, paramArrayOfString, sortOrder);

    }

}
