package com.about.switchweather.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 跃峰 on 2016/8/21.
 * GSON 格式
 */
public class Condition {
    @SerializedName("code")
    private String code;
    @SerializedName("txt")
    private String txt;
    @SerializedName("txt_en")
    private String txtEn;
    @SerializedName("icon")
    private String icon;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getTxtEn() {
        return txtEn;
    }

    public void setTxtEn(String txtEn) {
        this.txtEn = txtEn;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
