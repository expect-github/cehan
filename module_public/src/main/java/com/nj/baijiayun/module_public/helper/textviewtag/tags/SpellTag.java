package com.nj.baijiayun.module_public.helper.textviewtag.tags;

import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.helper.textviewtag.AbstractTagAttr;

/**
 * @author chengang
 * @date 2020-03-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.textviewtag.tags
 * @describe
 */
public class SpellTag extends AbstractTagAttr {
    @Override
    public int getBgColor() {
        return R.color.public_tag_spell_bg;
    }

    @Override
    public int getTextColor() {
        return R.color.public_tag_spell_text;
    }

    @Override
    public String getDefaultText() {
        return "拼";
    }

    @Override
    protected int getTriangleColor() {
        return R.color.public_tag_spell_bg_triangle;
    }
}
