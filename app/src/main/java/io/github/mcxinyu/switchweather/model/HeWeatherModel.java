package io.github.mcxinyu.switchweather.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 跃峰 on 2016/8/20.
 * GSON 格式
 */
public class HeWeatherModel implements Parcelable {
    @SerializedName("HeWeather5")
    private List<HeWeather5Bean> HeWeather5;

    public List<HeWeather5Bean> getHeWeather5() {
        return HeWeather5;
    }

    public void setHeWeather5(List<HeWeather5Bean> HeWeather5) {
        this.HeWeather5 = HeWeather5;
    }

    public static class HeWeather5Bean implements Parcelable {
        /**
         * aqi : {"city":{"aqi":"60","co":"1","no2":"54","o3":"77","pm10":"62","pm25":"39","qlty":"良","so2":"9"}}
         * basic : {"city":"深圳","cnty":"中国","id":"CN101280601","lat":"22.544000","lon":"114.109000","update":{"loc":"2017-02-28 08:51","utc":"2017-02-28 00:51"}}
         * daily_forecast : [{"astro":{"mr":"07:50","ms":"20:08","sr":"06:45","ss":"18:26"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2017-02-28","hum":"59","pcpn":"0.0","pop":"0","pres":"1021","tmp":{"max":"20","min":"15"},"uv":"10","vis":"10","wind":{"deg":"105","dir":"无持续风向","sc":"微风","spd":"0"}},{"astro":{"mr":"08:33","ms":"21:07","sr":"06:44","ss":"18:27"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2017-03-01","hum":"59","pcpn":"0.0","pop":"0","pres":"1020","tmp":{"max":"21","min":"14"},"uv":"10","vis":"10","wind":{"deg":"25","dir":"无持续风向","sc":"微风","spd":"4"}},{"astro":{"mr":"09:17","ms":"22:08","sr":"06:43","ss":"18:27"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2017-03-02","hum":"41","pcpn":"0.0","pop":"0","pres":"1021","tmp":{"max":"20","min":"13"},"uv":"10","vis":"10","wind":{"deg":"38","dir":"无持续风向","sc":"微风","spd":"9"}},{"astro":{"mr":"10:03","ms":"23:09","sr":"06:42","ss":"18:28"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2017-03-03","hum":"65","pcpn":"0.0","pop":"0","pres":"1017","tmp":{"max":"21","min":"15"},"uv":"-999","vis":"10","wind":{"deg":"121","dir":"无持续风向","sc":"微风","spd":"5"}},{"astro":{"mr":"10:50","ms":"null","sr":"06:42","ss":"18:28"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2017-03-04","hum":"77","pcpn":"0.0","pop":"0","pres":"1015","tmp":{"max":"23","min":"16"},"uv":"-999","vis":"10","wind":{"deg":"129","dir":"无持续风向","sc":"微风","spd":"3"}},{"astro":{"mr":"11:42","ms":"00:10","sr":"06:41","ss":"18:28"},"cond":{"code_d":"101","code_n":"305","txt_d":"多云","txt_n":"小雨"},"date":"2017-03-05","hum":"80","pcpn":"0.0","pop":"13","pres":"1014","tmp":{"max":"24","min":"16"},"uv":"-999","vis":"9","wind":{"deg":"150","dir":"无持续风向","sc":"微风","spd":"2"}},{"astro":{"mr":"12:35","ms":"01:11","sr":"06:40","ss":"18:29"},"cond":{"code_d":"305","code_n":"305","txt_d":"小雨","txt_n":"小雨"},"date":"2017-03-06","hum":"76","pcpn":"0.0","pop":"17","pres":"1015","tmp":{"max":"23","min":"18"},"uv":"-999","vis":"10","wind":{"deg":"143","dir":"无持续风向","sc":"微风","spd":"8"}}]
         * hourly_forecast : [{"cond":{"code":"100","txt":"晴"},"date":"2017-02-28 10:00","hum":"51","pop":"0","pres":"1023","tmp":"21","wind":{"deg":"70","dir":"东北风","sc":"微风","spd":"8"}},{"cond":{"code":"100","txt":"晴"},"date":"2017-02-28 13:00","hum":"43","pop":"0","pres":"1021","tmp":"25","wind":{"deg":"93","dir":"东风","sc":"微风","spd":"6"}},{"cond":{"code":"100","txt":"晴"},"date":"2017-02-28 16:00","hum":"44","pop":"0","pres":"1018","tmp":"26","wind":{"deg":"132","dir":"东南风","sc":"微风","spd":"8"}},{"cond":{"code":"100","txt":"晴"},"date":"2017-02-28 19:00","hum":"59","pop":"0","pres":"1019","tmp":"22","wind":{"deg":"131","dir":"东南风","sc":"微风","spd":"8"}},{"cond":{"code":"100","txt":"晴"},"date":"2017-02-28 22:00","hum":"72","pop":"0","pres":"1020","tmp":"19","wind":{"deg":"115","dir":"东南风","sc":"微风","spd":"8"}}]
         * now : {"cond":{"code":"100","txt":"晴"},"fl":"15","hum":"54","pcpn":"0","pres":"1022","tmp":"15","vis":"8","wind":{"deg":"140","dir":"东北风","sc":"3-4","spd":"10"}}
         * status : ok
         * suggestion : {"air":{"brf":"中","txt":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"},"comf":{"brf":"舒适","txt":"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"},"cw":{"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},"drsg":{"brf":"较舒适","txt":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。"},"flu":{"brf":"较易发","txt":"天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。"},"sport":{"brf":"较适宜","txt":"天气较好，户外运动请注意防晒。推荐您进行室内运动。"},"trav":{"brf":"适宜","txt":"天气较好，但丝毫不会影响您出行的心情。温度适宜又有微风相伴，适宜旅游。"},"uv":{"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}}
         */

