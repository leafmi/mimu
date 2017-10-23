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
    private String title;
    @Property(nameInDb = "duration")
    private long duration;
    @Property(nameInDb = "album_name")
    private String albumName;
    @Property(nameInDb = "artist_name")
    private String artistName;
    @Property(nameInDb = "album_pic_url")
    private String albumPicUrl;


    public OlineMusicInfo(long musicId, String data, String title, long duration, String albumName, String artistName, String albumPicUrl) {
        this.musicId = musicId;
        this.data = data;
        this.title = title;
        this.duration = duration;
        this.albumName = albumName;
        this.artistName = artistName;
        this.albumPicUrl = albumPicUrl;
    }

    @Generated(hash = 872015811)
    public OlineMusicInfo(Long _id, long musicId, String data, String title, long duration, String albumName, String artistName,
            String albumPicUrl) {
        this._id = _id;
        this.musicId = musicId;
        this.data = data;
        this.title = title;
        this.duration = duration;
        this.albumName = albumName;
        this.artistName = artistName;
        this.albumPicUrl = albumPicUrl;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumPicUrl() {
        return albumPicUrl;
    }

    public void setAlbumPicUrl(String albumPicUrl) {
        this.albumPicUrl = albumPicUrl;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
