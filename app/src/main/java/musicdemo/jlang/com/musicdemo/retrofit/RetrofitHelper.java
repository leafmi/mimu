package musicdemo.jlang.com.musicdemo.retrofit;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import musicdemo.jlang.com.musicdemo.ApplicationEx;
import musicdemo.jlang.com.musicdemo.retrofit.api.LastFmApiService;
import musicdemo.jlang.com.musicdemo.util.Constants;
import musicdemo.jlang.com.musicdemo.util.FileUtil;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JLang on 2016/11/28.
 */

public class RetrofitHelper {

    private static OkHttpClient mOkHttpClient;

    static {
        initOkHttpClient();
    }

    public static LastFmApiService getLastFmApiService() {
        String endpointUrl = Constants.BASE_API_URL_LASTFM;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endpointUrl)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(LastFmApiService.class);
    }

    /***
     * 初始化OKHttpClient
     */
    private static void initOkHttpClient() {
        if (mOkHttpClient == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mOkHttpClient = new OkHttpClient.Builder()
                    .cache(new Cache(FileUtil.getHttpCacheDir(ApplicationEx.getInstance()), Constants.HTTP_CACHE_SIZE))
                    .connectTimeout(Constants.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(Constants.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .build();
        }
    }
}
