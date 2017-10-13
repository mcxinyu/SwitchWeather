package io.github.mcxinyu.switchweather.model;

/**
 * Created by 跃峰 on 2016/8/23.
 * 不需要符合 GSON 格式
 */
@Deprecated
public class WeatherInfo {
    private String status;

    private String basicCity;
    private String basicCityId;
    private String basicUpdateLoc;
    private String basicUpdateUtc;

    private String nowCondCode;
    private String nowCondTxt;
    private String nowFl;
    private String nowHum;
    private String nowPcpn;
    private String nowPres;
    private String nowTmp;
    private String nowVis;
    private String nowWindDeg;
    private String nowWindDir;
    private String nowWindSc;
    private String nowWindSpd;

    // 在7天中提取的第一天存储为当天的整天预报
    private String dailyForecastDate;
    private String dailyForecastUv;
    private String dailyForecastCondCodeD;
    private String dailyForecastCondCodeN;
    private String dailyForecastCondTxtD;
    private String dailyForecastCondTxtN;
    private String dailyForecastTmpMax;
    private String dailyForecastTmpMin;
    private String dailyForecastAstroSr;
    private String dailyForecastAstroSs;

    private String aqi;
    private String aqiCo;
    private String aqiNo2;
    private String aqiO3;
    private String aqiPm10;
    private String aqiPm25;
    private String aqiQlty;
    private String aqiSo2;

    private String suggestionAirBrf;
    private String suggestionAirTxt;
    private String suggestionComfBrf;
    private String suggestionComfTxt;
    private String suggestionCwBrf;
    private String suggestionCwTxt;
    private String suggestionDrsgBrf;
    private String suggestionDrsgTxt;
    private String suggestionFluBrf;
    private String suggestionFluTxt;
    private String suggestionSportBrf;
    private String suggestionSportTxt;
    private String suggestionTravBrf;
    private String suggestionTravTxt;
    private String suggestionUvBrf;
    private String suggestionUvTxt;

    public WeatherInfo() {
    }