        @SerializedName("aqi")
        private AqiBean aqi;
        @SerializedName("basic")
        private BasicBean basic;
        @SerializedName("now")
        private NowBean now;
        @SerializedName("status")
        private String status;
        @SerializedName("suggestion")
        private SuggestionBean suggestion;
        @SerializedName("daily_forecast")
        private List<DailyForecastBean> dailyForecast;
        @SerializedName("hourly_forecast")
        private List<HourlyForecastBean> hourlyForecast;

        public AqiBean getAqi() {
            return aqi;
        }

        public void setAqi(AqiBean aqi) {
            this.aqi = aqi;
        }

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public SuggestionBean getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(SuggestionBean suggestion) {
            this.suggestion = suggestion;
        }

        public List<DailyForecastBean> getDailyForecast() {
            return dailyForecast;
        }

        public void setDailyForecast(List<DailyForecastBean> dailyForecast) {
            this.dailyForecast = dailyForecast;
        }

        public List<HourlyForecastBean> getHourlyForecast() {
            return hourlyForecast;
        }

        public void setHourlyForecast(List<HourlyForecastBean> hourlyForecast) {
            this.hourlyForecast = hourlyForecast;
        }

        public static class AqiBean implements Parcelable {
            /**
             * city : {"aqi":"60","co":"1","no2":"54","o3":"77","pm10":"62","pm25":"39","qlty":"良","so2":"9"}
             */

            @SerializedName("city")
            private CityBean city;

            public CityBean getCity() {
                return city;
            }

            public void setCity(CityBean city) {
                this.city = city;
            }

            public static class CityBean implements Parcelable {
                /**
                 * aqi : 60
                 * co : 1
                 * no2 : 54
                 * o3 : 77
                 * pm10 : 62
                 * pm25 : 39
                 * qlty : 良
                 * so2 : 9
                 */

                @SerializedName("aqi")
                private String aqi;
                @SerializedName("co")
                private String co;
                @SerializedName("no2")
                private String no2;
                @SerializedName("o3")
                private String o3;
                @SerializedName("pm10")
                private String pm10;
                @SerializedName("pm25")
                private String pm25;
                @SerializedName("qlty")
                private String qlty;
                @SerializedName("so2")
                private String so2;

                public String getAqi() {
                    if (aqi == null) {
                        return "N/A";
                    }
                    return aqi;
                }

                public void setAqi(String aqi) {
                    this.aqi = aqi;
                }

                public String getCo() {
                    if (co == null) {
                        return "N/A";
                    }
                    return co;
                }

                public void setCo(String co) {
                    this.co = co;
                }

                public String getNo2() {
                    if (no2 == null) {
                        return "N/A";
                    }
                    return no2;
                }

                public void setNo2(String no2) {
                    this.no2 = no2;
                }

                public String getO3() {
                    if (o3 == null) {
                        return "N/A";
                    }
                    return o3;
                }

                public void setO3(String o3) {
                    this.o3 = o3;
                }

                public String getPm10() {
                    if (pm10 == null) {
                        return "N/A";
                    }
                    return pm10;
                }

                public void setPm10(String pm10) {
                    this.pm10 = pm10;
                }

                public String getPm25() {
                    if (pm25 == null) {
                        return "N/A";
                    }
                    return pm25;
                }

                public void setPm25(String pm25) {
                    this.pm25 = pm25;
                }

                public String getQlty() {
                    if (qlty == null) {
                        return "N/A";
                    }
                    return qlty;
                }

                public void setQlty(String qlty) {
                    this.qlty = qlty;
                }

                public String getSo2() {
                    if (so2 == null) {
                        return "N/A";
                    }
                    return so2;
                }

                public void setSo2(String so2) {
                    this.so2 = so2;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.aqi);
                    dest.writeString(this.co);
                    dest.writeString(this.no2);
                    dest.writeString(this.o3);
                    dest.writeString(this.pm10);
                    dest.writeString(this.pm25);
                    dest.writeString(this.qlty);
                    dest.writeString(this.so2);
                }

                public CityBean() {
                }

