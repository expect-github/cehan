package com.nj.baijiayun.module_public.helper.update;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.Keep;

@Keep
public class AppUpdateBean {
    private String status;
    private String msg;
    private AppUpdateData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AppUpdateData getData() {
        return data;
    }

    public void setData(AppUpdateData data) {
        this.data = data;
    }

    public static class AppUpdateData implements Parcelable {
        /**
         * id : 5
         * version_name : 1.2
         * version_code : 2
         * version_detail : 编辑更新
         * apk_address : http://www.zywx.test/api/version/update/5
         * is_force_update : 0
         * type : 1
         * type_name : ios
         */

        private int id;
        @SerializedName("version")
        private String version_name;
        @SerializedName("version_code")
        private long version_code;
        @SerializedName("content")
        private String version_detail;
        @SerializedName("download_url")
        private String download_url;
        private String is_force_update;
        private int type;
        private String type_name;
        @SerializedName("download_set")
        private int forceUpdate;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

        public long getVersion_code() {
            return version_code;
        }

        public void setVersion_code(long version_code) {
            this.version_code = version_code;
        }

        public String getVersion_detail() {
            return version_detail;
        }

        public void setVersion_detail(String version_detail) {
            this.version_detail = version_detail;
        }


        public String getApk_address() {
            return download_url;
        }


        public String getIs_force_update() {
            return is_force_update;
        }

        public boolean isForceUpdate() {
            return forceUpdate == 2;
        }

        public void setIs_force_update(String is_force_update) {
            this.is_force_update = is_force_update;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.version_name);
            dest.writeLong(this.version_code);
            dest.writeString(this.version_detail);
            dest.writeString(this.download_url);
            dest.writeString(this.is_force_update);
            dest.writeInt(this.type);
            dest.writeString(this.type_name);
            dest.writeInt(this.forceUpdate);
        }

        public AppUpdateData() {
        }

        protected AppUpdateData(Parcel in) {
            this.id = in.readInt();
            this.version_name = in.readString();
            this.version_code = in.readLong();
            this.version_detail = in.readString();
            this.download_url = in.readString();
            this.is_force_update = in.readString();
            this.type = in.readInt();
            this.type_name = in.readString();
            this.forceUpdate = in.readInt();
        }

        public static final Creator<AppUpdateData> CREATOR = new Creator<AppUpdateData>() {
            @Override
            public AppUpdateData createFromParcel(Parcel source) {
                return new AppUpdateData(source);
            }

            @Override
            public AppUpdateData[] newArray(int size) {
                return new AppUpdateData[size];
            }
        };
    }
}