    public WeatherInfo(String status,
                       String basicCity, String basicCityId, String basicUpdateLoc, String basicUpdateUtc,
                       String nowCondCode, String nowCondTxt,
                       String nowFl, String nowHum, String nowPcpn, String nowPres, String nowTmp, String nowVis,
                       String nowWindDeg, String nowWindDir, String nowWindSc, String nowWindSpd,
                       String dailyForecastDate, String dailyForecastUv,
                       String dailyForecastCondCodeD, String dailyForecastCondCodeN,
                       String dailyForecastCondTxtD, String dailyForecastCondTxtN,
                       String dailyForecastTmpMax, String dailyForecastTmpMin,
                       String dailyForecastAstroSr, String dailyForecastAstroSs,
                       String aqi, String aqiCo, String aqiNo2, String aqiO3,
                       String aqiPm10, String aqiPm25, String aqiQlty, String aqiSo2,
                       String suggestionAirBrf, String suggestionAirTxt,
                       String suggestionComfBrf, String suggestionComfTxt,
                       String suggestionCwBrf, String suggestionCwTxt,
                       String suggestionDrsgBrf, String suggestionDrsgTxt,
                       String suggestionFluBrf, String suggestionFluTxt,
                       String suggestionSportBrf, String suggestionSportTxt,
                       String suggestionTravBrf, String suggestionTravTxt,
                       String suggestionUvBrf, String suggestionUvTxt) {
        this.status = status;

        this.basicCity = basicCity;
        this.basicCityId = basicCityId;
        this.basicUpdateLoc = basicUpdateLoc;
        this.basicUpdateUtc = basicUpdateUtc;
        this.nowCondCode = nowCondCode;
        this.nowCondTxt = nowCondTxt;
        this.nowFl = nowFl;
        this.nowHum = nowHum;
        this.nowPcpn = nowPcpn;
        this.nowPres = nowPres;
        this.nowTmp = nowTmp;
        this.nowVis = nowVis;
        this.nowWindDeg = nowWindDeg;
        this.nowWindDir = nowWindDir;
        this.nowWindSc = nowWindSc;
        this.nowWindSpd = nowWindSpd;
        this.dailyForecastDate = dailyForecastDate;
        this.dailyForecastUv = dailyForecastUv;
        this.dailyForecastCondCodeD = dailyForecastCondCodeD;
        this.dailyForecastCondCodeN = dailyForecastCondCodeN;
        this.dailyForecastCondTxtD = dailyForecastCondTxtD;
        this.dailyForecastCondTxtN = dailyForecastCondTxtN;
        this.dailyForecastTmpMax = dailyForecastTmpMax;
        this.dailyForecastTmpMin = dailyForecastTmpMin;
        this.dailyForecastAstroSr = dailyForecastAstroSr;
        this.dailyForecastAstroSs = dailyForecastAstroSs;
        this.aqi = aqi;
        this.aqiCo = aqiCo;
        this.aqiNo2 = aqiNo2;
        this.aqiO3 = aqiO3;
        this.aqiPm10 = aqiPm10;
        this.aqiPm25 = aqiPm25;
        this.aqiQlty = aqiQlty;
        this.aqiSo2 = aqiSo2;
        this.suggestionAirBrf = suggestionAirBrf;
        this.suggestionAirTxt = suggestionAirTxt;
        this.suggestionComfBrf = suggestionComfBrf;
        this.suggestionComfTxt = suggestionComfTxt;
        this.suggestionCwBrf = suggestionCwBrf;
        this.suggestionCwTxt = suggestionCwTxt;
        this.suggestionDrsgBrf = suggestionDrsgBrf;
        this.suggestionDrsgTxt = suggestionDrsgTxt;
        this.suggestionFluBrf = suggestionFluBrf;
        this.suggestionFluTxt = suggestionFluTxt;
        this.suggestionSportBrf = suggestionSportBrf;
        this.suggestionSportTxt = suggestionSportTxt;
        this.suggestionTravBrf = suggestionTravBrf;
        this.suggestionTravTxt = suggestionTravTxt;
        this.suggestionUvBrf = suggestionUvBrf;
        this.suggestionUvTxt = suggestionUvTxt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBasicCity() {
        return basicCity;
    }

    public void setBasicCity(String basicCity) {
        this.basicCity = basicCity;
    }

    public String getBasicCityId() {
        return basicCityId;
    }

    public void setBasicCityId(String basicCityId) {
        this.basicCityId = basicCityId;
    }

    public String getBasicUpdateLoc() {
        return basicUpdateLoc;
    }

    public void setBasicUpdateLoc(String basicUpdateLoc) {
        this.basicUpdateLoc = basicUpdateLoc;
    }

    public String getBasicUpdateUtc() {
        return basicUpdateUtc;
    }

    public void setBasicUpdateUtc(String basicUpdateUtc) {
        this.basicUpdateUtc = basicUpdateUtc;
    }

    public String getNowCondCode() {
        return nowCondCode;
    }

    public void setNowCondCode(String nowCondCode) {
        this.nowCondCode = nowCondCode;
    }

    public String getNowCondTxt() {
        return nowCondTxt;
    }

    public void setNowCondTxt(String nowCondTxt) {
        this.nowCondTxt = nowCondTxt;
    }

    public String getNowFl() {
        return nowFl;
    }

    public void setNowFl(String nowFl) {
        this.nowFl = nowFl;
    }

    public String getNowHum() {
        return nowHum;
    }

    public void setNowHum(String nowHum) {
        this.nowHum = nowHum;
    }

    public String getNowPcpn() {
        return nowPcpn;
    }

    public void setNowPcpn(String nowPcpn) {
        this.nowPcpn = nowPcpn;
    }

    public String getNowPres() {
        return nowPres;
    }

    public void setNowPres(String nowPres) {
        this.nowPres = nowPres;
    }

    public String getNowTmp() {
        return nowTmp;
    }

    public void setNowTmp(String nowTmp) {
        this.nowTmp = nowTmp;
    }

    public String getNowVis() {
        return nowVis;
    }

    public void setNowVis(String nowVis) {
        this.nowVis = nowVis;
    }

    public String getNowWindDeg() {
        return nowWindDeg;
    }

    public void setNowWindDeg(String nowWindDeg) {
        this.nowWindDeg = nowWindDeg;
    }

    public String getNowWindDir() {
        return nowWindDir;
    }

    public void setNowWindDir(String nowWindDir) {
        this.nowWindDir = nowWindDir;
    }

    public String getNowWindSc() {
        return nowWindSc;
    }

    public void setNowWindSc(String nowWindSc) {
        this.nowWindSc = nowWindSc;
    }

    public String getNowWindSpd() {
        return nowWindSpd;
    }

    public void setNowWindSpd(String nowWindSpd) {
        this.nowWindSpd = nowWindSpd;
    }

    public String getDailyForecastDate() {
        return dailyForecastDate;
    }

    public void setDailyForecastDate(String dailyForecastDate) {
        this.dailyForecastDate = dailyForecastDate;
    }

    public String getDailyForecastUv() {
        return dailyForecastUv;
    }

    public void setDailyForecastUv(String dailyForecastUv) {
        this.dailyForecastUv = dailyForecastUv;
    }

    public String getDailyForecastCondCodeD() {
        return dailyForecastCondCodeD;
    }

    public void setDailyForecastCondCodeD(String dailyForecastCondCodeD) {
        this.dailyForecastCondCodeD = dailyForecastCondCodeD;
    }

    public String getDailyForecastCondCodeN() {
        return dailyForecastCondCodeN;
    }

    public void setDailyForecastCondCodeN(String dailyForecastCondCodeN) {
        this.dailyForecastCondCodeN = dailyForecastCondCodeN;
    }

    public String getDailyForecastCondTxtD() {
        return dailyForecastCondTxtD;
    }

    public void setDailyForecastCondTxtD(String dailyForecastCondTxtD) {
        this.dailyForecastCondTxtD = dailyForecastCondTxtD;
    }

    public String getDailyForecastCondTxtN() {
        return dailyForecastCondTxtN;
    }

    public void setDailyForecastCondTxtN(String dailyForecastCondTxtN) {
        this.dailyForecastCondTxtN = dailyForecastCondTxtN;
    }

    public String getDailyForecastTmpMax() {
        return dailyForecastTmpMax;
    }

    public void setDailyForecastTmpMax(String dailyForecastTmpMax) {
        this.dailyForecastTmpMax = dailyForecastTmpMax;
    }

    public String getDailyForecastTmpMin() {
        return dailyForecastTmpMin;
    }

    public void setDailyForecastTmpMin(String dailyForecastTmpMin) {
        this.dailyForecastTmpMin = dailyForecastTmpMin;
    }

    public String getDailyForecastAstroSr() {
        return dailyForecastAstroSr;
    }

    public void setDailyForecastAstroSr(String dailyForecastAstroSr) {
        this.dailyForecastAstroSr = dailyForecastAstroSr;
    }

    public String getDailyForecastAstroSs() {
        return dailyForecastAstroSs;
    }

    public void setDailyForecastAstroSs(String dailyForecastAstroSs) {
        this.dailyForecastAstroSs = dailyForecastAstroSs;
    }

    public String getAqi() {
        if (aqi == null) {
            return "N/A";
        }
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getAqiCo() {
        if (aqiCo == null) {
            return "N/A";
        }
        return aqiCo;
    }

    public void setAqiCo(String aqiCo) {
        this.aqiCo = aqiCo;
    }

    public String getAqiNo2() {
        if (aqiNo2 == null) {
            return "N/A";
        }
        return aqiNo2;
    }

    public void setAqiNo2(String aqiNo2) {
        this.aqiNo2 = aqiNo2;
    }

    public String getAqiO3() {
        if (aqiO3 == null) {
            return "N/A";
        }
        return aqiO3;
    }

    public void setAqiO3(String aqiO3) {
        this.aqiO3 = aqiO3;
    }

    public String getAqiPm10() {
        if (aqiPm10 == null) {
            return "N/A";
        }
        return aqiPm10;
    }

    public void setAqiPm10(String aqiPm10) {
        this.aqiPm10 = aqiPm10;
    }

    public String getAqiPm25() {
        if (aqiPm25 == null) {
            return "N/A";
        }
        return aqiPm25;
    }

    public void setAqiPm25(String aqiPm25) {
        this.aqiPm25 = aqiPm25;
    }

    public String getAqiQlty() {
        return aqiQlty;
    }

    public void setAqiQlty(String aqiQlty) {
        this.aqiQlty = aqiQlty;
    }

    public String getAqiSo2() {
        if (aqiSo2 == null) {
            return "N/A";
        }
        return aqiSo2;
    }

    public void setAqiSo2(String aqiSo2) {
        this.aqiSo2 = aqiSo2;
    }

    public String getSuggestionAirBrf() {
        return suggestionAirBrf;
    }

    public void setSuggestionAirBrf(String suggestionAirBrf) {
        this.suggestionAirBrf = suggestionAirBrf;
    }

    public String getSuggestionAirTxt() {
        return suggestionAirTxt;
    }

    public void setSuggestionAirTxt(String suggestionAirTxt) {
        this.suggestionAirTxt = suggestionAirTxt;
    }

    public String getSuggestionComfBrf() {
        return suggestionComfBrf;
    }

    public void setSuggestionComfBrf(String suggestionComfBrf) {
        this.suggestionComfBrf = suggestionComfBrf;
    }

    public String getSuggestionComfTxt() {
        return suggestionComfTxt;
    }

    public void setSuggestionComfTxt(String suggestionComfTxt) {
        this.suggestionComfTxt = suggestionComfTxt;
    }

    public String getSuggestionCwBrf() {
        return suggestionCwBrf;
    }

    public void setSuggestionCwBrf(String suggestionCwBrf) {
        this.suggestionCwBrf = suggestionCwBrf;
    }

    public String getSuggestionCwTxt() {
        return suggestionCwTxt;
    }

    public void setSuggestionCwTxt(String suggestionCwTxt) {
        this.suggestionCwTxt = suggestionCwTxt;
    }

    public String getSuggestionDrsgBrf() {
        return suggestionDrsgBrf;
    }

    public void setSuggestionDrsgBrf(String suggestionDrsgBrf) {
        this.suggestionDrsgBrf = suggestionDrsgBrf;
    }

    public String getSuggestionDrsgTxt() {
        return suggestionDrsgTxt;
    }

    public void setSuggestionDrsgTxt(String suggestionDrsgTxt) {
        this.suggestionDrsgTxt = suggestionDrsgTxt;
    }

    public String getSuggestionFluBrf() {
        return suggestionFluBrf;
    }

    public void setSuggestionFluBrf(String suggestionFluBrf) {
        this.suggestionFluBrf = suggestionFluBrf;
    }

    public String getSuggestionFluTxt() {
        return suggestionFluTxt;
    }

    public void setSuggestionFluTxt(String suggestionFluTxt) {
        this.suggestionFluTxt = suggestionFluTxt;
    }

    public String getSuggestionSportBrf() {
        return suggestionSportBrf;
    }

    public void setSuggestionSportBrf(String suggestionSportBrf) {
        this.suggestionSportBrf = suggestionSportBrf;
    }

    public String getSuggestionSportTxt() {
        return suggestionSportTxt;
    }

    public void setSuggestionSportTxt(String suggestionSportTxt) {
        this.suggestionSportTxt = suggestionSportTxt;
    }

    public String getSuggestionTravBrf() {
        return suggestionTravBrf;
    }

    public void setSuggestionTravBrf(String suggestionTravBrf) {
        this.suggestionTravBrf = suggestionTravBrf;
    }

    public String getSuggestionTravTxt() {
        return suggestionTravTxt;
    }

    public void setSuggestionTravTxt(String suggestionTravTxt) {
        this.suggestionTravTxt = suggestionTravTxt;
    }

    public String getSuggestionUvBrf() {
        return suggestionUvBrf;
    }

    public void setSuggestionUvBrf(String suggestionUvBrf) {
        this.suggestionUvBrf = suggestionUvBrf;
    }

    public String getSuggestionUvTxt() {
        return suggestionUvTxt;
    }

    public void setSuggestionUvTxt(String suggestionUvTxt) {
        this.suggestionUvTxt = suggestionUvTxt;
    }
}
