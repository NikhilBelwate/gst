package com.blocpal.mbnk.gst.adapters.response;

public abstract class CommonResponse {
    protected Integer response_code;
    protected Boolean status;

    public Integer getResponse_code() {
        return response_code;
    }

    public void setResponse_code(Integer response_code) {
        this.response_code = response_code;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
