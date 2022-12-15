package com.devopsusach2020.model;

public class RespuestaAPICovid {
    private int statusCode;

    public RespuestaAPICovid() {
        this.statusCode = 0;
    }

    public int getStatusCode(){
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