                protected CityBean(Parcel in) {
                    this.aqi = in.readString();
                    this.co = in.readString();
                    this.no2 = in.readString();
                    this.o3 = in.readString();
                    this.pm10 = in.readString();
                    this.pm25 = in.readString();
                    this.qlty = in.readString();
                    this.so2 = in.readString();
                }

                public static final Parcelable.Creator<CityBean> CREATOR = new Parcelable.Creator<CityBean>() {
                    public CityBean createFromParcel(Parcel source) {
                        return new CityBean(source);
                    }

                    public CityBean[] newArray(int size) {
                        return new CityBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeParcelable(this.city, 0);
            }

            public AqiBean() {
            }

            protected AqiBean(Parcel in) {
                this.city = in.readParcelable(CityBean.class.getClassLoader());
            }

            public static final Creator<AqiBean> CREATOR = new Creator<AqiBean>() {
                public AqiBean createFromParcel(Parcel source) {
                    return new AqiBean(source);
                }

                public AqiBean[] newArray(int size) {
                    return new AqiBean[size];
                }
            };
        }

        public static class BasicBean implements Parcelable {
            /**
             * city : 深圳
             * cnty : 中国
             * id : CN101280601
             * lat : 22.544000
             * lon : 114.109000
             * update : {"loc":"2017-02-28 08:51","utc":"2017-02-28 00:51"}
             */

            @SerializedName("city")
            private String city;
            @SerializedName("cnty")
            private String cnty;
            @SerializedName("id")
            private String id;
            @SerializedName("lat")
            private String lat;
            @SerializedName("lon")
            private String lon;
            @SerializedName("update")
            private UpdateBean update;

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

            public UpdateBean getUpdate() {
                return update;
            }

            public void setUpdate(UpdateBean update) {
                this.update = update;
            }

            public static class UpdateBean implements Parcelable {
                /**
                 * loc : 2017-02-28 08:51
                 * utc : 2017-02-28 00:51
                 */

                @SerializedName("loc")
                private String loc;
                @SerializedName("utc")
                private String utc;

                public String getLoc() {
                    return loc;
                }

                public void setLoc(String loc) {
                    this.loc = loc;
                }

                public String getUtc() {
                    return utc;
                }

                public void setUtc(String utc) {
                    this.utc = utc;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.loc);
                    dest.writeString(this.utc);
                }

                public UpdateBean() {
                }

                protected UpdateBean(Parcel in) {
                    this.loc = in.readString();
                    this.utc = in.readString();
                }

                public static final Creator<UpdateBean> CREATOR = new Creator<UpdateBean>() {
                    public UpdateBean createFromParcel(Parcel source) {
                        return new UpdateBean(source);
                    }

                    public UpdateBean[] newArray(int size) {
                        return new UpdateBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.city);
                dest.writeString(this.cnty);
                dest.writeString(this.id);
                dest.writeString(this.lat);
                dest.writeString(this.lon);
                dest.writeParcelable(this.update, flags);
            }

            public BasicBean() {
            }

            protected BasicBean(Parcel in) {
                this.city = in.readString();
                this.cnty = in.readString();
                this.id = in.readString();
                this.lat = in.readString();
                this.lon = in.readString();
                this.update = in.readParcelable(UpdateBean.class.getClassLoader());
            }

            public static final Creator<BasicBean> CREATOR = new Creator<BasicBean>() {
                public BasicBean createFromParcel(Parcel source) {
                    return new BasicBean(source);
                }

                public BasicBean[] newArray(int size) {
                    return new BasicBean[size];
                }
            };
        }

        public static class NowBean implements Parcelable {
            /**
             * cond : {"code":"100","txt":"晴"}
             * fl : 15
             * hum : 54
             * pcpn : 0
             * pres : 1022
             * tmp : 15
             * vis : 8
             * wind : {"deg":"140","dir":"东北风","sc":"3-4","spd":"10"}
             */

            @SerializedName("cond")
            private CondBean cond;
            @SerializedName("fl")
            private String fl;
            @SerializedName("hum")
            private String hum;
            @SerializedName("pcpn")
            private String pcpn;
            @SerializedName("pres")
            private String pres;
            @SerializedName("tmp")
            private String tmp;
            @SerializedName("vis")
            private String vis;
            @SerializedName("wind")
            private WindBean wind;

            public CondBean getCond() {
                return cond;
            }

            public void setCond(CondBean cond) {
                this.cond = cond;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
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

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public WindBean getWind() {
                return wind;
            }

            public void setWind(WindBean wind) {
                this.wind = wind;
            }

            public static class CondBean implements Parcelable {
                /**
                 * code : 100
                 * txt : 晴
                 */

                @SerializedName("code")
                private String code;
                @SerializedName("txt")
                private String txt;

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

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.code);
                    dest.writeString(this.txt);
                }

                public CondBean() {
                }

                protected CondBean(Parcel in) {
                    this.code = in.readString();
                    this.txt = in.readString();
                }

