//package musicdemo.jlang.com.mimu.view;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.RectF;
//import android.graphics.drawable.GradientDrawable;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//import android.support.v7.widget.AppCompatSeekBar;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.view.Display;
//import android.view.Gravity;
//import android.view.WindowManager;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import musicdemo.jlang.com.mimu.util.ColorUtil;
//
//
///**
// * @Description: 自定义进度条
// * @Param:
// * @Return:
// * @Author: zhangliangming
// * @Date: 2017/7/16 23:30
// * @Throws:
// */
//public class LrcSeekBar extends AppCompatSeekBar {
//
//    private Context mContext;
//    private Paint backgroundPaint;
//    private int backgroundProgressColor = ColorUtil.parserColor("#e5e5e5", 255);
//
//    private Paint progressPaint;
//    private int progressColor = ColorUtil.parserColor("#0288d1", 255);
//
//    private Paint secondProgressPaint;
//    private int secondProgressColor = ColorUtil.parserColor("#b8b8b8", 255);
//
//    private Paint thumbPaint;
//    private int thumbColor = ColorUtil.parserColor("#0288d1", 255);
//
//
//    //是否正在拖动
//    private boolean isDrag = false;
//    /**
//     * 提示文本
//     */
//    private String mTipText;
//    /**
//     * seekbar监听事件
//     */
//    private OnChangeListener onChangeListener;
//
//    /**
//     * 时间窗口
//     */
//    private PopupWindow mTimePopupWindow;
//    private LinearLayout mTimePopupWindowView;
//    private int mTimePopupWindowViewColor = ColorUtil.parserColor("#0288d1", 180);
//
//    /**
//     * 时间和歌词窗口
//     */
//    private PopupWindow mTimeAndLrcPopupWindow;
//    private LinearLayout mTimeAndLrcPopupWindowView;
//    private int mTimeAndLrcPopupWindowViewColor = ColorUtil.parserColor("#0288d1", 180);
//    /**
//     * 时间提示
//     */
//    private TextView mTimeTextView;
//
//    /**
//     * 歌词提示
//     */
//    private TextView mLrcTextView;
//
//
//    public LrcSeekBar(Context context) {
//        super(context);
//        init(context);
//
//    }
//
//    public LrcSeekBar(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context);
//    }
//
//    public LrcSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context);
//    }
//
//    /**
//     * 初始化
//     */
//    private void init(Context context) {
//        this.mContext = context;
//        initPaint();
//    }
//
//    private void initPaint() {
//
//        //
//        backgroundPaint = new Paint();
//        backgroundPaint.setDither(true);
//        backgroundPaint.setAntiAlias(true);
//
//        //
//        progressPaint = new Paint();
//        progressPaint.setDither(true);
//        progressPaint.setAntiAlias(true);
//
//        //
//        secondProgressPaint = new Paint();
//        secondProgressPaint.setDither(true);
//        secondProgressPaint.setAntiAlias(true);
//
//        //
//        thumbPaint = new Paint();
//        thumbPaint.setDither(true);
//        thumbPaint.setAntiAlias(true);
//
//        //
//        setBackgroundProgressColorColor(backgroundProgressColor);
//        setSecondProgressColor(secondProgressColor);
//        setProgressColor(progressColor);
//        setThumbColor(thumbColor);
//
//        //
//        setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                if (isDrag) {
//                    //
//                    String timeText = null;
//                    String lrcText = null;
//                    if (onChangeListener != null) {
//                        timeText = onChangeListener.getTimeText();
//                        lrcText = onChangeListener.getLrcText();
//                    }
//                    if (timeText != null && !timeText.equals("") && lrcText != null && !lrcText.equals("")) {
//                        mHandler.sendEmptyMessage(SHOWTIMEANDLRCVIEW);
//                    } else if (timeText != null && !timeText.equals("")) {
//                        mHandler.sendEmptyMessage(SHOWTIMEVIEW);
//                    }
//                    mHandler.sendEmptyMessage(UPDATEVIEW);
//                }
//                if (onChangeListener != null) {
//                    onChangeListener.onProgressChanged();
//                }
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//                isDrag = true;
//                invalidate();
//
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//                new Thread() {
//
//                    @Override
//                    public void run() {
//                        postInvalidate();
//                        mHandler.sendEmptyMessage(HIDEVIEW);
//                        //
//                        if (onChangeListener != null) {
//                            onChangeListener.dragFinish();
//                        }
//                        try {
//                            // 延迟100ms才更新进度
//                            Thread.sleep(200);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        isDrag = false;
//                        postInvalidate();
//                    }
//
//                }.start();
//            }
//        });
//    }
//
//    @Override
//    protected synchronized void onDraw(Canvas canvas) {
//
//
//        int rSize = getHeight() / 4;
//        if (isDrag) {
//            rSize = getHeight() / 2;
//        }
//        int height = 2;
//        int leftPadding = rSize;
//
//        if (getProgress() > 0) {
//            leftPadding = 0;
//        }
//
//        RectF backgroundRect = new RectF(leftPadding, getHeight() / 2 - height, getWidth(),
//                getHeight() / 2 + height);
//        //backgroundPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawRoundRect(backgroundRect, rSize, rSize, backgroundPaint);
//
//
//        if (getMax() != 0) {
//            RectF secondProgressRect = new RectF(leftPadding, getHeight() / 2 - height,
//                    getSecondaryProgress() * getWidth() / getMax(), getHeight()
//                    / 2 + height);
//            canvas.drawRoundRect(secondProgressRect, rSize, rSize, secondProgressPaint);
//
//            RectF progressRect = new RectF(leftPadding, getHeight() / 2 - height,
//                    getProgress() * getWidth() / getMax(), getHeight() / 2
//                    + height);
//            canvas.drawRoundRect(progressRect, rSize, rSize, progressPaint);
//
//
//            int cx = getProgress() * getWidth() / getMax();
//            if ((cx + rSize) > getWidth()) {
//                cx = getWidth() - rSize;
//            } else {
//                cx = Math.max(cx, rSize);
//            }
//            int cy = getHeight() / 2;
//            canvas.drawCircle(cx, cy, rSize, thumbPaint);
//        }
//    }
//
//    @Override
//    public synchronized void setProgress(int progress) {
//        if (!isDrag)
//            super.setProgress(progress);
//    }
//
//
//    public interface OnChangeListener {
//
//        void onProgressChanged();
//
//        String getTimeText();
//
//        String getLrcText();
//
//        void dragFinish();
//    }
//
//
//    public void setOnChangeListener(OnChangeListener onChangeListener) {
//        this.onChangeListener = onChangeListener;
//    }
//
//    /**
//     * 刷新View
//     */
//    private void invalidateView() {
//        if (Looper.getMainLooper() == Looper.myLooper()) {
//            //  当前线程是主UI线程，直接刷新。
//            invalidate();
//        } else {
//            //  当前线程是非UI线程，post刷新。
//            postInvalidate();
//        }
//    }
//
//    /////////////////////////////////////////////////////////////
//
//
//    public void setBackgroundProgressColorColor(int color) {
//        backgroundProgressColor = color;
//        backgroundPaint.setColor(backgroundProgressColor);
//        invalidateView();
//    }
//
//    public void setProgressColor(int color) {
//        progressColor = color;
//        progressPaint.setColor(progressColor);
//        invalidateView();
//    }
//
//    public void setSecondProgressColor(int color) {
//        secondProgressColor = color;
//        secondProgressPaint.setColor(secondProgressColor);
//        invalidateView();
//    }
//
//    public void setThumbColor(int color) {
//        thumbColor = color;
//        thumbPaint.setColor(thumbColor);
//        invalidateView();
//    }
//
//    public void setTimePopupWindowViewColor(int fillColor) {
//
//        mTimePopupWindowViewColor = fillColor;
//
//    }
//
//    public void setTimeAndLrcPopupWindowViewColor(int fillColor) {
//
//        mTimeAndLrcPopupWindowViewColor = fillColor;
//
//
//    }
//
//
//}
