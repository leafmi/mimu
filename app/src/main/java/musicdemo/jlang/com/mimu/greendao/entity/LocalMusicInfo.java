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
    @Property(nameInDb = "music_data")
    private String musicData;
    @Property(nameInDb = "music_size")
    private long musicSize;
    @Property(nameInDb = "modified_time")
    private long modifiedTime;
    @Property(nameInDb = "add_time")
    private long addTime;
    @Property(nameInDb = "duration")
    private long duration;
    @Property(nameInDb = "title")
    private long title;
    @Property(nameInDb = "artist_id")
    private long artistId;
    @Property(nameInDb = "album_id")
    private long albumId;

    public LocalMusicInfo(long musicId, String musicData, long musicSize, long modifiedTime, long addTime, long duration, long title, long artistId, long albumId) {
        this.musicId = musicId;
        this.musicData = musicData;
        this.musicSize = musicSize;
        this.modifiedTime = modifiedTime;
        this.addTime = addTime;
        this.duration = duration;
        this.title = title;
        this.artistId = artistId;
        this.albumId = albumId;
    }

    @Generated(hash = 754586604)
    public LocalMusicInfo(Long _id, long musicId, String musicData, long musicSize, long modifiedTime, long addTime, long duration, long title, long artistId,
            long albumId) {
        this._id = _id;
        this.musicId = musicId;
        this.musicData = musicData;
        this.musicSize = musicSize;
        this.modifiedTime = modifiedTime;
        this.addTime = addTime;
        this.duration = duration;
        this.title = title;
        this.artistId = artistId;
        this.albumId = albumId;
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

    public String getMusicData() {
        return musicData;
    }

    public void setMusicData(String musicData) {
        this.musicData = musicData;
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

    public long getTitle() {
        return title;
    }

    public void setTitle(long title) {
        this.title = title;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
