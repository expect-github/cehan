package com.nj.baijiayun.module_public.helper.videoplay;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.basic.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BjyTokenWrapperBean {
    @SerializedName("token_info")
    private List<BjyTokenData> tokenInfo;
    @SerializedName("room_info")
    private List<LiveRoomParams> roomParams;

    public BjyTokenData getTokenData() {
        if (tokenInfo == null || tokenInfo.size() == 0) {
            return null;
        }
        return tokenInfo.get(0);
    }

    public List<BjyTokenData> getTokenListData() {
        if (tokenInfo == null) {
            tokenInfo = new ArrayList<>();
        }
        return tokenInfo;
    }

    public List<LiveRoomParams> getRoomParams() {
        if (roomParams == null) {
            roomParams = new ArrayList<>();
        }
        return roomParams;
    }

    public LiveRoomParams getLiveRoomParams() {
        if (roomParams == null || roomParams.size() == 0) {
            return null;
        }
        return roomParams.get(0);
    }

    public static class LiveRoomParams {


        /**
         * room_id : 19053060509775
         * user_number : 19
         * user_name : ZYDS
         * user_role : 0
         * user_avatar : http://test.api.hdjy.zhaoyongkang.com/uploads/images/avatar.jpguploads/images/avatar.jpg
         * group_id : 1
         * sign : 1329a44b74257e24d1d0402f58381fac
         */

        private String room_id;
        private String user_number;
        private String user_name;
        private int user_role;
        private String user_avatar;
        private int group_id;
        private String sign;

        public String getRoom_id() {
            if (StringUtils.isEmpty(room_id)) {
                return "0";
            }
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getUser_number() {
            return user_number;
        }

        public void setUser_number(String user_number) {
            this.user_number = user_number;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public int getUser_role() {
            return user_role;
        }

        public void setUser_role(int user_role) {
            this.user_role = user_role;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
