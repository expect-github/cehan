package com.nj.baijiayun.module_public.helper;

/**
 * @author chengang
 * @date 2019-07-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class JumpWrapBean {

    /**
     * 跳转类型 0无链接 1链接 2课程 3 资讯 4:图书 5:文库
     */
    private int type;
    private String typeContent;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeContent() {
        return typeContent;
    }

    public int getId() {
        try {
            return Integer.parseInt(typeContent);
        } catch (Exception ee) {
            return 0;
        }
    }

    public void setTypeContent(String typeContent) {
        this.typeContent = typeContent;
    }
}
