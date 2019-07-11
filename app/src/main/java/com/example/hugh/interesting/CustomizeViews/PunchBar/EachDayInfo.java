package com.example.hugh.interesting.CustomizeViews.PunchBar;

/**
 * Created by Hugh on 2019/7/11.
 */
public class EachDayInfo {

    private String date;
    private Boolean isPunched;
    private Boolean isToday;

    public EachDayInfo(String date, Boolean isPunched, Boolean isToday) {
        this.date = date;
        this.isPunched = isPunched;
        this.isToday = isToday;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getPunched() {
        return isPunched;
    }

    public void setPunched(Boolean punched) {
        isPunched = punched;
    }

    public Boolean getToaday() {
        return isToday;
    }

    public void setToaday(Boolean toaday) {
        isToday = toaday;
    }
}
