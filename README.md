# ShadowDrawable
为View 和 ViewGroup 添加阴影效果

#Sample

        View bg = findViewById(R.id.rl_bg);
        ShadowDrawable shadowDrawable = new ShadowDrawable();
        shadowDrawable.setColor(ContextCompat.getColor(this, R.color.my_blur))
                .setOffsetY(DensityUtil.dip2px(this, 5))
                .setRadius(DensityUtil.dip2px(this, 8))
                .setSoftline(DensityUtil.dip2px(this, 8))
                .setFilterColor(0x56ffffff)
                .setTopMargin(DensityUtil.dip2px(this, 3))
                .attach(bg)
                .build();
