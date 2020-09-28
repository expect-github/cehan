package com.nj.baijiayun.module_public.helper.textviewtag;

import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.widget.IconTextSpan;

/**
 * @author chengang
 * @date 2020-03-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.textviewtag
 * @describe
 */
public abstract class AbstractTagAttr {

    public AbstractTagAttr() {
        createIconSpan();
    }

    private IconTextSpan iconTextSpan;

    protected abstract int getBgColor();

    protected abstract int getTextColor();

    protected abstract String getDefaultText();

    public void createIconSpan() {
        iconTextSpan = new IconTextSpan(BaseApp.getInstance(), getDefaultText()).setBgColorResId(getBgColor())
                .setTextColorResId(getTextColor())
//                .setBgTriangleResId(getTriangleColor())
                .setAttrToPaint();

    }

    public IconTextSpan getIconTextSpan() {
        return iconTextSpan;
    }

    protected  abstract  int getTriangleColor();
}
