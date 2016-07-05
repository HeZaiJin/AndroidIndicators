package com.haozhang.widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * @author HaoZhang
 * @date 2016/7/4.
 */
public class LnkPageIndicator extends View {
    private static final String TAG = "LnkPageIndicator";

    private int mAnimDuration = 400;
    private int mDefaultColor = Color.parseColor("#C9C9C9");
    private int mSelectColor = Color.parseColor("#626262");
    private int mIndicatorWidth;
    private int mIndicatorSpacing;
    private int mMax = 3;
    private int mSelectIndex = 0;
    private int mCacheIndex = 0;
    private boolean mInitIndicators = false;
    Context mContext;
    Paint mPaint;
    Paint mDefaultPaint;
    ValueAnimator mAnimator;

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
        mIndicatorSpacing = getResources().getDimensionPixelSize(R.dimen.default_indicator_spacing);
        mAnimator = ValueAnimator.ofFloat(0f, 1f);
        mAnimator.setDuration(mAnimDuration);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.setRepeatCount(1);
        mAnimator.setRepeatMode(ValueAnimator.REVERSE);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mSelectColor);

        mDefaultPaint = new Paint();
        mDefaultPaint.setAntiAlias(true);
        mDefaultPaint.setColor(mDefaultColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int spaceWidth = mIndicatorSpacing * (mMax - 1) + mIndicatorWidth * mMax;
        int r = mIndicatorWidth / 2;
        float left = (width - spaceWidth) / 2 + mCacheIndex * (mIndicatorWidth + mIndicatorSpacing);
        float top = (height - mIndicatorWidth) / 2;
        float total = (mSelectIndex - mCacheIndex) * (mIndicatorWidth + mIndicatorSpacing);
        mPaint.setColor(mSelectColor);
        float sx = (width - spaceWidth) / 2 + r;
        float sy = (height - mIndicatorWidth) / 2 + r;

        if (mAnimator.isRunning()) {
            float av = (float) mAnimator.getAnimatedValue();
            RectF rectf = null;
            if (mRepeat) {
                Log.d(TAG, "av:" + av);
                rectf = new RectF(left + total * (1.0f - av), top, left + mIndicatorWidth + total, top + mIndicatorWidth);
                // cache index anim
                RectF cacheRf = new RectF(left + mIndicatorWidth *av / 2,top,left + mIndicatorWidth *av /2+ mIndicatorWidth,top + mIndicatorWidth);
                canvas.save();
                canvas.clipRect(cacheRf);
                canvas.drawRoundRect(cacheRf, r, r, mDefaultPaint);
                canvas.restore();
            } else {
                rectf = new RectF(left, top, left + mIndicatorWidth + total * av, top + mIndicatorWidth);
            }
            canvas.save();
            drawIndicators(canvas, sx, sy, r,true);
            canvas.restore();

            canvas.save();
            canvas.clipRect(rectf);
            canvas.drawRoundRect(rectf, r, r, mPaint);
            canvas.restore();
            // draw other cirle
        } else {
            drawIndicators(canvas, sx, sy, r,false);
        }
    }

    private void drawIndicators(Canvas canvas, float sX, float sY, float r,boolean anim) {
        for (int i = 0; i < mMax; i++) {
            if (anim){
                if ( i != mCacheIndex){
                    canvas.drawCircle(sX, sY, r, mDefaultPaint);
                }
            }else {
                if (mSelectIndex == i) {
                    canvas.drawCircle(sX, sY, r, mPaint);
                } else {
                    canvas.drawCircle(sX, sY, r, mDefaultPaint);
                }
            }
            sX += mIndicatorWidth + mIndicatorSpacing;
        }
        mInitIndicators = true;
    }

    public int getMax() {
        return mMax;
    }

    public void setSelectIndex(int index) {

        if (mAnimator.isRunning()) return;

        this.mSelectIndex = index;
        mAnimator.setTarget(this);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(TAG, "start");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "end");
                mCacheIndex = mSelectIndex;
                mRepeat = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d(TAG, "cancel");
                mRepeat = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d(TAG, "repeat");
                mRepeat = true;
            }
        });
        mAnimator.start();
//        invalidate();
    }

    boolean mRepeat = false;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
