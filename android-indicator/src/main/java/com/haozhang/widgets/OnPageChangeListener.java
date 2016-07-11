package com.haozhang.widgets;

/**
 * @author HaoZhang
 */
public interface OnPageChangeListener {
    abstract void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    abstract void onPageSelected(int position);

    abstract void onPageScrollStateChanged(int state);
}
