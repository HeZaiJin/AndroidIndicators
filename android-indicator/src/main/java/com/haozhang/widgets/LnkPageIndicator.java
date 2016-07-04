package com.haozhang.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author HaoZhang
 * @date 2016/7/4.
 */
public class LnkPageIndicator extends View {
    private static final String TAG = "LnkPageIndicator";

    private int mDefaultColor = Color.parseColor("#C9C9C9");
    private int mSelectColor = Color.parseColor("#626262");
    private int mIndicatorWidth ;
    Context mContext;
    Paint mPaint;

    public LnkPageIndicator(Context context) {
        this(context, null);
    }

    public LnkPageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LnkPageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LnkPageIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mContext = getContext();
        mIndicatorWidth = getResources().getDimensionPixelSize(R.dimen.default_indicator_width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


}
