package com.haozhang.widgets;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

/**
 * @author HaoZhang
 * @hide
 */
public class LnkViewPagerIndicatorManager {
    private static final String TAG = "LVPIManager";
    public static final int MODE_FORWORD = 0;
    public static final int MODE_CLOSE = 1;
    public static final int MODE_RESET = 2;
    public static final int TYPE_LEFT = 3;
    public static final int TYPE_RIGHT = 4;

    private int mode;
    private int type = TYPE_LEFT;
    private int position;
    private float positionOffset;

    private int width = -1;
    private int height;
    private int indicatorWidth;
    private int indicatorSpacing;

    private int canvasWidth;
    private int canvasHeight;
    private int selectIndex;
    float sX;
    float sY;

    float left;
    float top;


    public void init(Canvas canvas, int max, int indicatorWidth, int indicatorSpacing, int position) {
        if (width == -1) {
            selectIndex = position;
            this.position = position;
            width = canvas.getWidth();
            height = canvas.getHeight();
            this.indicatorWidth = indicatorWidth;
            this.indicatorSpacing = indicatorSpacing;
            canvasWidth = indicatorSpacing * (max - 1) + indicatorWidth * max;
            left = (width - canvasWidth) / 2 + position * (indicatorWidth + indicatorSpacing);
            top = (height - indicatorWidth) / 2;
            int r = indicatorWidth / 2;
            sX = (width - canvasWidth) / 2 + r;
            sY = (height - indicatorWidth) / 2 + r;
        }
    }

    public float getsX() {
        return sX;
    }

    public float getsY() {
        return sY;
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    public int getPosition() {
        return position;
    }

    public LnkViewPagerIndicatorManager setPosition(int position) {
        this.position = position;
        if (selectIndex > position){
            // right
            Log.d(TAG,"type right");
            type = TYPE_RIGHT;
        }else {
            Log.d(TAG,"type left");
            type = TYPE_LEFT;
        }
        return this;
    }

    public float getPositionOffset() {
        return positionOffset;
    }


    public int getMode() {
        return mode;
    }

    public RectF getForwordRectF(){
        RectF rectF = null;
        if (type == TYPE_LEFT) {
            // start index is postion
            float left = (width - canvasWidth) / 2 + position * (indicatorSpacing + indicatorWidth);
            rectF = new RectF(left,top,left+2 * positionOffset * indicatorSpacing +indicatorWidth,top+indicatorWidth);
        }else {
            // start index is selecIndex
            float left = (width - canvasWidth) / 2 + selectIndex * (indicatorSpacing + indicatorWidth);
            rectF = new RectF(left - 2 * positionOffset * indicatorSpacing,top,left + indicatorWidth,top+indicatorWidth);
        }
        return rectF;
    }



    public LnkViewPagerIndicatorManager setPositionOffset(float positionOffset) {
        this.positionOffset = positionOffset;

        if ((type==TYPE_LEFT && positionOffset > 0.5f) || (type==TYPE_RIGHT && positionOffset < 0.5f)) {
            // start close anim
            mode = MODE_CLOSE;
        } else if (positionOffset == 0) {
            // reset point
            selectIndex = position;
            mode = MODE_RESET;
        } else {
            // normal forword anim
            mode = MODE_FORWORD;
        }
        return this;
    }
}
