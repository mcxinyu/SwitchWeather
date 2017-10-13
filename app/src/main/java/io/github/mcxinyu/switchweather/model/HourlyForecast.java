package io.github.mcxinyu.switchweather.model;

/**
 * Created by 跃峰 on 2016/8/24.
 * 不需要符合 GSON 格式
 */
@Deprecated
public class HourlyForecast {
    private String date;
    private String cityId;
    private String city;
    private String condCode;
    private String condTxt;
    private String hum;
    private String pop;
    private String pres;
    private String tmp;
    private String windDeg;
    private String windDir;
    private String windSc;
    private String windSpd;

    public HourlyForecast() {
    }

    public HourlyForecast(String date,
                          String cityId, String city,
                          String condCode, String condTxt,
                          String hum, String pop, String pres, String tmp,
                          String windDeg, String windDir, String windSc, String windSpd) {
        this.date = date;
        this.cityId = cityId;
        this.city = city;
        this.condCode = condCode;
        this.condTxt = condTxt;
        this.hum = hum;
        this.pop = pop;
        this.pres = pres;
        this.tmp = tmp;
        this.windDeg = windDeg;
        this.windDir = windDir;
        this.windSc = windSc;
        this.windSpd = windSpd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCondCode() {
        return condCode;
    }

    public void setCondCode(String condCode) {
        this.condCode = condCode;
    }

    public String getCondTxt() {
        return condTxt;
    }

    public void setCondTxt(String condTxt) {
        this.condTxt = condTxt;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(String windDeg) {
        this.windDeg = windDeg;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getWindSc() {
        return windSc;
    }

    public void setWindSc(String windSc) {
        this.windSc = windSc;
    }

    public String getWindSpd() {
        return windSpd;
    }

    public void setWindSpd(String windSpd) {
        this.windSpd = windSpd;
    }
}
