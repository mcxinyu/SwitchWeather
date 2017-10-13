package io.github.mcxinyu.switchweather.model;

/**
 * Created by 跃峰 on 2016/8/21.
 * GSON 格式
 */
@Deprecated
public class Condition {
    private String code;
    private String txt;
    private String txtEn;
    private String icon;

    public Condition() {
    }

    public Condition(String code, String txt, String txtEn, String icon) {
        this.code = code;
        this.txt = txt;
        this.txtEn = txtEn;
        this.icon = icon;
    }

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
