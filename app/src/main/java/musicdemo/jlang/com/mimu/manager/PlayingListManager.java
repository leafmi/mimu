//package musicdemo.jlang.com.mimu.manager;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import musicdemo.jlang.com.mimu.ApplicationEx;
//import musicdemo.jlang.com.mimu.bean.MusicMessage;
//import musicdemo.jlang.com.mimu.bean.MusicInfo;
//import musicdemo.jlang.com.mimu.greendao.SQLiteOpenHelperManager;
//import musicdemo.jlang.com.mimu.greendao.entity.MusicPlayingInfo;
//import musicdemo.jlang.com.mimu.greendao.gen.DaoSession;
//import musicdemo.jlang.com.mimu.greendao.gen.MusicPlayingListDao;
//import musicdemo.jlang.com.mimu.util.PreferencesUtility;
//
///**
// * Created by JLang on 2017/10/20.
// */
//
//public class PlayingListManager {
//    private static PlayingListManager instance;
//    private ApplicationEx mApplicationEx;
//
//    /**
//     * 当前播放歌单集合数据
//     */
//    private List<MusicPlayingInfo> currentPlayingList = new ArrayList<>();
//    private Map<String, MusicInfo> musicSource = new HashMap<>();
//    private int currentPlayingIndex = -1;
//    private MusicPlayingInfo currentPlayingInfo;
//    private MusicInfo currentMusicInfo;
//
//    /**
//     * 当前歌曲
//     */
//    private MusicMessage curMusicMessage;
//
//
//    public static PlayingListManager getInstance() {
//        if (instance == null) {
//            synchronized (PlayingListManager.class) {
//                instance = new PlayingListManager(ApplicationEx.getInstance());
//            }
//        }
//        return instance;
//    }
//
//    private PlayingListManager(ApplicationEx mApplicationEx) {
////        //初始化数据
////        DaoSession daoSession = SQLiteOpenHelperManager.createDaoSession(mApplicationEx, true);
////        List<MusicPlayingInfo> list = daoSession.getMusicPlayingListDao().queryBuilder()
////                .orderAsc(MusicPlayingListDao.Properties.OrderFirst)
////                .orderAsc(MusicPlayingListDao.Properties.OrderSecond).build().list();
////        currentPlayingList.addAll(list);
////
//
//        this.mApplicationEx = mApplicationEx;
//    }
//
//    //-----------------------------------------------------------------------------------------
//    public List<MusicPlayingInfo> getCurrentPlayingList() {
//        return currentPlayingList;
//    }
//
//    public void setCurrentPlayingList(List<MusicPlayingInfo> currentPlayingList) {
//        this.currentPlayingList = currentPlayingList;
//    }
//
//    /**
//     * 获取当前播放歌单的索引
//     *
//     * @return 当前播放索引
//     */
//    public int getCurrentPlayingIndex() {
//        return currentPlayingIndex;
//    }
//
//    /**
//     * 设置当前播放歌单的索引
//     *
//     * @param currentPlayingIndex 当前播放索引
//     */
//    public void setCurrentPlayingIndex(int currentPlayingIndex) {
//        this.currentPlayingIndex = currentPlayingIndex;
//    }
//
//    public MusicPlayingInfo getCurrentPlayingInfo() {
//        if (currentPlayingInfo == null) {
//            if (currentPlayingIndex != -1 && currentPlayingIndex < currentPlayingList.size())
//                currentPlayingInfo = currentPlayingList.get(currentPlayingIndex);
//        }
//        return currentPlayingInfo;
//    }
//
//    public MusicMessage getCurMusicMessage() {
//        return curMusicMessage;
//    }
//
//    public void setCurMusicMessage(MusicMessage curMusicMessage) {
//        this.curMusicMessage = curMusicMessage;
//    }
//
//    /**
//     * 获取当前播放音乐的详细信息
//     *
//     * @return 当前播放音乐
//     */
//    public MusicInfo getCurrentMusicInfo() {
////        MusicInfo musicInfo = musicSource.get(curMusicMessage.getMusicData());
//////        if (musicInfo != null) {
//////            return currentMusicInfo;
//////        }
//        return null;
//    }
//
//
//    //-------------------------------------------------------------------------------
//
//
//    /**
//     * 添加歌单到播放列表
//     *
//     * @param musicInfos Music 集合
//     */
//    public void addLocalPlayingList(List<MusicInfo> musicInfos, int sourceId) {
////        int size = musicInfos.size();
////        for (int i = 0; i < size; i++) {
////            MusicInfo musicInfo = musicInfos.get(i);
////            musicSource.put(musicInfo.path, musicInfo);
////            currentPlayingList.add(new MusicPlayingInfo(musicInfo.id, musicInfo.path, i, 0));
////        }
////        DaoSession daoSession = SQLiteOpenHelperManager.createDaoSession(mApplicationEx, true);
////        if (daoSession != null) {
////            MusicPlayingListDao musicPlayingListDao = daoSession.getMusicPlayingListDao();
////            //清除上次歌单所有数据
////            musicPlayingListDao.insertOrReplaceInTx(currentPlayingList);
////
////            //保存歌单资源Id
////            PreferencesUtility.getInstance(mApplicationEx).setCurrentPlayingListSourceId(sourceId);
////        }
//    }
//
//    /**
//     * 添加音乐到当前播放的下一曲
//     *
//     * @param musicInfo
//     */
//    public void addLocalPlayingList(MusicInfo musicInfo) {
////        if (musicInfo == null || currentPlayingInfo == null) {
////            return;
////        }
////        musicSource.put(musicInfo.path, musicInfo);
////        MusicPlayingInfo playingList = new MusicPlayingInfo(musicInfo.id, musicInfo.path
////                , currentPlayingInfo.getOrderFirst()
////                , currentPlayingInfo.getOrderSecond() + 1);
////
////        currentPlayingList.add(currentPlayingIndex, playingList);
////
////        DaoSession daoSession = SQLiteOpenHelperManager.createDaoSession(mApplicationEx, true);
////        if (daoSession != null) {
////            MusicPlayingListDao musicPlayingListDao = daoSession.getMusicPlayingListDao();
////            musicPlayingListDao.insertOrReplace(playingList);
////        }
//    }
//
//
//    /**
//     * 计算当前播放Id
//     *
//     * @param playData 当前播放音乐数据地址
//     */
//    public void calcCurrentPLayingIndex(String playData) {
//        int size = currentPlayingList.size();
//        for (int i = 0; i < size; i++) {
//            MusicPlayingInfo playingList = currentPlayingList.get(i);
//            if (playingList.getMusicData().equals(playData)) {
//                currentPlayingIndex = i;
//                return;
//            }
//        }
//    }
//}
