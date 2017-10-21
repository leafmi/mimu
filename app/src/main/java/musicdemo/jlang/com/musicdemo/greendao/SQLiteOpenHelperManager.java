package musicdemo.jlang.com.musicdemo.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import musicdemo.jlang.com.musicdemo.greendao.gen.DaoMaster;
import musicdemo.jlang.com.musicdemo.greendao.gen.DaoSession;

/**
 * 管理创建SQLiteOpenHelper对象;
 * Created by John on 2016/1/11.
 */
public final class SQLiteOpenHelperManager {

    private static final String DATABASE_NAME_MUSIC = "music.db";

    private static OpenHelperWrap sInstance;

    private static class OpenHelperWrap extends DaoMaster.DevOpenHelper {

        public OpenHelperWrap(Context context, String name) {
            super(context, name, null);
        }

        @Override
        public synchronized void close() {
            //覆盖掉父类的close，防止误操作；使用close2来真正关闭数据库。
            // 只有在service生命周期结束的时候才应该调用close方法；
            Log.w("SQLiteOpenHelperManager", "使用close2来真正关闭数据库");
        }

        public synchronized void close2() {
            super.close();
        }
    }

    public static SQLiteOpenHelper getSQLiteOpenHelper(Context context) {
        if (sInstance == null) {
            synchronized (SQLiteOpenHelperManager.class) {
                if (sInstance == null) {
                    sInstance = new OpenHelperWrap(context, DATABASE_NAME_MUSIC);
                }
            }
        }
        return sInstance;
    }

    public static void close() {
        synchronized (SQLiteOpenHelperManager.class) {
            if (sInstance == null) {
                return;
            }

            try {
                sInstance.close2();
            } catch (Exception e) {
            }

            sInstance = null;
        }
    }

    public static DaoSession createDaoSession(Context context, boolean writable) {
        synchronized (SQLiteOpenHelperManager.class) {
            try {
                SQLiteOpenHelper helper = getSQLiteOpenHelper(context);
                SQLiteDatabase db = writable ? helper.getWritableDatabase() : helper.getReadableDatabase();
                DaoMaster daoMaster = new DaoMaster(db);
                return daoMaster.newSession();
            } catch (Exception e) {
                return null;
            }
        }
    }

}
