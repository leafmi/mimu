package musicdemo.jlang.com.mimu.util;

/**
 * Created by JLang on 2017/10/23.
 */

public class DateUtil {
    /**
     * 整数时间转换成字符串
     *
     * @param time
     * @return
     */
    public static String parseTimeToString(int time) {

        time /= 1000;
        int minute = time / 60;
        // int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }
}
