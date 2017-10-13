package io.github.mcxinyu.switchweather.database.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by huangyuefeng on 2017/8/31.
 * Contact me : mcxinyu@foxmail.com
 */
@Entity
public class CityBean {
    @Id
    @NotNull
    private String cityId;
    private String cityName;
    private String provName;
    private String longitude;   // 经度
    private String latitude;    // 纬度
    private boolean isLocation;

    @Generated(hash = 1357946122)
    public CityBean(@NotNull String cityId, String cityName, String provName,
                    String longitude, String latitude, boolean isLocation) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.provName = provName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isLocation = isLocation;
    }

    @Generated(hash = 273649691)
    public CityBean() {
    }

    public String getCityId() {
        return this.cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvName() {
        return this.provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public boolean getIsLocation() {
        return this.isLocation;
    }

    public void setIsLocation(boolean isLocation) {
        this.isLocation = isLocation;
    }
}
