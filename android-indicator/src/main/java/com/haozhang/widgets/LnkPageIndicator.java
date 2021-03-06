package com.haozhang.widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * @author HaoZhang
 */
public class LnkPageIndicator extends View {
    private static final String TAG = "LnkPageIndicator";

    private int mAnimDuration = 300;
    private int mDefaultColor = Color.parseColor("#C9C9C9");
    private int mSelectColor = Color.parseColor("#626262");
    private int mIndicatorWidth;
    private int mIndicatorSpacing;
    private int mMax = 3;
    private int mSelectIndex = 0;
    private int mCacheIndex = 0;
    Context mContext;
    Paint mPaint;
    Paint mDefaultPaint;
    ValueAnimator mAnimator;
    boolean mRepeat = false;

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
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.LnkPageIndicator);
        mIndicatorWidth = array.getDimensionPixelSize(R.styleable.LnkPageIndicator_indicator_width, -1);
        mIndicatorSpacing = getResources().getDimensionPixelSize(R.dimen.default_indicator_spacing);
        mIndicatorSpacing = array.getDimensionPixelSize(R.styleable.LnkPageIndicator_indicator_spacing, mIndicatorSpacing);
        mDefaultColor = array.getColor(R.styleable.LnkPageIndicator_indicator_default_color, mDefaultColor);
        mSelectColor = array.getColor(R.styleable.LnkPageIndicator_indicator_selected_color, mSelectColor);
        mMax = array.getInteger(R.styleable.LnkPageIndicator_indicator_max, mMax);
        mAnimDuration = array.getInteger(R.styleable.LnkPageIndicator_indicator_duration,mAnimDuration);
        mSelectIndex = array.getInteger(R.styleable.LnkPageIndicator_indicator_selected_index, mSelectIndex);
        mCacheIndex = mSelectIndex;
        array.recycle();

        mAnimator = ValueAnimator.ofFloat(0f, 1f);
        mAnimator.setDuration(mAnimDuration);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
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
        if (mIndicatorWidth == -1) {
            mIndicatorWidth = height;
        }
        int spaceWidth = mIndicatorSpacing * (mMax - 1) + mIndicatorWidth * mMax;
        int r = mIndicatorWidth / 2;
        float left = (width - spaceWidth) / 2 + mCacheIndex * (mIndicatorWidth + mIndicatorSpacing);
        float top = (height - mIndicatorWidth) / 2;
        float total = (mSelectIndex - mCacheIndex) * (mIndicatorWidth + mIndicatorSpacing);
        float sx = (width - spaceWidth) / 2 + r;
        float sy = (height - mIndicatorWidth) / 2 + r;

        if (mAnimator.isRunning()) {
            float av = (float) mAnimator.getAnimatedValue();
            RectF rectf = null;
            if (mRepeat) {
                RectF cacheRf = new RectF(left + mIndicatorWidth * av / 2, top, left + mIndicatorWidth * av / 2 + mIndicatorWidth, top + mIndicatorWidth);
                if (mSelectIndex > mCacheIndex) {
                    rectf = new RectF(left + total * (1.0f - av), top, left + mIndicatorWidth + total, top + mIndicatorWidth);
                } else {
                    rectf = new RectF(left + total, top, left + mIndicatorWidth + total * (1.0f - av), top + mIndicatorWidth);
                    cacheRf = new RectF(left - mIndicatorWidth * av / 2, top, left + mIndicatorWidth - mIndicatorWidth * av / 2, top + mIndicatorWidth);
                }
                canvas.save();
                canvas.clipRect(cacheRf);
                canvas.drawRoundRect(cacheRf, r, r, mDefaultPaint);
                canvas.restore();
            } else {
                if (mSelectIndex > mCacheIndex) {
                    rectf = new RectF(left, top, left + mIndicatorWidth + total * av, top + mIndicatorWidth);
                } else {
                    rectf = new RectF(left + total * av, top, left + mIndicatorWidth, top + mIndicatorWidth);
                }
            }
            canvas.save();
            drawIndicators(canvas, sx, sy, r, true);
            canvas.restore();

            canvas.save();
            canvas.clipRect(rectf);
            canvas.drawRoundRect(rectf, r, r, mPaint);
            canvas.restore();
        } else {
            drawIndicators(canvas, sx, sy, r, false);
        }
    }

    public LnkPageIndicator setAnimInterpolator(Interpolator interpolator){
        this.mAnimator.setInterpolator(interpolator);
        return this;
    }

    private void drawIndicators(Canvas canvas, float sX, float sY, float r, boolean anim) {
        for (int i = 0; i < mMax; i++) {
            if (anim) {
                if (i != mCacheIndex) {
                    canvas.drawCircle(sX, sY, r, mDefaultPaint);
                }
            } else {
                if (mSelectIndex == i) {
                    canvas.drawCircle(sX, sY, r, mPaint);
                } else {
                    canvas.drawCircle(sX, sY, r, mDefaultPaint);
                }
            }
            sX += mIndicatorWidth + mIndicatorSpacing;
        }
    }

    public int getMax() {
        return mMax;
    }

    public void setSelectIndex(int index) {

        if (mAnimator.isRunning()) return;
        if (mSelectIndex == index) return;

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
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mCacheIndex = mSelectIndex;
                mRepeat = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mRepeat = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                mRepeat = true;
            }
        });
        mAnimator.start();
    }

    public int getAnimDuration() {
        return mAnimDuration;
    }

    public LnkPageIndicator setAnimDuration(int mAnimDuration) {
        this.mAnimDuration = mAnimDuration;
        return this;
    }

    public int getDefaultColor() {
        return mDefaultColor;
    }

    public LnkPageIndicator setDefaultColor(int mDefaultColor) {
        this.mDefaultColor = mDefaultColor;
        return this;
    }

    public int getSelectColor() {
        return mSelectColor;
    }

    public LnkPageIndicator setSelectColor(int mSelectColor) {
        this.mSelectColor = mSelectColor;
        return this;
    }

    public int getIndicatorWidth() {
        return mIndicatorWidth;
    }

    public LnkPageIndicator setIndicatorWidth(int mIndicatorWidth) {
        this.mIndicatorWidth = mIndicatorWidth;
        return this;

    }

    public int getIndicatorSpacing() {
        return mIndicatorSpacing;
    }

    public LnkPageIndicator setIndicatorSpacing(int mIndicatorSpacing) {
        this.mIndicatorSpacing = mIndicatorSpacing;
        return this;
    }


    public LnkPageIndicator setMax(int mMax) {
        this.mMax = mMax;
        return this;
    }

    public int getSelectIndex() {
        return mSelectIndex;
    }

    public boolean isAnim() {
        return mAnimator.isRunning();
    }

}
