package com.nj.baijiayun.module_public.helper;

import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.util.SparseArray;

import com.caverock.androidsvg.RenderOptions;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

/**
 * @author chengang
 * @date 2019-11-28
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class PriceIconHelper {

    private static SparseArray<IconKey> iconSparseArray = new SparseArray<>();
    //需要缓存

    public static Drawable getVectorDrawable(String svgData, int filterColor, float fontHeight, boolean needBold, float heightScale) {
        int hashCode = getHashCode((int) fontHeight, filterColor, needBold);
        IconKey iconKey = iconSparseArray.get(hashCode);
        Picture picture = null;
        if (iconKey == null) {
            iconKey = new IconKey();
            try {

                RenderOptions renderOptions = new RenderOptions();
                SVG svg = SVG.getFromString(svgData);
                renderOptions.css(String.format("path{fill:%s;}", getHexString(filterColor)));
                int h = (int) (fontHeight *  heightScale);
                svg.setDocumentWidth((svg.getDocumentWidth() * h / svg.getDocumentHeight()));
                svg.setDocumentHeight(h);
                picture = svg.renderToPicture(renderOptions);
                iconKey.picture = picture;
                iconKey.width = (int) svg.getDocumentWidth();
                iconKey.height = (int) svg.getDocumentHeight();
                iconKey.top = (int) (h - fontHeight) / 2;
                iconSparseArray.put(hashCode, iconKey);
            } catch (SVGParseException e) {
                e.printStackTrace();
            }
        } else {
            picture = iconKey.picture;
        }
        PictureDrawable pictureDrawable = new PictureDrawable(picture);
        pictureDrawable.setBounds(0, iconKey.top, iconKey.width, iconKey.height);

        return pictureDrawable;
    }

    private static String getHexString(int color) {
        String s = "#";
        int colorStr = ((color & 0x00ff0000) | (color & 0x0000ff00) | (color & 0x000000ff));
        s = s + Integer.toHexString(colorStr);
        return s;
    }

    private static class IconKey {

        private int width;
        private int height;
        private int top;
        private Picture picture;


    }

    private static int getHashCode(int height, int color, boolean needBold) {
        return height + color + (needBold ? 1000 : 0);
    }


}
