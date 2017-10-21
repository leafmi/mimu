package musicdemo.jlang.com.mimu.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by JLang on 2016/11/28.
 */

public class ToastUtil {

    private static Toast makeText;

    /**
     * 极简吐司提示
     *
     * @param context
     * @param text
     */
    public static void showToast(Context context, int text) {
        if (makeText == null) {
            makeText = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        makeText.setText(text);
        makeText.show();
    }

    /**
     * 极简吐司提示
     *
     * @param context
     * @param text
     */
    public static void showToast(Context context, String text) {
        if (makeText == null) {
            makeText = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        makeText.setText(text);
        makeText.show();
    }


    /**
     *
     * @param context
     * @param text
     * @param duration
     */
    public static void showToast(Context context, String text, int duration) {
        if (makeText == null) {
            makeText = Toast.makeText(context, "", duration);
        }
        makeText.setText(text);
        makeText.show();
    }

    /**
     *
     * @param context
     * @param text
     * @param duration
     */
    public static void showToast(Context context, int text, int duration) {
        if (makeText == null) {
            makeText = Toast.makeText(context, "", duration);
        }
        makeText.setText(text);
        makeText.show();
    }
}
