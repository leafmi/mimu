package musicdemo.jlang.com.mimu.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by JLang on 2017/10/24.
 */
@Entity(nameInDb = "favourite_music_info")
public class FavouriteMusicInfo {
    @Id(autoincrement = true)
    private Long _id = null;

    @Property(nameInDb = "music_Id")
    private long musicId;

    @Property(nameInDb = "data")
    private String data;

    @Property(nameInDb = "add_time")
    private long addTime;

    public FavouriteMusicInfo(long musicId, String data, long addTime) {
        this.musicId = musicId;
        this.data = data;
        this.addTime = addTime;
    }

    public FavouriteMusicInfo(long musicId, String data) {
        this.musicId = musicId;
        this.data = data;
    }

    @Generated(hash = 299957083)
    public FavouriteMusicInfo(Long _id, long musicId, String data, long addTime) {
        this._id = _id;
        this.musicId = musicId;
        this.data = data;
        this.addTime = addTime;
    }

    @Generated(hash = 761091131)
    public FavouriteMusicInfo() {
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

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}


