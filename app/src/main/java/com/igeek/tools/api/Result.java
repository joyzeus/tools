package com.igeek.tools.api;

import java.io.Serializable;

public class Result implements Serializable {

    private int ret;

    private String msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "ret=" + ret +
                ", msg='" + msg + '\'' +
                '}';
    }
}
