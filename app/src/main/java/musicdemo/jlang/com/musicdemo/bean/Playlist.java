package musicdemo.jlang.com.musicdemo.bean;

/**
 * Created by JLang on 2017/10/19.
 */

public class Playlist {
    public final long id;
    public final String name;
    public final int songCount;

    public Playlist() {
        this.id = -1;
        this.name = "";
        this.songCount = -1;
    }

    public Playlist(long _id, String _name, int _songCount) {
        this.id = _id;
        this.name = _name;
        this.songCount = _songCount;
    }
}
