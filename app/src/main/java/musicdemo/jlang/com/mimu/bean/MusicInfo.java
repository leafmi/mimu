package musicdemo.jlang.com.mimu.bean;

import java.io.Serializable;

/**
 * Created by hefuyi on 2016/11/3.
 */

public class MusicInfo implements Serializable {
    /**
     * 类型
     */
    public static final int LOCAL = 0;
    public static final int DOWNLOAD = 1;
    public static final int NET = 2;


    /**
     * 类型
     */
    private int type = LOCAL;
    public long albumId;
    public String albumName;
    public long artistId;
    public String artistName;
    public int duration;
    public long id;
    public String title;
    public int trackNumber;
    public float playCountScore;
    public String path;
    private long size;

    public MusicInfo() {
        this.id = -1;
        this.albumId = -1;
        this.artistId = -1;
        this.title = "";
        this.artistName = "";
        this.albumName = "";
        this.duration = -1;
        this.trackNumber = -1;
        this.path = "";
    }

    public MusicInfo(long _id, long _albumId, long _artistId, String _title, String _artistName, String _albumName, int _duration, int _trackNumber) {
        this.id = _id;
        this.albumId = _albumId;
        this.artistId = _artistId;
        this.title = _title;
        this.artistName = _artistName;
        this.albumName = _albumName;
        this.duration = _duration;
        this.trackNumber = _trackNumber;
        this.path = "";
    }

    public MusicInfo(long _id, long _albumId, long _artistId, String _title, String _artistName, String _albumName, int _duration, int _trackNumber, String _path, long _size) {
        this.id = _id;
        this.albumId = _albumId;
        this.artistId = _artistId;
        this.title = _title;
        this.artistName = _artistName;
        this.albumName = _albumName;
        this.duration = _duration;
        this.trackNumber = _trackNumber;
        this.path = _path;
        this.size = _size;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getAlbumId() {
        return albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public long getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public int getDuration() {
        return duration;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public float getPlayCountScore() {
        return playCountScore;
    }

    public void setPlayCountScore(float playCountScore) {
        this.playCountScore = playCountScore;
    }

    public String getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }
}
