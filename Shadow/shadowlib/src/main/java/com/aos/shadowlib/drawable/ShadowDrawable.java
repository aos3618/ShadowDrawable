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
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

/**
 * View bg = findViewById(R.id.rl_bg);
 * ShadowDrawable shadowDrawable = new ShadowDrawable();
 * shadowDrawable.setColor(ContextCompat.getColor(this, R.color.my_blur))    //shadowcolor
 * .setOffsetY(DensityUtil.dip2px(this, 5))    //阴影下偏移--offset of the shadow
 * .setRadius(DensityUtil.dip2px(this, 8))     //四角半径--concern of the rectangle
 * .setEdgeShadowWidth(DensityUtil.dip2px(this, 8))   //四周阴影半径-- the shadow of each edge of the rectangle
 * .setFilterColor(0x56ffffff)                 //中间值，越大阴影越接近设置的值-- the slot to said how close to the shadowcolor
 * .setTopMargin(DensityUtil.dip2px(this, 3))  //上间距--top margin
 * .setParentHeight(DensityUtil.dip2px(this, 200))  //设置要依附的View的高度 -- the height of parent view
 * .attach(bg)                                 //要在哪个View上面加阴影-- the shadow parent.※
 * .build();                                   //显示，必调-- to show the shadow.※
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
    private int edgeShadowWidth = 20;
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
        paintShadow.setShadowLayer(edgeShadowWidth, offsetX, offsetY, color & colorFilter);
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
        paintShadow.setShadowLayer(edgeShadowWidth, offsetX, offsetY, color & colorFilter);
        return this;
    }

    /**
     * 四周阴影宽度
     *
     * @param s
     * @return
     */
    public ShadowDrawable setEdgeShadowWidth(int s) {
        this.edgeShadowWidth = s;
        paintShadow.setShadowLayer(edgeShadowWidth, offsetX, offsetY, color & colorFilter);
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
        paintShadow.setShadowLayer(edgeShadowWidth, offsetX, offsetY, color & colorFilter);
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
        paintShadow.setShadowLayer(edgeShadowWidth, offsetX, offsetY, color & colorFilter);
        return this;
    }

    public ShadowDrawable setOffsetX(int x) {
        this.offsetX = x;
        paintShadow.setShadowLayer(edgeShadowWidth, offsetX, offsetY, color & colorFilter);
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
        paintShadow.setShadowLayer(edgeShadowWidth, offsetX, offsetY, color & colorFilter);
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
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        path.reset();
        rectF = new RectF(edgeShadowWidth, topMargin, bounds.width() - edgeShadowWidth, bounds.height() - offsetY - edgeShadowWidth);
        path.addRoundRect(rectF, new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, Path.Direction.CW);

    }

    public ShadowDrawable setShadowAlpha(int i) {
        setAlpha(i);
        return this;
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
