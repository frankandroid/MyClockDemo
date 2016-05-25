package com.example.frank.myclockdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 创建者     Frank
 * 创建时间   2016/5/25 13:54
 * 描述	      ${一个时钟的demo}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class MyClockView extends View {

    private static final String TAG = "MyClockView";

    private static final int EACHTICK = 6;
    //秒针
    private float second;

    //分针
    private float minute;

    //时针
    private float hour;


    private Paint mPaint;

    private int mWidth;
    private int mHeight;

    //中间圆的半径
    private int mCircle;

    //普通刻度的长度

    private int mNormalTick;

    //整点刻度的长度
    private int mSpecialTick;

    //margin值

    private int mMargin;
    private Timer mTimer;


    public MyClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyClockView(Context context) {
        this(context, null);
    }

    public MyClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mNormalTick = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources()
                .getDisplayMetrics());
        mSpecialTick = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources()
                .getDisplayMetrics());

        mCircle = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,2,getResources().getDisplayMetrics());

        mTimer = new Timer();

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
        mMargin = params.leftMargin;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //注意获取布局参数一定要在这里搞
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
        mMargin = params.leftMargin;
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLACK);

        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2 - 20, mPaint);

        for (int i = 0; i < 12; i++) {

            if (i == 0 || i == 3 || i == 6 || i == 9) {

                mPaint.setTextSize(50);

                String degree = String.valueOf(i);
                if (i == 0) {
                    degree = String.valueOf(12);
                }

                //Log.d(TAG, "margin" + mMargin + ">>>>" + "mnormal=" + mNormalTick);
                //多减去10是外层圆的strokewidth的值
                canvas.drawLine(mWidth / 2, mHeight / 2 + mMargin - 10 - mWidth / 2, mWidth / 2, mHeight / 2 - mWidth
                        / 2 + mSpecialTick, mPaint);
                canvas.drawText(degree, mWidth / 2 - mPaint.measureText(degree) / 2, mHeight / 2 - mWidth / 2 +
                        mSpecialTick * 2, mPaint);
            } else {
                mPaint.setStrokeWidth(6);
                mPaint.setTextSize(25);
                String degree = String.valueOf(i);
                canvas.drawLine(mWidth / 2, mHeight / 2 + mMargin - 10 - mWidth / 2, mWidth / 2, mHeight / 2 - mWidth
                        / 2 + mNormalTick, mPaint);
                canvas.drawText(degree, mWidth / 2 - mPaint.measureText(degree) / 2, mHeight / 2 - mWidth / 2 +
                        mNormalTick * 2, mPaint);
            }
            canvas.rotate(30, mWidth / 2, mHeight / 2);
        }

        canvas.drawCircle(mWidth / 2, mHeight / 2, mCircle, mPaint);


        //save rotate restore 三个配合使用，其实也就是把之前的当做一个图层保存起来，然后在新的图层上面作画，然后再合并在一起。
        canvas.save();
        canvas.rotate(second, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - mWidth / 2 + 150, mPaint);
        canvas.restore();


        canvas.save();
        mPaint.setStrokeWidth(15);
        canvas.rotate(minute, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - mWidth / 2 + 250, mPaint);
        canvas.restore();

        canvas.save();
        mPaint.setStrokeWidth(35);
        canvas.rotate(hour, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - mWidth / 2 + 300, mPaint);
        canvas.restore();


        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                second = second + 6;
                minute = (float)minute +(float)((float)6/(float)60);
                hour = (float)hour+(float)((float)30/(float)3600);
                if (second == 360) {
                    second = 0;
                }
                postInvalidate();
            }
        }, 1);

        
    }



}
