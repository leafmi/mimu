package musicdemo.jlang.com.mimu.dataloader;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import musicdemo.jlang.com.mimu.bean.FolderInfo;

/**
 * Created by JLang on 2017/10/16.
 */

public class FolderLoader {
    /**
     * 检索包含音频文件的文件夹, 并统计该文件夹下的歌曲数目
     *
     * @return
     */
    public static Observable<List<FolderInfo>> getFoldersWithSong(final Context context) {
        final List<FolderInfo> folderInfos = new ArrayList<FolderInfo>();
        final String num_of_songs = "num_of_songs";
        final String[] projection = new String[]{MediaStore.Files.FileColumns.DATA,
                "count(" + MediaStore.Files.FileColumns.PARENT + ") as " + num_of_songs};

        final String selection = " is_music=1 AND title != '' " + " ) " + " group by ( "
                + MediaStore.Files.FileColumns.PARENT;

        return Observable.create(new ObservableOnSubscribe<List<FolderInfo>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<FolderInfo>> e) throws Exception {
                Cursor cursor = context.getContentResolver().query(
                        MediaStore.Files.getContentUri("external"), projection, selection, null, null);

                if (cursor != null) {
                    int index_data = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
                    int index_num_of_songs = cursor.getColumnIndex(num_of_songs);

                    while (cursor.moveToNext()) {

                        // 获取每个目录下的歌曲数量
                        int songCount = cursor.getInt(index_num_of_songs);

                        // 获取文件的路径，如/storage/sdcard0/MIUI/music/Baby.mp3
                        String filepath = cursor.getString(index_data);

                        // 获取文件所属文件夹的路径，如/storage/sdcard0/MIUI/music
                        String folderpath = filepath.substring(0, filepath.lastIndexOf(File.separator));

                        // 获取文件所属文件夹的名称，如music
                        String foldername = folderpath.substring(folderpath.lastIndexOf(File.separator) + 1);


                        folderInfos.add(new FolderInfo(foldername, folderpath, songCount));
                    }
                }

                if (cursor != null) {
                    cursor.close();
                }
                e.onNext(folderInfos);
            }
        });
    }
}
