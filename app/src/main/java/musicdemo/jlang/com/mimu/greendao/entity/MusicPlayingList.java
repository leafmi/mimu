package musicdemo.jlang.com.mimu.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import musicdemo.jlang.com.mimu.greendao.gen.DaoSession;
import musicdemo.jlang.com.mimu.greendao.gen.MusicPlayingListDao;

/**
 * Created by JLang on 2017/10/19.
 */

@Entity(
        // 指定数据库中表的名称。默认情况下，该名称基于实体类名。
        nameInDb = "music_playing_list"
)
public class MusicPlayingList {
    @Id(autoincrement = true)
    private Long _id = null;

    @Property(nameInDb = "music_Id")
    private long musicId;

    @Property(nameInDb = "music_data")
    private String musicData;

    @Property(nameInDb = "order_first")
    private int orderFirst;

    @Property(nameInDb = "order_second")
    private int orderSecond;

    public MusicPlayingList(long musicId, String musicData, int orderFirst, int orderSecond) {
        this.musicId = musicId;
        this.musicData = musicData;
        this.orderFirst = orderFirst;
        this.orderSecond = orderSecond;
    }

    @Generated(hash = 1065905114)
    public MusicPlayingList(Long _id, long musicId, String musicData, int orderFirst,
            int orderSecond) {
        this._id = _id;
        this.musicId = musicId;
        this.musicData = musicData;
        this.orderFirst = orderFirst;
        this.orderSecond = orderSecond;
    }

    @Generated(hash = 1532292764)
    public MusicPlayingList() {
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
