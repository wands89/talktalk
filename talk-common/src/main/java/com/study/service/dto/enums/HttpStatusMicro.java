package com.study.service.dto.enums;

public enum HttpStatusMicro {
    /**
     * 数据库操作错误
     */
    SQL_SELECT_ERROR(10000, "服务器异常，查询数据失败！");

    private int code;
    private String message;

    HttpStatusMicro(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
