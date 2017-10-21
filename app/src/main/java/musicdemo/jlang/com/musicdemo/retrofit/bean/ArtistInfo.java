package musicdemo.jlang.com.musicdemo.retrofit.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by JLang on 2017/10/16.
 */

public class ArtistInfo {

    private static final String ARTIST = "artist";

    @Expose
    @SerializedName(ARTIST)
    public LastfmArtist mArtist;
}
