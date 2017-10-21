package musicdemo.jlang.com.musicdemo.retrofit.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JLang on 2017/10/16.
 */

public class Artwork {

    private static final String URL = "#text";
    private static final String SIZE = "size";

    @SerializedName(URL)
    public String mUrl;

    @SerializedName(SIZE)
    public String mSize;
}
