package musicdemo.jlang.com.mimu.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by JLang on 2017/10/21.
 */

@Entity(nameInDb = "local_music_info")
public class LocalMusicInfo {
    @Id(autoincrement = true)
    private Long _id = null;
    @Property(nameInDb = "music_Id")
    private long musicId;
    @Property(nameInDb = "data")
    private String data;
    @Property(nameInDb = "music_size")
    private long musicSize;
    @Property(nameInDb = "modified_time")
    private long modifiedTime;
    @Property(nameInDb = "add_time")
    private long addTime;
    @Property(nameInDb = "duration")
    private long duration;
    @Property(nameInDb = "title")
    private String title;
    @Property(nameInDb = "artist_name")
    private String artistName;
    @Property(nameInDb = "album_name")
    private String albumName;
    @Property(nameInDb = "album_pic_url")
    private String albumPicUrl;


    public LocalMusicInfo(long musicId, String data, long musicSize, long modifiedTime, long addTime, long duration, String title, String artistName, String albumName, String albumPicUrl) {
        this.musicId = musicId;
        this.data = data;
        this.musicSize = musicSize;
        this.modifiedTime = modifiedTime;
        this.addTime = addTime;
        this.duration = duration;
        this.title = title;
        this.artistName = artistName;
        this.albumName = albumName;
        this.albumPicUrl = albumPicUrl;
    }

    @Generated(hash = 1538689197)
    public LocalMusicInfo(Long _id, long musicId, String data, long musicSize, long modifiedTime, long addTime, long duration, String title, String artistName, String albumName,
            String albumPicUrl) {
        this._id = _id;
        this.musicId = musicId;
        this.data = data;
        this.musicSize = musicSize;
        this.modifiedTime = modifiedTime;
        this.addTime = addTime;
        this.duration = duration;
        this.title = title;
        this.artistName = artistName;
        this.albumName = albumName;
        this.albumPicUrl = albumPicUrl;
    }

    @Generated(hash = 1287061425)
    public LocalMusicInfo() {
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

    public long getMusicSize() {
        return musicSize;
    }

    public void setMusicSize(long musicSize) {
        this.musicSize = musicSize;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
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
