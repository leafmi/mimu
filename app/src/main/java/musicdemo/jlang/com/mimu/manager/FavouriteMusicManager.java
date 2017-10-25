package musicdemo.jlang.com.mimu.manager;

import android.content.Context;

import musicdemo.jlang.com.mimu.greendao.SQLiteOpenHelperManager;
import musicdemo.jlang.com.mimu.greendao.entity.FavouriteMusicInfo;
import musicdemo.jlang.com.mimu.greendao.gen.DaoSession;
import musicdemo.jlang.com.mimu.greendao.gen.FavouriteMusicInfoDao;

/**
 * 添加删除音乐喜爱 Manager
 * Created by JLang on 2017/10/24.
 */

public class FavouriteMusicManager {
    private Context mContext;
    private DaoSession daoSession;
    private FavouriteMusicInfoDao favouriteMusicInfoDao;


    public FavouriteMusicManager() {
        DaoSession daoSession = SQLiteOpenHelperManager.createDaoSession(mContext, true);
        if (daoSession != null) {
            favouriteMusicInfoDao = daoSession.getFavouriteMusicInfoDao();
        }
    }

    public void addFavouriteMusic(long musicId, String data) {
        if (favouriteMusicInfoDao != null) {
            favouriteMusicInfoDao.insert(new FavouriteMusicInfo(musicId, data, System.currentTimeMillis()));
        }
    }

    public void deleteFavouriteMusic(long musicId, String data) {
        if (favouriteMusicInfoDao != null) {
            favouriteMusicInfoDao.queryBuilder()
                    .where(FavouriteMusicInfoDao.Properties.MusicId.eq(musicId), FavouriteMusicInfoDao.Properties.Data.eq(data))
                    .buildDelete()
                    .executeDeleteWithoutDetachingEntities();
        }
    }
}
