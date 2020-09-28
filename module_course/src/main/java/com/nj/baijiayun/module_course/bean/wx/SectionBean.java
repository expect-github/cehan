package com.nj.baijiayun.module_course.bean.wx;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.downloader.realmbean.DownloadItem;
import com.nj.baijiayun.module_public.bean.PublicTeacherBean;
import com.nj.baijiayun.refresh.recycleview.ITreeModel;
import com.nj.baijiayun.refresh.recycleview.TreeItemExpandAttr;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.bean.wx
 * @describe 节
 */
public class SectionBean implements ITreeModel {

    /**
     * id : 19
     * classify_title : 第一分类01
     * basis_title : 大班课12
     * advance_time : 10
     * parent_id : 1
     * start_play : 07-20 00:00
     * end_play : 07-21 00:00
     * is_playback : 1
     * is_try_see : 0
     * is_download : 1
     * try_see_time : 0
     * course_type : 5
     * is_free : 0
     * teacher_id : 10
     * chapter_title : 第一章
     * is_vip_class :
     * total_start_play : 2019-07-20 00:00
     * total_end_play : 2019-07-21 00:00
     * play_type : 2
     * video_id : 123456
     * datum : []
     * teachers : [{"id":10,"teacher_name":"张三疯"}]
     */

    /**
     * 这个id是逻辑表id 章节映射表的id
     * 由于一个节可以对应多个章 所有有这个关系id
     */
    private int id;
    @SerializedName("classify_title")
    private String classifyTitle;
    @SerializedName("basis_title")
    private String basisTitle;
    @SerializedName("advance_time")
    private int advanceTime;
    /**
     * 章id
     */
    @SerializedName("parent_id")
    private int parentId;
    @SerializedName("start_play")
    private String startPlay;
    @SerializedName("end_play")
    private String endPlay;
    @SerializedName("is_playback")
    private int isPlayback;
    @SerializedName("is_try_see")
    private int isTrySee;
    @SerializedName("is_download")
    private int isDownload;
    @SerializedName("try_see_time")
    private int trySeeTime;
    @SerializedName("course_type")
    private int courseType;
    @SerializedName("is_free")
    private int isFree;
    @SerializedName("teacher_id")
    private String teacherId;
    @SerializedName("chapter_title")
    private String chapterTitle;
    @SerializedName("is_vip_class")
    private String isVipClass;
    @SerializedName("total_start_play")
    private String totalStartPlay;
    @SerializedName("total_end_play")
    private String totalEndPlay;
    /**
     * 1未开始 2进行中(直播中,开课中) 3停播(暂无回放,已结束) 4重播(查看回放)
     */
    @SerializedName("play_type")
    private int playType;
    @SerializedName("video_id")
    private String videoId;

    private List<PublicTeacherBean> teachers;
    @SerializedName("progress_rate")
    private int progressRate;
    private boolean isChecked = false;
    private int downloadState = DownloadItem.DOWNLOAD_STATUS_WAITING;
    private int downloadProgress;
    private List<DatumBean> datum;
    @SerializedName("periods_title")
    private String periodsTitle;
    /**
     * 这个才是真正的唯一id 目前仅仅用于跳作业
     */
    @SerializedName("period_id")
    private int periodId;


    public int getPeriodId() {
        return periodId;
    }

    private HomeWorkBean homework;

    public HomeWorkBean getHomework() {
        if (homework == null) {
            homework = new HomeWorkBean();
        }
        return homework;
    }

    public SectionBean() {
        abstractTreeItemAttr.onExpand();
    }


    private TreeItemExpandAttr abstractTreeItemAttr = new TreeItemExpandAttr(this);

    private List<DatumBean> splitDatum;

    public List<DatumBean> getDatum() {
        if (datum == null || datum.size() == 0) {
            return null;
        }
        DatumBean sourceDatumBean = datum.get(0);
        String[] split = sourceDatumBean.getFileName().split(";");
        if (split.length == 0) {
            return null;
        }
        if (splitDatum == null) {
            splitDatum = new ArrayList<>(split.length);
        }
        if (splitDatum.size() == 0) {
            for (int i = 0; i < split.length; i++) {
                DatumBean clone = (DatumBean) sourceDatumBean.clone();
                clone.setFileName(split[i]);
                splitDatum.add(clone);
            }
        }
        return splitDatum;

    }

    public void setDatum(List<DatumBean> datum) {
        this.datum = datum;
    }

    public String getPeriodsTitle() {
        return periodsTitle;
    }

    public String getSectionTitle() {
        return periodsTitle;
    }

    public void setPeriodsTitle(String periodsTitle) {
        this.periodsTitle = periodsTitle;
    }

    @Override
    public List<? extends ITreeModel> getChilds() {
        return null;
    }

    @Override
    public TreeItemExpandAttr getTreeItemAttr() {
        return abstractTreeItemAttr;
    }

    public boolean isCanTrySee() {
        return isTrySee == 1;
    }

    public int getPlayType() {
        return playType;
    }

    public int getCourseType() {
        return courseType;
    }

    public List<PublicTeacherBean> getTeachers() {
        return teachers;
    }

    public boolean hasTeacher() {
        return teachers != null && teachers.size() > 0;
    }

    public String getFirstTeacherName() {
        if (hasTeacher()) {
            return teachers.get(0).getName();
        }
        return "";
    }

    public String getStartPlay() {
        return startPlay;
    }

    public String getEndPlay() {
        return endPlay;
    }

    public boolean isCanDownLoad() {
        return isDownload == 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProgressRate() {
        return progressRate;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(int downloadState) {
        this.downloadState = downloadState;
    }

    public int getDownloadProgress() {
        return downloadProgress;
    }

    public void setDownloadProgress(int downloadProgress) {
        this.downloadProgress = downloadProgress;
    }

    public boolean isDownloadComplete() {
        return downloadState == DownloadItem.DOWNLOAD_STATUS_COMPLETE;
    }

    public boolean isDownloadInProgress() {
        return downloadState == DownloadItem.DOWNLOAD_STATUS_DOWNLOADING;
    }


}
