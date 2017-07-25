package com.aos.shadow;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aos.shadowlib.drawable.ShadowDrawable;
import com.aos.shadowlib.utils.DensityUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {

        View bg = findViewById(R.id.rl_bg);
        ShadowDrawable shadowDrawable = new ShadowDrawable();
        shadowDrawable.setColor(ContextCompat.getColor(this, R.color.my_blur))    //shadowcolor
                .setOffsetY(DensityUtil.dip2px(this, 5))    //阴影下偏移--offset of the shadow
                .setRadius(DensityUtil.dip2px(this, 8))     //四角半径--concern of the rectangle
                .setSoftline(DensityUtil.dip2px(this, 8))   //四周阴影半径-- the shadow of each edge of the rectangle
                .setFilterColor(0x56ffffff)                 //中间值，越大阴影越接近设置的值-- the slot to said how close to the shadowcolor
                .setTopMargin(DensityUtil.dip2px(this, 3))  //上间距--top margin
                .setParentHeight(DensityUtil.dip2px(this, 200))  //设置要依附的View的高度 -- the height of parent view
                .attach(bg)                                 //要在哪个View上面加阴影-- the shadow parent.※
                .build();                                   //显示，必调-- to show the shadow.※
    }
}
