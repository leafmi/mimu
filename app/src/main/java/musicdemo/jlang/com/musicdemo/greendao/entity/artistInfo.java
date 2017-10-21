package musicdemo.jlang.com.musicdemo.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by JLang on 2017/10/21.
 */
@Entity(nameInDb = "artist_info")
public class artistInfo {
    @Id(autoincrement = true)
    private Long _id = null;
    @Property(nameInDb = "artist_id")
    private int artistId;
    @Property(nameInDb = "artist_name")
    private int artistName;
    @Property(nameInDb = "artist_pic_url")
    private int artistPicUrl;
    @Property(nameInDb = "artist_big_pic_url")
    private int artistBigPicUrl;

    public artistInfo(int artistId, int artistName, int artistPicUrl, int artistBigPicUrl) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistPicUrl = artistPicUrl;
        this.artistBigPicUrl = artistBigPicUrl;
    }

    @Generated(hash = 455862732)
    public artistInfo(Long _id, int artistId, int artistName, int artistPicUrl,
            int artistBigPicUrl) {
        this._id = _id;
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistPicUrl = artistPicUrl;
        this.artistBigPicUrl = artistBigPicUrl;
    }

    @Generated(hash = 111249044)
    public artistInfo() {
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getArtistName() {
        return artistName;
    }

    public void setArtistName(int artistName) {
        this.artistName = artistName;
    }

    public int getArtistPicUrl() {
        return artistPicUrl;
    }

    public void setArtistPicUrl(int artistPicUrl) {
        this.artistPicUrl = artistPicUrl;
    }

    public int getArtistBigPicUrl() {
        return artistBigPicUrl;
    }

    public void setArtistBigPicUrl(int artistBigPicUrl) {
        this.artistBigPicUrl = artistBigPicUrl;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
