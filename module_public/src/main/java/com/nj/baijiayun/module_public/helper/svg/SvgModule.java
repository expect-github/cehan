package com.nj.baijiayun.module_public.helper.svg;

import android.content.Context;
import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.module.AppGlideModule;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;

/**
 * @author chengang
 * @date 2019-11-29
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.svg
 * @describe
 */
@GlideModule
public class SvgModule extends AppGlideModule {
    @Override
    public void registerComponents(
            Context context, Glide glide, Registry registry) {
        registry
                .register(SVG.class, PictureDrawable.class, (toTranscode, options) -> {
                    SVG svg = toTranscode.get();
                    Picture picture = svg.renderToPicture();
                    PictureDrawable drawable = new PictureDrawable(picture);
                    return new SimpleResource<>(drawable);
                })
                .append(InputStream.class, SVG.class, new SvgDecoder());
    }

    // Disable manifest parsing to avoid adding similar modules twice.
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}