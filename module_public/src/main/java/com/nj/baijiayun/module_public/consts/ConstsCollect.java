package com.nj.baijiayun.module_public.consts;

/**
 * @author chengang
 * @date 2019-07-16
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.consts
 * @describe
 */
public class ConstsCollect {
    //业务类型：login登录 getPassword找回密码 editMobile修改手机 loginOauth第三方登录绑定 boundBank绑定银行卡 downOrder下单

    public static final int TEACHER = 2;
    public static final int COURSE = 1;
    public static final int LIBRARY = 5;
//    类型 1 课程收藏 2 收藏老师 3 收藏试题 4 图书收藏 5 文库收藏 6:帖子收藏

    public static String getParamsKey(int type) {
        String result = "id";
        switch (type) {
            default:
                break;
            case COURSE:
                result = "course_basis_id";
                break;
            case TEACHER:
                result = "teacher_id";
                break;
            case LIBRARY:
                result = "library_id";
                break;
        }
        return result;

    }


}