                public static final Creator<CondBean> CREATOR = new Creator<CondBean>() {
                    public CondBean createFromParcel(Parcel source) {
                        return new CondBean(source);
                    }

                    public CondBean[] newArray(int size) {
                        return new CondBean[size];
                    }
                };
            }

            public static class WindBean implements Parcelable {
                /**
                 * deg : 140
                 * dir : 东北风
                 * sc : 3-4
                 * spd : 10
                 */

                @SerializedName("deg")
                private String deg;
                @SerializedName("dir")
                private String dir;
                @SerializedName("sc")
                private String sc;
                @SerializedName("spd")
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.deg);
                    dest.writeString(this.dir);
                    dest.writeString(this.sc);
                    dest.writeString(this.spd);
                }

                public WindBean() {
                }

                protected WindBean(Parcel in) {
                    this.deg = in.readString();
                    this.dir = in.readString();
                    this.sc = in.readString();
                    this.spd = in.readString();
                }

                public static final Creator<WindBean> CREATOR = new Creator<WindBean>() {
                    public WindBean createFromParcel(Parcel source) {
                        return new WindBean(source);
                    }

                    public WindBean[] newArray(int size) {
                        return new WindBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeParcelable(this.cond, flags);
                dest.writeString(this.fl);
                dest.writeString(this.hum);
                dest.writeString(this.pcpn);
                dest.writeString(this.pres);
                dest.writeString(this.tmp);
                dest.writeString(this.vis);
                dest.writeParcelable(this.wind, flags);
            }

            public NowBean() {
            }

            protected NowBean(Parcel in) {
                this.cond = in.readParcelable(CondBean.class.getClassLoader());
                this.fl = in.readString();
                this.hum = in.readString();
                this.pcpn = in.readString();
                this.pres = in.readString();
                this.tmp = in.readString();
                this.vis = in.readString();
                this.wind = in.readParcelable(WindBean.class.getClassLoader());
            }

            public static final Creator<NowBean> CREATOR = new Creator<NowBean>() {
                public NowBean createFromParcel(Parcel source) {
                    return new NowBean(source);
                }

                public NowBean[] newArray(int size) {
                    return new NowBean[size];
                }
            };
        }

        public static class SuggestionBean implements Parcelable {
            /**
             * air : {"brf":"中","txt":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"}
             * comf : {"brf":"舒适","txt":"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"}
             * cw : {"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"}
             * drsg : {"brf":"较舒适","txt":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。"}
             * flu : {"brf":"较易发","txt":"天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。"}
             * sport : {"brf":"较适宜","txt":"天气较好，户外运动请注意防晒。推荐您进行室内运动。"}
             * trav : {"brf":"适宜","txt":"天气较好，但丝毫不会影响您出行的心情。温度适宜又有微风相伴，适宜旅游。"}
             * uv : {"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}
             */

            @SerializedName("air")
            private AirBean air;
            @SerializedName("comf")
            private ComfBean comf;
            @SerializedName("cw")
            private CwBean cw;
            @SerializedName("drsg")
            private DrsgBean drsg;
            @SerializedName("flu")
            private FluBean flu;
            @SerializedName("sport")
            private SportBean sport;
            @SerializedName("trav")
            private TravBean trav;
            @SerializedName("uv")
            private UvBean uv;

            public AirBean getAir() {
                return air;
            }

            public void setAir(AirBean air) {
                this.air = air;
            }

            public ComfBean getComf() {
                return comf;
            }

            public void setComf(ComfBean comf) {
                this.comf = comf;
            }

            public CwBean getCw() {
                return cw;
            }

            public void setCw(CwBean cw) {
                this.cw = cw;
            }

            public DrsgBean getDrsg() {
                return drsg;
            }

            public void setDrsg(DrsgBean drsg) {
                this.drsg = drsg;
            }

            public FluBean getFlu() {
                return flu;
            }

            public void setFlu(FluBean flu) {
                this.flu = flu;
            }

            public SportBean getSport() {
                return sport;
            }

            public void setSport(SportBean sport) {
                this.sport = sport;
            }

            public TravBean getTrav() {
                return trav;
            }

            public void setTrav(TravBean trav) {
                this.trav = trav;
            }

            public UvBean getUv() {
                return uv;
            }

            public void setUv(UvBean uv) {
                this.uv = uv;
            }

            public static class AirBean implements Parcelable {
                /**
                 * brf : 中
                 * txt : 气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。
                 */

                @SerializedName("brf")
                private String brf;
                @SerializedName("txt")
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.brf);
                    dest.writeString(this.txt);
                }

                public AirBean() {
                }

                protected AirBean(Parcel in) {
                    this.brf = in.readString();
                    this.txt = in.readString();
                }

                public static final Creator<AirBean> CREATOR = new Creator<AirBean>() {
                    public AirBean createFromParcel(Parcel source) {
                        return new AirBean(source);
                    }

                    public AirBean[] newArray(int size) {
                        return new AirBean[size];
                    }
                };
            }

