package com.calabrio.dto.message;

import com.google.gson.annotations.Expose;

public class SuccessMessage {
    @Expose
    private String success;

    public SuccessMessage(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
