package com.BusinessGuru.MentorshipManagementBackend.commons;


public class ApiResponse<T> {
    private Meta meta;
    private T data;

    public ApiResponse(Meta meta, T data) {
        this.meta = meta;
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public T getData() {
        return data;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public void setData(T data) {
        this.data = data;
    }
}