            public static class ComfBean implements Parcelable {
                /**
                 * brf : 舒适
                 * txt : 白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。
                 */

                @SerializedName("brf")
                private String brf;
                @SerializedName("txt")
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.brf);
                    dest.writeString(this.txt);
                }

                public ComfBean() {
                }

                protected ComfBean(Parcel in) {
                    this.brf = in.readString();
                    this.txt = in.readString();
                }

                public static final Creator<ComfBean> CREATOR = new Creator<ComfBean>() {
                    public ComfBean createFromParcel(Parcel source) {
                        return new ComfBean(source);
                    }

                    public ComfBean[] newArray(int size) {
                        return new ComfBean[size];
                    }
                };
            }

            public static class CwBean implements Parcelable {
                /**
                 * brf : 较适宜
                 * txt : 较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。
                 */

                @SerializedName("brf")
                private String brf;
                @SerializedName("txt")
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.brf);
                    dest.writeString(this.txt);
                }

                public CwBean() {
                }

                protected CwBean(Parcel in) {
                    this.brf = in.readString();
                    this.txt = in.readString();
                }

                public static final Creator<CwBean> CREATOR = new Creator<CwBean>() {
                    public CwBean createFromParcel(Parcel source) {
                        return new CwBean(source);
                    }

                    public CwBean[] newArray(int size) {
                        return new CwBean[size];
                    }
                };
            }

            public static class DrsgBean implements Parcelable {
                /**
                 * brf : 较舒适
                 * txt : 建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。
                 */

                @SerializedName("brf")
                private String brf;
                @SerializedName("txt")
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.brf);
                    dest.writeString(this.txt);
                }

                public DrsgBean() {
                }

                protected DrsgBean(Parcel in) {
                    this.brf = in.readString();
                    this.txt = in.readString();
                }

                public static final Creator<DrsgBean> CREATOR = new Creator<DrsgBean>() {
                    public DrsgBean createFromParcel(Parcel source) {
                        return new DrsgBean(source);
                    }

                    public DrsgBean[] newArray(int size) {
                        return new DrsgBean[size];
                    }
                };
            }

            public static class FluBean implements Parcelable {
                /**
                 * brf : 较易发
                 * txt : 天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。
                 */

                @SerializedName("brf")
                private String brf;
                @SerializedName("txt")
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.brf);
                    dest.writeString(this.txt);
                }

                public FluBean() {
                }

                protected FluBean(Parcel in) {
                    this.brf = in.readString();
                    this.txt = in.readString();
                }

                public static final Creator<FluBean> CREATOR = new Creator<FluBean>() {
                    public FluBean createFromParcel(Parcel source) {
                        return new FluBean(source);
                    }

                    public FluBean[] newArray(int size) {
                        return new FluBean[size];
                    }
                };
            }

            public static class SportBean implements Parcelable {
                /**
                 * brf : 较适宜
                 * txt : 天气较好，户外运动请注意防晒。推荐您进行室内运动。
                 */

                @SerializedName("brf")
                private String brf;
                @SerializedName("txt")
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.brf);
                    dest.writeString(this.txt);
                }

                public SportBean() {
                }

                protected SportBean(Parcel in) {
                    this.brf = in.readString();
                    this.txt = in.readString();
                }

                public static final Creator<SportBean> CREATOR = new Creator<SportBean>() {
                    public SportBean createFromParcel(Parcel source) {
                        return new SportBean(source);
                    }

                    public SportBean[] newArray(int size) {
                        return new SportBean[size];
                    }
                };
            }

            public static class TravBean implements Parcelable {
                /**
                 * brf : 适宜
                 * txt : 天气较好，但丝毫不会影响您出行的心情。温度适宜又有微风相伴，适宜旅游。
                 */

                @SerializedName("brf")
                private String brf;
                @SerializedName("txt")
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.brf);
                    dest.writeString(this.txt);
                }

                public TravBean() {
                }

                protected TravBean(Parcel in) {
                    this.brf = in.readString();
                    this.txt = in.readString();
                }

                public static final Creator<TravBean> CREATOR = new Creator<TravBean>() {
                    public TravBean createFromParcel(Parcel source) {
                        return new TravBean(source);
                    }

                    public TravBean[] newArray(int size) {
                        return new TravBean[size];
                    }
                };
            }

            public static class UvBean implements Parcelable {
                /**
                 * brf : 弱
                 * txt : 紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。
                 */

                @SerializedName("brf")
                private String brf;
                @SerializedName("txt")
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.brf);
                    dest.writeString(this.txt);
                }

                public UvBean() {
                }

                protected UvBean(Parcel in) {
                    this.brf = in.readString();
                    this.txt = in.readString();
                }

                public static final Creator<UvBean> CREATOR = new Creator<UvBean>() {
                    public UvBean createFromParcel(Parcel source) {
                        return new UvBean(source);
                    }

                    public UvBean[] newArray(int size) {
                        return new UvBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeParcelable(this.air, flags);
                dest.writeParcelable(this.comf, flags);
                dest.writeParcelable(this.cw, flags);
                dest.writeParcelable(this.drsg, flags);
                dest.writeParcelable(this.flu, flags);
                dest.writeParcelable(this.sport, flags);
                dest.writeParcelable(this.trav, flags);
                dest.writeParcelable(this.uv, flags);
            }

            public SuggestionBean() {
            }

            protected SuggestionBean(Parcel in) {
                this.air = in.readParcelable(AirBean.class.getClassLoader());
                this.comf = in.readParcelable(ComfBean.class.getClassLoader());
                this.cw = in.readParcelable(CwBean.class.getClassLoader());
                this.drsg = in.readParcelable(DrsgBean.class.getClassLoader());
                this.flu = in.readParcelable(FluBean.class.getClassLoader());
                this.sport = in.readParcelable(SportBean.class.getClassLoader());
                this.trav = in.readParcelable(TravBean.class.getClassLoader());
                this.uv = in.readParcelable(UvBean.class.getClassLoader());
            }

            public static final Parcelable.Creator<SuggestionBean> CREATOR = new Parcelable.Creator<SuggestionBean>() {
                public SuggestionBean createFromParcel(Parcel source) {
                    return new SuggestionBean(source);
                }

                public SuggestionBean[] newArray(int size) {
                    return new SuggestionBean[size];
                }
            };
        }

        public static class DailyForecastBean implements Parcelable {
            /**
             * astro : {"mr":"07:50","ms":"20:08","sr":"06:45","ss":"18:26"}
             * cond : {"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"}
             * date : 2017-02-28
             * hum : 59
             * pcpn : 0.0
             * pop : 0
             * pres : 1021
             * tmp : {"max":"20","min":"15"}
             * uv : 10
             * vis : 10
             * wind : {"deg":"105","dir":"无持续风向","sc":"微风","spd":"0"}
             */

            @SerializedName("astro")
            private AstroBean astro;
            @SerializedName("cond")
            private CondBeanX cond;
            @SerializedName("date")
            private String date;
            @SerializedName("hum")
            private String hum;
            @SerializedName("pcpn")
            private String pcpn;
            @SerializedName("pop")
            private String pop;
            @SerializedName("pres")
            private String pres;
            @SerializedName("tmp")
            private TmpBean tmp;
            @SerializedName("uv")
            private String uv;
            @SerializedName("vis")
            private String vis;
            @SerializedName("wind")
            private WindBeanX wind;

            public AstroBean getAstro() {
                return astro;
            }

            public void setAstro(AstroBean astro) {
                this.astro = astro;
            }

            public CondBeanX getCond() {
                return cond;
            }

            public void setCond(CondBeanX cond) {
                this.cond = cond;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
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

            public TmpBean getTmp() {
                return tmp;
            }

            public void setTmp(TmpBean tmp) {
                this.tmp = tmp;
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

            public WindBeanX getWind() {
                return wind;
            }

            public void setWind(WindBeanX wind) {
                this.wind = wind;
            }

            public static class AstroBean implements Parcelable {
                /**
                 * mr : 07:50
                 * ms : 20:08
                 * sr : 06:45
                 * ss : 18:26
                 */

                @SerializedName("mr")
                private String mr;
                @SerializedName("ms")
                private String ms;
                @SerializedName("sr")
                private String sr;
                @SerializedName("ss")
                private String ss;

                public String getMr() {
                    return mr;
                }

                public void setMr(String mr) {
                    this.mr = mr;
                }

                public String getMs() {
                    return ms;
                }

                public void setMs(String ms) {
                    this.ms = ms;
                }

                public String getSr() {
                    return sr;
                }

                public void setSr(String sr) {
                    this.sr = sr;
                }

                public String getSs() {
                    return ss;
                }

                public void setSs(String ss) {
                    this.ss = ss;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.mr);
                    dest.writeString(this.ms);
                    dest.writeString(this.sr);
                    dest.writeString(this.ss);
                }

                public AstroBean() {
                }

                protected AstroBean(Parcel in) {
                    this.mr = in.readString();
                    this.ms = in.readString();
                    this.sr = in.readString();
                    this.ss = in.readString();
                }

                public static final Creator<AstroBean> CREATOR = new Creator<AstroBean>() {
                    public AstroBean createFromParcel(Parcel source) {
                        return new AstroBean(source);
                    }

                    public AstroBean[] newArray(int size) {
                        return new AstroBean[size];
                    }
                };
            }

            public static class CondBeanX implements Parcelable {
                /**
                 * code_d : 101
                 * code_n : 101
                 * txt_d : 多云
                 * txt_n : 多云
                 */

                @SerializedName("code_d")
                private String codeD;
                @SerializedName("code_n")
                private String codeN;
                @SerializedName("txt_d")
                private String txtD;
                @SerializedName("txt_n")
                private String txtN;

                public String getCodeD() {
                    return codeD;
                }

                public void setCodeD(String codeD) {
                    this.codeD = codeD;
                }

                public String getCodeN() {
                    return codeN;
                }

                public void setCodeN(String codeN) {
                    this.codeN = codeN;
                }

                public String getTxtD() {
                    return txtD;
                }

                public void setTxtD(String txtD) {
                    this.txtD = txtD;
                }

                public String getTxtN() {
                    return txtN;
                }

                public void setTxtN(String txtN) {
                    this.txtN = txtN;
                }

                public CondBeanX() {
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.codeD);
                    dest.writeString(this.codeN);
                    dest.writeString(this.txtD);
                    dest.writeString(this.txtN);
                }

                protected CondBeanX(Parcel in) {
                    this.codeD = in.readString();
                    this.codeN = in.readString();
                    this.txtD = in.readString();
                    this.txtN = in.readString();
                }

                public static final Creator<CondBeanX> CREATOR = new Creator<CondBeanX>() {
                    public CondBeanX createFromParcel(Parcel source) {
                        return new CondBeanX(source);
                    }

                    public CondBeanX[] newArray(int size) {
                        return new CondBeanX[size];
                    }
                };
            }

            public static class TmpBean implements Parcelable {
                /**
                 * max : 20
                 * min : 15
                 */

                @SerializedName("max")
                private String max;
                @SerializedName("min")
                private String min;

                public String getMax() {
                    return max;
                }

                public void setMax(String max) {
                    this.max = max;
                }

                public String getMin() {
                    return min;
                }

                public void setMin(String min) {
                    this.min = min;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.max);
                    dest.writeString(this.min);
                }

                public TmpBean() {
                }

                protected TmpBean(Parcel in) {
                    this.max = in.readString();
                    this.min = in.readString();
                }

                public static final Creator<TmpBean> CREATOR = new Creator<TmpBean>() {
                    public TmpBean createFromParcel(Parcel source) {
                        return new TmpBean(source);
                    }

                    public TmpBean[] newArray(int size) {
                        return new TmpBean[size];
                    }
                };
            }

            public static class WindBeanX implements Parcelable {
                /**
                 * deg : 105
                 * dir : 无持续风向
                 * sc : 微风
                 * spd : 0
                 */

                @SerializedName("deg")
                private String deg;
                @SerializedName("dir")
                private String dir;
                @SerializedName("sc")
                private String sc;
                @SerializedName("spd")
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.deg);
                    dest.writeString(this.dir);
                    dest.writeString(this.sc);
                    dest.writeString(this.spd);
                }

                public WindBeanX() {
                }

                protected WindBeanX(Parcel in) {
                    this.deg = in.readString();
                    this.dir = in.readString();
                    this.sc = in.readString();
                    this.spd = in.readString();
                }

                public static final Creator<WindBeanX> CREATOR = new Creator<WindBeanX>() {
                    public WindBeanX createFromParcel(Parcel source) {
                        return new WindBeanX(source);
                    }

                    public WindBeanX[] newArray(int size) {
                        return new WindBeanX[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeParcelable(this.astro, flags);
                dest.writeParcelable(this.cond, flags);
                dest.writeString(this.date);
                dest.writeString(this.hum);
                dest.writeString(this.pcpn);
                dest.writeString(this.pop);
                dest.writeString(this.pres);
                dest.writeParcelable(this.tmp, flags);
                dest.writeString(this.uv);
                dest.writeString(this.vis);
                dest.writeParcelable(this.wind, flags);
            }

            public DailyForecastBean() {
            }

            protected DailyForecastBean(Parcel in) {
                this.astro = in.readParcelable(AstroBean.class.getClassLoader());
                this.cond = in.readParcelable(CondBeanX.class.getClassLoader());
                this.date = in.readString();
                this.hum = in.readString();
                this.pcpn = in.readString();
                this.pop = in.readString();
                this.pres = in.readString();
                this.tmp = in.readParcelable(TmpBean.class.getClassLoader());
                this.uv = in.readString();
                this.vis = in.readString();
                this.wind = in.readParcelable(WindBeanX.class.getClassLoader());
            }

            public static final Creator<DailyForecastBean> CREATOR = new Creator<DailyForecastBean>() {
                public DailyForecastBean createFromParcel(Parcel source) {
                    return new DailyForecastBean(source);
                }

                public DailyForecastBean[] newArray(int size) {
                    return new DailyForecastBean[size];
                }
            };
        }

        public static class HourlyForecastBean implements Parcelable {
            /**
             * cond : {"code":"100","txt":"晴"}
             * date : 2017-02-28 10:00
             * hum : 51
             * pop : 0
             * pres : 1023
             * tmp : 21
             * wind : {"deg":"70","dir":"东北风","sc":"微风","spd":"8"}
             */

            @SerializedName("cond")
            private CondBeanXX cond;
            @SerializedName("date")
            private String date;
            @SerializedName("hum")
            private String hum;
            @SerializedName("pop")
            private String pop;
            @SerializedName("pres")
            private String pres;
            @SerializedName("tmp")
            private String tmp;
            @SerializedName("wind")
            private WindBeanXX wind;

            public CondBeanXX getCond() {
                return cond;
            }

            public void setCond(CondBeanXX cond) {
                this.cond = cond;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
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

            public WindBeanXX getWind() {
                return wind;
            }

            public void setWind(WindBeanXX wind) {
                this.wind = wind;
            }

            public static class CondBeanXX implements Parcelable {
                /**
                 * code : 100
                 * txt : 晴
                 */

                @SerializedName("code")
                private String code;
                @SerializedName("txt")
                private String txt;

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

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.code);
                    dest.writeString(this.txt);
                }

                public CondBeanXX() {
                }

                protected CondBeanXX(Parcel in) {
                    this.code = in.readString();
                    this.txt = in.readString();
                }

                public static final Creator<CondBeanXX> CREATOR = new Creator<CondBeanXX>() {
                    public CondBeanXX createFromParcel(Parcel source) {
                        return new CondBeanXX(source);
                    }

                    public CondBeanXX[] newArray(int size) {
                        return new CondBeanXX[size];
                    }
                };
            }

            public static class WindBeanXX implements Parcelable {
                /**
                 * deg : 70
                 * dir : 东北风
                 * sc : 微风
                 * spd : 8
                 */

                @SerializedName("deg")
                private String deg;
                @SerializedName("dir")
                private String dir;
                @SerializedName("sc")
                private String sc;
                @SerializedName("spd")
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.deg);
                    dest.writeString(this.dir);
                    dest.writeString(this.sc);
                    dest.writeString(this.spd);
                }

                public WindBeanXX() {
                }

                protected WindBeanXX(Parcel in) {
                    this.deg = in.readString();
                    this.dir = in.readString();
                    this.sc = in.readString();
                    this.spd = in.readString();
                }

                public static final Creator<WindBeanXX> CREATOR = new Creator<WindBeanXX>() {
                    public WindBeanXX createFromParcel(Parcel source) {
                        return new WindBeanXX(source);
                    }

                    public WindBeanXX[] newArray(int size) {
                        return new WindBeanXX[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeParcelable(this.cond, flags);
                dest.writeString(this.date);
                dest.writeString(this.hum);
                dest.writeString(this.pop);
                dest.writeString(this.pres);
                dest.writeString(this.tmp);
                dest.writeParcelable(this.wind, flags);
            }

            public HourlyForecastBean() {
            }

            protected HourlyForecastBean(Parcel in) {
                this.cond = in.readParcelable(CondBeanXX.class.getClassLoader());
                this.date = in.readString();
                this.hum = in.readString();
                this.pop = in.readString();
                this.pres = in.readString();
                this.tmp = in.readString();
                this.wind = in.readParcelable(WindBeanXX.class.getClassLoader());
            }

            public static final Creator<HourlyForecastBean> CREATOR = new Creator<HourlyForecastBean>() {
                public HourlyForecastBean createFromParcel(Parcel source) {
                    return new HourlyForecastBean(source);
                }

                public HourlyForecastBean[] newArray(int size) {
                    return new HourlyForecastBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.aqi, flags);
            dest.writeParcelable(this.basic, flags);
            dest.writeParcelable(this.now, flags);
            dest.writeString(this.status);
            dest.writeParcelable(this.suggestion, 0);
            dest.writeList(this.dailyForecast);
            dest.writeList(this.hourlyForecast);
        }

        public HeWeather5Bean() {
        }

        protected HeWeather5Bean(Parcel in) {
            this.aqi = in.readParcelable(AqiBean.class.getClassLoader());
            this.basic = in.readParcelable(BasicBean.class.getClassLoader());
            this.now = in.readParcelable(NowBean.class.getClassLoader());
            this.status = in.readString();
            this.suggestion = in.readParcelable(SuggestionBean.class.getClassLoader());
            this.dailyForecast = new ArrayList<DailyForecastBean>();
            in.readList(this.dailyForecast, List.class.getClassLoader());
            this.hourlyForecast = new ArrayList<HourlyForecastBean>();
            in.readList(this.hourlyForecast, List.class.getClassLoader());
        }

        public static final Creator<HeWeather5Bean> CREATOR = new Creator<HeWeather5Bean>() {
            public HeWeather5Bean createFromParcel(Parcel source) {
                return new HeWeather5Bean(source);
            }

            public HeWeather5Bean[] newArray(int size) {
                return new HeWeather5Bean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.HeWeather5);
    }

    public HeWeatherModel() {
    }

    protected HeWeatherModel(Parcel in) {
        this.HeWeather5 = new ArrayList<HeWeather5Bean>();
        in.readList(this.HeWeather5, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<HeWeatherModel> CREATOR = new Parcelable.Creator<HeWeatherModel>() {
        public HeWeatherModel createFromParcel(Parcel source) {
            return new HeWeatherModel(source);
        }

        public HeWeatherModel[] newArray(int size) {
            return new HeWeatherModel[size];
        }
    };
}
