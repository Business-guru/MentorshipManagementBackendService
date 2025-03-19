package com.BusinessGuru.MentorshipManagementBackend.commons;

public class Meta {
    private String msg;
    private Boolean success;

    public Meta(String msg, Boolean success) {
        this.msg = msg;
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
