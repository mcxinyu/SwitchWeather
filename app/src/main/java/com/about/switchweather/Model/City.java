package com.about.switchweather.Model;

/**
 * Created by 跃峰 on 2016/8/24.
 */
public class City {
    /**
     * city : 平谷
     * cnty : 中国
     * id : CN101011500
     * lat : 40.204000
     * lon : 117.150000
     * prov : 直辖市
     */

    private String city;
    private String cnty;
    private String id;
    private String lat;
    private String lon;
    private String prov;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }
}
