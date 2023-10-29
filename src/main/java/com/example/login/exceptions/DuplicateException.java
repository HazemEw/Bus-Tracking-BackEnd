package com.example.login.exceptions;

public class DuplicateException extends RuntimeException{
    private String resourceName;

    public DuplicateException(String resourceName) {
        super(String.format("Name : %s  already used ", resourceName));
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
