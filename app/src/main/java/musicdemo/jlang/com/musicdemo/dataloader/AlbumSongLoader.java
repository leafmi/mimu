package musicdemo.jlang.com.musicdemo.dataloader;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import musicdemo.jlang.com.musicdemo.bean.MusicInfo;
import musicdemo.jlang.com.musicdemo.util.PreferencesUtility;

/**
 * Created by JLang on 2017/10/17.
 */

public class AlbumSongLoader {
    public static Observable<List<MusicInfo>> getSongsForAlbum(final Context context, final long albumID) {
        return Observable.create(new ObservableOnSubscribe<List<MusicInfo>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<MusicInfo>> e) throws Exception {
                Cursor cursor = makeAlbumSongCursor(context, albumID);
                List<MusicInfo> arrayList = new ArrayList<MusicInfo>();
                if ((cursor != null) && (cursor.moveToFirst()))
                    do {
                        long id = cursor.getLong(0);
                        String title = cursor.getString(1);
                        String artist = cursor.getString(2);
                        String album = cursor.getString(3);
                        int duration = cursor.getInt(4);
                        int trackNumber = cursor.getInt(5);
                        /*This fixes bug where some track numbers displayed as 100 or 200*/
                        while (trackNumber >= 1000) {
                            trackNumber -= 1000; //When error occurs the track numbers have an extra 1000 or 2000 added, so decrease till normal.
                        }
                        long artistId = cursor.getInt(6);
                        long albumId = albumID;

                        arrayList.add(new MusicInfo(id, albumId, artistId, title, artist, album, duration, trackNumber));
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


    private static Cursor makeAlbumSongCursor(Context context, long albumID) {
        ContentResolver contentResolver = context.getContentResolver();
        final String albumSongSortOrder = PreferencesUtility.getInstance(context).getAlbumSongSortOrder();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String string = "is_music=1 AND title != '' AND album_id=" + albumID;
        Cursor cursor = contentResolver.query(uri, new String[]{"_id", "title", "artist", "album", "duration", "track", "artist_id"}, string, null, albumSongSortOrder);
        return cursor;
    }
}
