package com.thirdpart.vo;

/**
 * Created by Administrator on 2015/4/24.
 */
public class AccessKeyVO {
    private String accessKey;
    private String user;
    private String thirdPartMark;
    private String userKey;

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getThirdPartMark() {
        return thirdPartMark;
    }

    public void setThirdPartMark(String thirdPartMark) {
        this.thirdPartMark = thirdPartMark;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
