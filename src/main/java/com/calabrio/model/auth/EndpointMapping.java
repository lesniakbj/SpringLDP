package com.calabrio.model.auth;

import com.google.gson.annotations.Expose;

public class EndpointMapping {
    @Expose
    private String pathPattern;

    @Expose
    private String method;

    public String getPathPattern() {
        return pathPattern;
    }

    public String getMethod() {
        return method;
    }

    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
