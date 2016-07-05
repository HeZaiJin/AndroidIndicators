package com.haozhang.widgets;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author HaoZhang
 * @date 2016/7/5.
 */
public class LnkPageIndicators extends LinearLayout {

    private int mAnimDuration = 250;
    private int mDefaultColor = Color.parseColor("#C9C9C9");
    private int mSelectColor = Color.parseColor("#626262");
    private int mIndicatorWidth;
    private int mIndicatorSpacing ;
    private int mMax = 3;
    private int mSelectIndex = 0;
    private int mCacheIndex = 0;
    private boolean mInitIndicators = false;
    Context mContext;

    ValueAnimator mAnimator;

    public LnkPageIndicators(Context context) {
        this(context,null);
    }

    public LnkPageIndicators(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LnkPageIndicators(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(21)
    public LnkPageIndicators(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mContext = getContext();
        mIndicatorWidth = getResources().getDimensionPixelSize(R.dimen.default_indicator_width);
        mIndicatorSpacing = getResources().getDimensionPixelSize(R.dimen.default_indicator_spacing);
        mAnimator = ValueAnimator.ofFloat(0f,0f);
        mAnimator.setDuration(mAnimDuration);

    }


}
