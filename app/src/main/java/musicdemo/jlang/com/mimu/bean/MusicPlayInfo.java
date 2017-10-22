package musicdemo.jlang.com.mimu.bean;

/**
 * Created by Jlang on 2017/10/22.
 */

public class MusicPlayInfo {
    private int musicId;
    private String data;
    private String title;
    private String artistName;//歌手名字
    private String artistPicUrl;//歌手图片Url
    private String artistBigPicUrl;//歌手大图Url
    private String albumName;//专辑名字
    private String albumPicUrl;//专辑图片Url
    private String albumBigPicUrl;//专辑大图Url
    private long duration;//歌曲时长
    private int type;//歌曲类型


    public MusicPlayInfo(int musicId, String data, String title, String artistName, String artistPicUrl, String artistBigPicUrl
            , String albumName, String albumPicUrl, String albumBigPicUrl, long duration, int type) {
        this.musicId = musicId;
        this.data = data;
        this.title = title;
        this.artistName = artistName;
        this.artistPicUrl = artistPicUrl;
        this.artistBigPicUrl = artistBigPicUrl;
        this.albumName = albumName;
        this.albumPicUrl = albumPicUrl;
        this.albumBigPicUrl = albumBigPicUrl;
        this.duration = duration;
        this.type = type;
    }

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
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

    public String getArtistBigPicUrl() {
        return artistBigPicUrl;
    }

    public void setArtistBigPicUrl(String artistBigPicUrl) {
        this.artistBigPicUrl = artistBigPicUrl;
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
}
