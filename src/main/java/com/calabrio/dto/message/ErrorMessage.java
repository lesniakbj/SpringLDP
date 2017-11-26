package com.calabrio.dto.message;

import com.google.gson.annotations.Expose;

public class ErrorMessage {
    @Expose
    private String error;

    public ErrorMessage(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
