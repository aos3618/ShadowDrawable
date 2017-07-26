package com.aos.shadowlib.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntRange;
import android.view.View;
import android.view.ViewGroup;

/**
 * ShadowDrawable shadowDrawable = new ShadowDrawable();
 * shadowDrawable.setColor(ContextCompat.getColor(this, R.color.my_blur))
 * .setOffsetY(DensityUtil.dip2px(this, 5))
 * .setRadius(DensityUtil.dip2px(this, 8))
 * .setSoftline(DensityUtil.dip2px(this, 8))
 * .setFilterColor(0x56ffffff)
 * .setTopMargin(DensityUtil.dip2px(this, 3))
 * .attach(mCenterContainer)
 * .build();
 * <p>
 * Created by AoS on 2017/7/14.
 */

public class ShadowDrawable extends Drawable {
    private Paint paint;
    private Paint paintShadow;
    private Path path;
    private RectF rectF;
    private View parent;

    private int color = 0x00000000;
    private int radius = 10;
    private int offsetX = 0;
    private int offsetY = 10;
    private int softline = 20;
    private int colorFilter = 0x16ffffff;
    private int parentHeight = 0;
    private int topMargin = 0;

    public ShadowDrawable() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);

        paintShadow = new Paint();
        paintShadow.setColor(Color.WHITE);
        paintShadow.setAntiAlias(true);

        path = new Path();
    }

    /**
     * 默认
     *
     * @param c
     */
    public ShadowDrawable setDefault(int c) {
        this.color = c;
        paintShadow.setShadowLayer(softline, offsetX, offsetY, color & colorFilter);
        return this;
    }

    /**
     * 这是圆角半径
     *
     * @param r
     * @return
     */
    public ShadowDrawable setRadius(int r) {
        this.radius = r;
        paintShadow.setShadowLayer(softline, offsetX, offsetY, color & colorFilter);
        return this;
    }

    /**
     * 柔边
     *
     * @param s
     * @return
     */
    public ShadowDrawable setSoftline(int s) {
        this.softline = s;
        paintShadow.setShadowLayer(softline, offsetX, offsetY, color & colorFilter);
        return this;
    }

    /**
     * 最深的颜色
     *
     * @param c
     * @return
     */
    public ShadowDrawable setColor(int c) {
        this.color = c;
        paintShadow.setShadowLayer(softline, offsetX, offsetY, color & colorFilter);
        return this;
    }

    /**
     * 一个中间值 越接近 0xffffffff 阴影初始颜色越深
     *
     * @param c
     * @return
     */
    public ShadowDrawable setFilterColor(int c) {
        this.colorFilter = c;
        paintShadow.setShadowLayer(softline, offsetX, offsetY, color & colorFilter);
        return this;
    }

    public ShadowDrawable setOffsetX(int x) {
        this.offsetX = x;
        paintShadow.setShadowLayer(softline, offsetX, offsetY, color & colorFilter);
        return this;
    }

    /**
     * 下面阴影偏移
     *
     * @param y
     * @return
     */
    public ShadowDrawable setOffsetY(int y) {
        this.offsetY = y;
        paintShadow.setShadowLayer(softline, offsetX, offsetY, color & colorFilter);
        return this;
    }

    /**
     * 上方阴影深度
     *
     * @param y
     * @return
     */
    public ShadowDrawable setTopMargin(int y) {
        this.topMargin = y;
        return this;
    }

    /**
     * 要依附的view
     *
     * @param view
     * @return
     */
    public ShadowDrawable attach(View view) {
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        parent = view;
        return this;
    }

    /**
     * 要依附的view 的高度
     *
     * @param parentHeight
     * @return
     */
    public ShadowDrawable setParentHeight(int parentHeight) {
        this.parentHeight = parentHeight;
        return this;
    }

    /**
     * 显示
     */
    public void build() {
        if (parent == null) {
            throw new UnsupportedOperationException("shadow drawable must have a parent");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            parent.setBackground(this);
        } else {
            parent.setBackgroundDrawable(this);
        }
        if (parentHeight != 0) {
            ViewGroup.LayoutParams lp = parent.getLayoutParams();
            lp.height = parentHeight /*+ parent.getMeasuredHeight()*/;
        }
        invalidateSelf();
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(path, paintShadow);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        paintShadow.setAlpha(alpha);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
//        Rect rect = new Rect(bounds.left, bounds.top, bounds.right + offsetX, bounds.bottom + offsetY);
        super.onBoundsChange(bounds);

        path.reset();
        rectF = new RectF(softline, topMargin, bounds.width() - softline, bounds.height() - offsetY - softline);
        path.addRoundRect(rectF, new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, Path.Direction.CW);

    }

    public ShadowDrawable setShadowAlpha(int i) {
        setAlpha(i);
        return this;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
