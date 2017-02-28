package com.about.switchweather.Model;


import com.about.switchweather.Util.SortedListAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 跃峰 on 2016/8/24.
 * GSON 格式
 */
public class City implements SortedListAdapter.ViewModel{
    /**
     * id : CN101010100
     * cityEn : beijing
     * cityZh : 北京
     * countryCode : CN
     * countryEn : China
     * countryZh : 中国
     * provinceEn : beijing
     * provinceZh : 北京
     * leaderEn : beijing
     * leaderZh : 北京
     * lat : 39.904
     * lon : 116.391
     */

    @SerializedName("id")
    private String id;
    @SerializedName("cityEn")
    private String cityEn;
    @SerializedName("cityZh")
    private String cityZh;
    @SerializedName("countryCode")
    private String countryCode;
    @SerializedName("countryEn")
    private String countryEn;
    @SerializedName("countryZh")
    private String countryZh;
    @SerializedName("provinceEn")
    private String provinceEn;
    @SerializedName("provinceZh")
    private String provinceZh;
    @SerializedName("leaderEn")
    private String leaderEn;
    @SerializedName("leaderZh")
    private String leaderZh;
    @SerializedName("lat")
    private String lat;
    @SerializedName("lon")
    private String lon;

    public String getId() {
        return id;
    }

    public City() {
    }

    public City(String id, String cityEn, String cityZh,
                String countryCode, String countryEn, String countryZh,
                String provinceEn, String provinceZh,
                String leaderEn, String leaderZh,
                String lat, String lon) {
        this.id = id;
        this.cityEn = cityEn;
        this.cityZh = cityZh;
        this.countryCode = countryCode;
        this.countryEn = countryEn;
        this.countryZh = countryZh;
        this.provinceEn = provinceEn;
        this.provinceZh = provinceZh;
        this.leaderEn = leaderEn;
        this.leaderZh = leaderZh;
        this.lat = lat;
        this.lon = lon;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getCityZh() {
        return cityZh;
    }

    public void setCityZh(String cityZh) {
        this.cityZh = cityZh;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public String getCountryZh() {
        return countryZh;
    }

    public void setCountryZh(String countryZh) {
        this.countryZh = countryZh;
    }

    public String getProvinceEn() {
        return provinceEn;
    }

    public void setProvinceEn(String provinceEn) {
        this.provinceEn = provinceEn;
    }

    public String getProvinceZh() {
        return provinceZh;
    }

    public void setProvinceZh(String provinceZh) {
        this.provinceZh = provinceZh;
    }

    public String getLeaderEn() {
        return leaderEn;
    }

    public void setLeaderEn(String leaderEn) {
        this.leaderEn = leaderEn;
    }

    public String getLeaderZh() {
        return leaderZh;
    }

    public void setLeaderZh(String leaderZh) {
        this.leaderZh = leaderZh;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (id != city.id) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
