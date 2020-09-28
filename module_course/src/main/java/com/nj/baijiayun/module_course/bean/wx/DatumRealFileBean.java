package com.nj.baijiayun.module_course.bean.wx;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-08-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.bean.wx
 * @describe
 */
public class DatumRealFileBean {

    /**
     * datum_id : 7
     * file_name : 报告规范.pdf
     * file_url : https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/file/2019osX5cy4EAu1565580162.pdf
     */

    @SerializedName("datum_id")
    private int datumId;
    @SerializedName("file_name")
    private String fileName;
    @SerializedName("file_url")
    private String fileUrl;
    @SerializedName("spell_status")
    private int spellStatus;
    @SerializedName("spell_status_tips")
    private String spellStatusTips;

    public int getDatumId() {
        return datumId;
    }

    public void setDatumId(int datumId) {
        this.datumId = datumId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public boolean needShowUnOpenToast() {
        return spellStatus == 0;
    }

    public String getShowMag() {
        return spellStatusTips;
    }

}
