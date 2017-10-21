package musicdemo.jlang.com.mimu.dataloader;

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
import musicdemo.jlang.com.mimu.bean.MusicInfo;
import musicdemo.jlang.com.mimu.util.PreferencesUtility;

/**
 * Created by JLang on 2017/10/17.
 */

public class ArtistSongLoader {
    public static Observable<List<MusicInfo>> getSongsForArtist(final Context context, final long artistID) {
        return Observable.create(new ObservableOnSubscribe<List<MusicInfo>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<MusicInfo>> e) throws Exception {
                Cursor cursor = makeArtistSongCursor(context, artistID);
                List<MusicInfo> songsList = new ArrayList<MusicInfo>();
                if ((cursor != null) && (cursor.moveToFirst()))
                    do {
                        long id = cursor.getLong(0);
                        String title = cursor.getString(1);
                        String artist = cursor.getString(2);
                        String album = cursor.getString(3);
                        int duration = cursor.getInt(4);
                        int trackNumber = cursor.getInt(5);
                        long albumId = cursor.getInt(6);
                        long artistId = artistID;

                        songsList.add(new MusicInfo(id, albumId, artistId, title, artist, album, duration, trackNumber));
                    }
                    while (cursor.moveToNext());
                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
                e.onNext(songsList);
                e.onComplete();
            }
        });
    }


    private static Cursor makeArtistSongCursor(Context context, long artistID) {
        ContentResolver contentResolver = context.getContentResolver();
        final String artistSongSortOrder = PreferencesUtility.getInstance(context).getArtistSongSortOrder();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String string = "is_music=1 AND title != '' AND artist_id=" + artistID;
        return contentResolver.query(uri, new String[]{"_id", "title", "artist", "album", "duration", "track", "album_id"}, string, null, artistSongSortOrder);
    }
}
