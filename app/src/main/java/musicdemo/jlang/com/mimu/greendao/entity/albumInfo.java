package musicdemo.jlang.com.mimu.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by JLang on 2017/10/21.
 */
@Entity(nameInDb = "album_info")
public class albumInfo {
    @Id(autoincrement = true)
    private Long _id = null;
    @Property(nameInDb = "album_id")
    private int albumId;
    @Property(nameInDb = "album_name")
    private String albumName;
    @Property(nameInDb = "album_pic_url")
    private String albumPicUrl;
    @Property(nameInDb = "album_big_pic_url")
    private String albumBigPicUrl;


    public albumInfo(int albumId, String albumName, String albumPicUrl, String albumBigPicUrl) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.albumPicUrl = albumPicUrl;
        this.albumBigPicUrl = albumBigPicUrl;
    }

    @Generated(hash = 33956661)
    public albumInfo(Long _id, int albumId, String albumName, String albumPicUrl,
            String albumBigPicUrl) {
        this._id = _id;
        this.albumId = albumId;
        this.albumName = albumName;
        this.albumPicUrl = albumPicUrl;
        this.albumBigPicUrl = albumBigPicUrl;
    }

    @Generated(hash = 458914951)
    public albumInfo() {
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
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

    public String getAlbumBigPicUrl() {
        return albumBigPicUrl;
    }

    public void setAlbumBigPicUrl(String albumBigPicUrl) {
        this.albumBigPicUrl = albumBigPicUrl;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
