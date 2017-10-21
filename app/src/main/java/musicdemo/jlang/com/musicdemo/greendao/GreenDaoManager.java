package musicdemo.jlang.com.musicdemo.greendao;

import musicdemo.jlang.com.musicdemo.ApplicationEx;
import musicdemo.jlang.com.musicdemo.greendao.gen.DaoMaster;
import musicdemo.jlang.com.musicdemo.greendao.gen.DaoSession;

/**
 * Created by JLang on 2017/10/19.
 */

public class GreenDaoManager {


    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static volatile GreenDaoManager mInstance = null;

    private GreenDaoManager() {
        if (mInstance == null) {
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(ApplicationEx.getInstance(), "user.db");
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
        }
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {
                if (mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }

}
