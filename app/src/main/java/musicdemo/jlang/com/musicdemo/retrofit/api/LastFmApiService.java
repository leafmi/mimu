package musicdemo.jlang.com.musicdemo.retrofit.api;

import io.reactivex.Observable;
import musicdemo.jlang.com.musicdemo.retrofit.bean.ArtistInfo;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by JLang on 2017/10/16.
 */

public interface LastFmApiService {
    String BASE_PARAMETERS_ARTIST = "?method=artist.getinfo&api_key=fdb3a51437d4281d4d64964d333531d4&format=json";

    @GET(BASE_PARAMETERS_ARTIST)
    Observable<ArtistInfo> getArtistInfo(@Query("artist") String artist);
}
