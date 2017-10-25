package musicdemo.jlang.com.mimu.bean;

/**
 * Created by Jlang on 2017/10/22.
 */

public class MusicPlayInfo {
    private long musicId;
    private String data;
    private String title;
    private String artistName;//歌手名字
    private String artistPicUrl;//歌手图片Url
    private String albumName;//专辑名字
    private String albumPicUrl;//专辑图片Url
    private long duration;//歌曲时长
    private int type;//歌曲类型
    private boolean isFavourite;//是否是喜欢的
    private int orderFirst;
    private int orderSecond;

    public MusicPlayInfo(long musicId, String data, String title, String artistName, String albumName,
                         String albumPicUrl, long duration, int type, int orderFirst, int orderSecond) {
        this.musicId = musicId;
        this.data = data;
        this.title = title;
        this.artistName = artistName;
        this.albumName = albumName;
        this.albumPicUrl = albumPicUrl;
        this.duration = duration;
        this.type = type;
        this.orderFirst = orderFirst;
        this.orderSecond = orderSecond;
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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistPicUrl() {
        return artistPicUrl;
    }

    public void setArtistPicUrl(String artistPicUrl) {
        this.artistPicUrl = artistPicUrl;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
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
}
