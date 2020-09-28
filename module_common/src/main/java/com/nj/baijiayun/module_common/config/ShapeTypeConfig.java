package com.nj.baijiayun.module_common.config;
public enum ShapeTypeConfig {
    QQ(0),
    QQZONE(1),
    WX(2),
    WXP(3),
    SINA(4),
    IMG(5),
    REPORT(6);
    private int type;

    ShapeTypeConfig(int var3) {
        this.type = var3;
    }
    public static ShapeTypeConfig from(int var0) {
        switch(var0) {
            case 0:
                return QQ;
            case 1:
                return QQZONE;
            case 2:
                return WX;
            case 3:
                return WXP;
            case 4:
                return SINA;
            case 5:
                return IMG;
            default:
                return QQ;
        }
    }

    public int getType() {
        return this.type;
    }
}
