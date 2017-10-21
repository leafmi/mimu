package musicdemo.jlang.com.musicdemo.bean;

/**
 * Created by JLang on 2017/10/16.
 */

public class FolderInfo {

    public final String folderName;
    public final String folderPath;
    public final int songCount;

    public FolderInfo() {
        this.folderName = "";
        this.folderPath = "";
        this.songCount = -1;
    }

    public FolderInfo(String _folderName, String _folderPath, int _songCount) {
        this.folderName = _folderName;
        this.folderPath = _folderPath;
        this.songCount = _songCount;
    }
}
