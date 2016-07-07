package com.haozhang.indicator;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.haozhang.widgets.LnkPageIndicator;

public class MainActivity extends AppCompatActivity {

    private com.haozhang.widgets.LnkPageIndicator indicator;
    private android.support.v4.view.ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.indicator = (LnkPageIndicator) findViewById(R.id.indicator);

    }

    class MyAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
}
