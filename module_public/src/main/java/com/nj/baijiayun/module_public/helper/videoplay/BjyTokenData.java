package com.nj.baijiayun.module_public.helper.videoplay;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.basic.utils.StringUtils;

public class BjyTokenData implements Parcelable {


    /**
     * type : 1
     * sub_type : huifang
     * video_id : 12005331
     * room_id : 18071275316531
     * token : TU6X2zkSBF-wCRjhQyTd4NRtjiPn9eGeqj6SWfJe6PzJ3UQJWJQTLjTSEzZrILF4
     */

    private String type;
    private String sub_type;
    private String video_id;
    private String room_id;
    private String token;
    @SerializedName("student_code")
    private String studentCode;
    private int courseChapterId;
    @SerializedName("course_periods_id")
    private int coursePeriodsId;
    @SerializedName("courseId")
    private int courseId;

    protected BjyTokenData(Parcel in) {
        type = in.readString();
        sub_type = in.readString();
        video_id = in.readString();
        room_id = in.readString();
        token = in.readString();
        studentCode = in.readString();
        courseChapterId = in.readInt();
        coursePeriodsId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(sub_type);
        dest.writeString(video_id);
        dest.writeString(room_id);
        dest.writeString(token);
        dest.writeString(studentCode);
        dest.writeInt(courseChapterId);
        dest.writeInt(coursePeriodsId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BjyTokenData> CREATOR = new Creator<BjyTokenData>() {
        @Override
        public BjyTokenData createFromParcel(Parcel in) {
            return new BjyTokenData(in);
        }

        @Override
        public BjyTokenData[] newArray(int size) {
            return new BjyTokenData[size];
        }
    };

    public String getStudentCode() {
        return studentCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTypeLive() {
        this.type = "1";
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public String getVideo_id() {
        if (video_id == null || video_id.length() <= 0) {
            return room_id;
        }
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public boolean isRoomEmpty() {
        return StringUtils.isEmpty(room_id) || "0".equals(room_id);
    }


    public String getRoom_id() {
        if (StringUtils.isEmpty(room_id)) {
            return "0";
        }
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getToken() {

        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BjyTokenData() {
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCoursePeriodsId(int coursePeriodsId) {
        this.coursePeriodsId = coursePeriodsId;
    }

    public int getCoursePeriodsId() {
        return coursePeriodsId;
    }

    public void setCourseChapterId(int courseChapterId) {
        this.courseChapterId = courseChapterId;
    }

    public int getCourseChapterId() {
        return courseChapterId;
    }
}
