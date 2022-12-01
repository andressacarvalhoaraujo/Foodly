package com.uniritter.cdm.cookingrecipeapplication.helper;

public class RequestHelper {
    public boolean isSuccess;
    public RequestType type;
    public String errorMessage;

    public RequestHelper(boolean isSuccess, RequestType type) {
        this.isSuccess = isSuccess;
        this.type = type;
    }

    public RequestHelper(boolean isSuccess, RequestType type, String errorMessage) {
        this.isSuccess = isSuccess;
        this.type = type;
        this.errorMessage = errorMessage;
    }
}


