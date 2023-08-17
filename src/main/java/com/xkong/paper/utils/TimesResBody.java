package com.xkong.paper.utils;

public class TimesResBody extends ResBody{
    Integer times;
    public TimesResBody(Boolean success, String message, Integer times) {
        super(success, message);
        this.times = times;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}
