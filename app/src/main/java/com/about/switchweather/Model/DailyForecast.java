package com.about.switchweather.model;

/**
 * Created by 跃峰 on 2016/8/24.
 * 不需要符合 GSON 格式
 */
public class DailyForecast {
    private String date;

    private String cityId;
    private String city;

    private String astroSr;
    private String astroSs;
    private String astroMr;
    private String astroMs;

    private String condCodeD;
    private String condCodeN;
    private String condTxtD;
    private String condTxtN;

    private String hum;
    private String pcpn;
    private String pop;
    private String pres;

    private String tmpMax;
    private String tmpMin;

    private String uv;
    private String vis;

    private String windDeg;
    private String windDir;
    private String windSc;
    private String windSpd;

    public DailyForecast() {
    }

    public DailyForecast(String date,
                         String cityId, String city,
                         String astroSr, String astroSs, String astroMr, String astroMs,
                         String condCodeD, String condCodeN, String condTxtD, String condTxtN,
                         String hum, String pcpn, String pop, String pres,
                         String tmpMax, String tmpMin,
                         String uv, String vis,
                         String windDeg, String windDir, String windSc, String windSpd) {
        this.date = date;
        this.cityId = cityId;
        this.city = city;
        this.astroSr = astroSr;
        this.astroSs = astroSs;
        this.astroMr = astroMr;
        this.astroMs = astroMs;
        this.condCodeD = condCodeD;
        this.condCodeN = condCodeN;
        this.condTxtD = condTxtD;
        this.condTxtN = condTxtN;
        this.hum = hum;
        this.pcpn = pcpn;
        this.pop = pop;
        this.pres = pres;
        this.tmpMax = tmpMax;
        this.tmpMin = tmpMin;
        this.uv = uv;
        this.vis = vis;
        this.windDeg = windDeg;
        this.windDir = windDir;
        this.windSc = windSc;
        this.windSpd = windSpd;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAstroSr() {
        return astroSr;
    }

    public void setAstroSr(String astroSr) {
        this.astroSr = astroSr;
    }

    public String getAstroSs() {
        return astroSs;
    }

    public void setAstroSs(String astroSs) {
        this.astroSs = astroSs;
    }

    public String getAstroMr() {
        return astroMr;
    }

    public void setAstroMr(String astroMr) {
        this.astroMr = astroMr;
    }

    public String getAstroMs() {
        return astroMs;
    }

    public void setAstroMs(String astroMs) {
        this.astroMs = astroMs;
    }

    public String getCondCodeD() {
        return condCodeD;
    }

    public void setCondCodeD(String condCodeD) {
        this.condCodeD = condCodeD;
    }

    public String getCondCodeN() {
        return condCodeN;
    }

    public void setCondCodeN(String condCodeN) {
        this.condCodeN = condCodeN;
    }

    public String getCondTxtD() {
        return condTxtD;
    }

    public void setCondTxtD(String condTxtD) {
        this.condTxtD = condTxtD;
    }

    public String getCondTxtN() {
        return condTxtN;
    }

    public void setCondTxtN(String condTxtN) {
        this.condTxtN = condTxtN;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
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

    public String getTmpMax() {
        return tmpMax;
    }

    public void setTmpMax(String tmpMax) {
        this.tmpMax = tmpMax;
    }

    public String getTmpMin() {
        return tmpMin;
    }

    public void setTmpMin(String tmpMin) {
        this.tmpMin = tmpMin;
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv) {
        this.uv = uv;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
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
