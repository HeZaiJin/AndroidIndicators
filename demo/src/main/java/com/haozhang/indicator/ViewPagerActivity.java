package com.haozhang.indicator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Scroller;

import com.haozhang.widgets.LnkViewPagerIndicator;

import java.lang.reflect.Field;

public class ViewPagerActivity extends AppCompatActivity {
    private static final String TAG = "ViewPagerActivity";
    LnkViewPagerIndicator mIndicator;
    ViewPager mViewPager;
    ImageView[] mImageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mIndicator = (LnkViewPagerIndicator) findViewById(R.id.indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        MyAdapter adapter = new MyAdapter(4);
        mIndicator.setMax(4);
        mViewPager.setAdapter(adapter);
        setScrollSpeedUsingRefection(mViewPager,1000);
        mViewPager.setOnPageChangeListener(mIndicator);
    }
    private void setScrollSpeedUsingRefection(ViewPager viewPager,int duration) {
        try {
            Field localField = viewPager.getClass().getDeclaredField("mScroller");
            localField.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(ViewPagerActivity.this, new DecelerateInterpolator(1.5F));
            scroller.setDuration(duration);
            localField.set(viewPager, scroller);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class FixedSpeedScroller extends Scroller {

        private int mDuration = 1000;
        boolean useFixedSpeed = false;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        public void setScrollAtFixedSpeed(int paramInt) {
            this.useFixedSpeed = true;
            this.mDuration = paramInt;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);

        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setDuration(int timeMilli){
            this.mDuration = timeMilli;
        }
    }

    class MyAdapter extends PagerAdapter{
        int max;

        public MyAdapter(int max) {
            this.max = max;
        }

        @Override
        public int getCount() {
            return max;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return getItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }

    public View getItem(ViewGroup parent, int position) {

        if (null == mImageViews) {
            int[] res = new int[]{R.drawable.ic_google, R.drawable.fb, R.drawable.github, R.drawable.gg};
            mImageViews = new ImageView[4];
            for (int i = 0; i < 4; i++) {
                ImageView img = new ImageView(ViewPagerActivity.this);
                img.setImageResource(res[i]);
                img.setScaleType(ImageView.ScaleType.CENTER);
                mImageViews[i] = img;
            }
        }
        View view = mImageViews[position];
        parent.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return view;
    }
}
