package com.about.switchweather.DataBase;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 跃峰 on 2016/8/21.
 * 数据库方案架构
 */
public class WeatherDbSchema {
    public static final class ConditionTable{
        public static final String NAME = "Condition";

        public static final class Columns{
            public static final String CODE = "code";
            public static final String TEXT = "txt";
            public static final String TEXT_EN = "txt_en";
            public static final String ICON = "icon";
        }
    }

    public static final class WeatherInfoTable {
        public static final String NAME = "WeatherInfo";

        public static final class Columns{
            public static final String STATUS = "status";

            public static final class Aqi {
                public static final class City {
                    @SerializedName("aqi")
                    private String AQI;
                    @SerializedName("co")
                    private String CO;
                    @SerializedName("no2")
                    private String NO2;
                    @SerializedName("o3")
                    private String O3;
                    @SerializedName("pm10")
                    private String PM10;
                    @SerializedName("pm25")
                    private String PM25;
                    @SerializedName("qlty")
                    private String QLTY;
                    @SerializedName("so2")
                    private String SO2;
                }
            }

            public static final class Basic {
                public static final String CITY = "basic_city";
                @SerializedName("cnty")
                private String CNTY;
                public static final String ID = "city_id";
                @SerializedName("lat")
                private String LAT;
                @SerializedName("lon")
                private String LON;

                public static final class Update {
                    public static final String LOC = "basic_update_loc";
                    @SerializedName("utc")
                    private String UTC;
                }
            }

            public static final class Now {
                @SerializedName("fl")
                private String FL;
                @SerializedName("hum")
                private String HUM;
                @SerializedName("pcpn")
                private String PCPN;
                @SerializedName("pres")
                private String PRES;
                public static final String TMP = "now_tmp";
                @SerializedName("vis")
                private String VIS;

                public static final class Cond {
                    public static final String CODE = "now_cond_code";
                    public static final String TXT = "now_cond_txt";

                }

                public static final class Wind {
                    @SerializedName("deg")
                    private String DEG;
                    @SerializedName("dir")
                    private String DIR;
                    @SerializedName("sc")
                    private String SC;
                    @SerializedName("spd")
                    private String SPD;
                }
            }

            public static final class Suggestion {
                /**
                 * brf : 较不舒适
                 * txt : 白天虽然有雨，但仍无法削弱较高气温带来的暑意，同时降雨造成湿度加大会您感到有些闷热，不很舒适。
                 */

                private Comf comf;
                @SerializedName("cw")
                private Comf CW;
                @SerializedName("drsg")
                private Comf DRSG;
                @SerializedName("flu")
                private Comf FLU;
                @SerializedName("sport")
                private Comf SPORT;
                @SerializedName("trav")
                private Comf TRAV;
                @SerializedName("uv")
                private Comf UV;

                public Comf getComf() {
                    return comf;
                }

                public void setComf(Comf comf) {
                    this.comf = comf;
                }

                public Comf getCW() {
                    return CW;
                }

                public void setCW(Comf CW) {
                    this.CW = CW;
                }

                public Comf getDRSG() {
                    return DRSG;
                }

                public void setDRSG(Comf DRSG) {
                    this.DRSG = DRSG;
                }

                public Comf getFLU() {
                    return FLU;
                }

                public void setFLU(Comf FLU) {
                    this.FLU = FLU;
                }

                public Comf getSPORT() {
                    return SPORT;
                }

                public void setSPORT(Comf SPORT) {
                    this.SPORT = SPORT;
                }

                public Comf getTRAV() {
                    return TRAV;
                }

                public void setTRAV(Comf TRAV) {
                    this.TRAV = TRAV;
                }

                public Comf getUV() {
                    return UV;
                }

                public void setUV(Comf UV) {
                    this.UV = UV;
                }

                public static final class Comf {
                    @SerializedName("brf")
                    private String BRF;
                    @SerializedName("txt")
                    private String TXT;
                }
            }

            public static final class DailyForecast {
                public static final String DATE = "df_date";
                @SerializedName("hum")
                private String HUM;
                @SerializedName("pcpn")
                private String PCPN;
                @SerializedName("pop")
                private String POP;
                @SerializedName("pres")
                private String PRES;
                @SerializedName("vis")
                private String VIS;

                public static final class Astro {
                    @SerializedName("sr")
                    private String SR;
                    @SerializedName("ss")
                    private String SS;
                }

                public static final class Cond {
                    public static final String CODE_D = "df_cond_code_d";
                    public static final String CODE_N = "df_cond_code_n";
                    public static final String TXT_D = "df_cond_txt_d";
                    public static final String TXT_N = "df_cond_txt_n";
                }

                public static final class Tmp {
                    public static final String MAX = "df_tmp_max";
                    public static final String MIN = "df_tmp_min";
                }

                public static final class Wind {
                    private String DEG;
                    private String DIR;
                    private String SC;
                    private String SPD;
                }
            }

            public static final class HourlyForecast {
                private String DATE;
                private String HUM;
                private String POP;
                private String PRES;
                private String TMP;

                public static final class Wind {
                    private String DEG;
                    private String DIR;
                    private String SC;
                    private String SPD;
                }
            }
        }
    }

    public static final class CityTable {
        public static final String NAME = "City";

        public static final class Columns{
            public static final String ID = "id";
            public static final String CITY = "city";
            public static final String CNTY = "cnty";
            public static final String LAT = "lat";
            public static final String LON = "lon";
            public static final String PROV = "prov";
        }
    }
}
