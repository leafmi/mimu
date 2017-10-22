package musicdemo.jlang.com.mimu.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Jlang on 2017/10/22.
 */
@Entity(nameInDb = "oline_music_info")
public class OlineMusicInfo {
    @Id(autoincrement = true)
    private Long _id = null;
    @Property(nameInDb = "music_Id")
    private long musicId;
    @Property(nameInDb = "data")
    private String data;
    @Property(nameInDb = "title")
    private long title;
    @Property(nameInDb = "album_name")
    private String albumName;
    @Property(nameInDb = "artist_name")
    private int artistName;

    public OlineMusicInfo(long musicId, String data, long title, String albumName, int artistName) {
        this.musicId = musicId;
        this.data = data;
        this.title = title;
        this.albumName = albumName;
        this.artistName = artistName;
    }

    @Generated(hash = 494193038)
    public OlineMusicInfo(Long _id, long musicId, String data, long title, String albumName,
            int artistName) {
        this._id = _id;
        this.musicId = musicId;
        this.data = data;
        this.title = title;
        this.albumName = albumName;
        this.artistName = artistName;
    }

    @Generated(hash = 204555332)
    public OlineMusicInfo() {
    }

    public long getMusicId() {
        return musicId;
    }

    public void setMusicId(long musicId) {
        this.musicId = musicId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTitle() {
        return title;
    }

    public void setTitle(long title) {
        this.title = title;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getArtistName() {
        return artistName;
    }

    public void setArtistName(int artistName) {
        this.artistName = artistName;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
