package musicdemo.jlang.com.mimu.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import musicdemo.jlang.com.mimu.greendao.gen.DaoSession;

/**
 * Created by JLang on 2017/10/19.
 */

@Entity(
        // 指定数据库中表的名称。默认情况下，该名称基于实体类名。
        nameInDb = "music_playing_info"
)
public class MusicPlayingInfo {
    @Id(autoincrement = true)
    private Long _id = null;

    @Property(nameInDb = "music_Id")
    private long musicId;

    @Property(nameInDb = "data")
    private String data;

    @Property(nameInDb = "order_first")
    private int orderFirst;

    @Property(nameInDb = "order_second")
    private int orderSecond;

    public MusicPlayingInfo(long musicId, String data, int orderFirst, int orderSecond) {
        this.musicId = musicId;
        this.data = data;
        this.orderFirst = orderFirst;
        this.orderSecond = orderSecond;
    }

    @Generated(hash = 910406780)
    public MusicPlayingInfo(Long _id, long musicId, String data, int orderFirst,
            int orderSecond) {
        this._id = _id;
        this.musicId = musicId;
        this.data = data;
        this.orderFirst = orderFirst;
        this.orderSecond = orderSecond;
    }

    @Generated(hash = 82384843)
    public MusicPlayingInfo() {
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

    public int getOrderFirst() {
        return orderFirst;
    }

    public void setOrderFirst(int orderFirst) {
        this.orderFirst = orderFirst;
    }

    public int getOrderSecond() {
        return orderSecond;
    }

    public void setOrderSecond(int orderSecond) {
        this.orderSecond = orderSecond;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
