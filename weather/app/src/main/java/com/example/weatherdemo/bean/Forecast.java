package com.example.weatherdemo.bean;

public class Forecast {
    private String api;
    private int pm10;
    private String  date;
    private String  fl;
    private String  fx;
    private String  high;
    private String  low;
    private String  notice;
    private String  sunrise;
    private String  sunset;
    private String  type;
    private String  week;
    private String  ymd;

    private String shidu;

    private int pm25;
    Forecast(){

    }
    Forecast(int pm10,int pm25,String date,String fl,String fx,String high,String low,String notice,String sunrise,String sunset,String type,String week,String ymd){

    }
    public String getApi() {
        return api;
    }

    public void setApi(String aqi) {
        this.api = api;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }
    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public int getPm25() {
        return pm25;
    }

    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }
    public int getPm10() {
        return pm25;
    }

    public void setPm10(int pm10) {
        this.pm10= pm10;
    }
}
