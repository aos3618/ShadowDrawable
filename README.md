# ShadowDrawable
为View 和 ViewGroup 添加阴影效果
Android , Add shadow for View or ViewGroup

# Sample

        View bg = findViewById(R.id.rl_bg);
        ShadowDrawable shadowDrawable = new ShadowDrawable();
        shadowDrawable.setColor(ContextCompat.getColor(this, R.color.my_blur))    //shadowcolor
                .setOffsetY(DensityUtil.dip2px(this, 5))    //阴影下偏移--offset of the shadow
                .setRadius(DensityUtil.dip2px(this, 8))     //四角半径--concern of the rectangle
                .setSoftline(DensityUtil.dip2px(this, 8))   //四周阴影半径-- the shadow of each edge of the rectangle
                .setFilterColor(0x56ffffff)      //中间值，越大阴影越接近设置的值-- the slot to said how close to the shadowcolor
                .setTopMargin(DensityUtil.dip2px(this, 3))  //上间距--top margin
                .setParentHeight(DensityUtil.dip2px(this, 200))  //设置要依附的View的高度 -- the height of parent view
                .attach(bg)                                 //要在哪个View上面加阴影-- the shadow parent.MUST
                .build();                                   //显示，必调-- to show the shadow.MUST
                
# Result
  ![image](https://github.com/aos3618/ShadowDrawable/blob/master/Shadow/shadow.jpg)
