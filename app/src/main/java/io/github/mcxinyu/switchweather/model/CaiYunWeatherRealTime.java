package io.github.mcxinyu.switchweather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huangyuefeng on 2017/8/30.
 * Contact me : mcxinyu@foxmail.com
 */
public class CaiYunWeatherRealTime {
    /**
     * status : ok
     * lang : zh_CN
     * server_time : 1504083794
     * tzshift : 28800
     * location : [25.1552,121.6544]
     * unit : metric
     * result : {"status":"ok","temperature":34.2,"skycon":"CLEAR_DAY","cloudrate":0.14,"aqi":17,"humidity":0.76,"pres":101013.69,"pm25":9,"precipitation":{"nearest":{"status":"ok","distance":21.86,"intensity":0.25},"local":{"status":"ok","intensity":0,"dataSource":"radar"}},"wind":{"direction":89.3,"speed":21.88}}
     */

    @SerializedName("status")
    private String status;
    @SerializedName("lang")
    private String lang;
    @SerializedName("server_time")
    private int serverTime;
    @SerializedName("tzshift")
    private int tzShift;
    @SerializedName("unit")
    private String unit;
    @SerializedName("result")
    private ResultBean result;
    @SerializedName("location")
    private List<Double> location;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getServerTime() {
        return serverTime;
    }

    public void setServerTime(int serverTime) {
        this.serverTime = serverTime;
    }

    public int getTzShift() {
        return tzShift;
    }

    public void setTzShift(int tzShift) {
        this.tzShift = tzShift;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public static class ResultBean {
        /**
         * status : ok
         * temperature : 34.2
         * skycon : CLEAR_DAY
         * cloudrate : 0.14
         * aqi : 17
         * humidity : 0.76
         * pres : 101013.69
         * pm25 : 9
         * precipitation : {"nearest":{"status":"ok","distance":21.86,"intensity":0.25},"local":{"status":"ok","intensity":0,"dataSource":"radar"}}
         * wind : {"direction":89.3,"speed":21.88}
         */

        @SerializedName("status")
        private String status;
        @SerializedName("temperature")
        private double temperature;
        @SerializedName("skycon")
        private String skyCon;
        @SerializedName("cloudrate")
        private double cloudRate;
        @SerializedName("aqi")
        private int aqi;
        @SerializedName("humidity")
        private double humidity;
        @SerializedName("pres")
        private double pres;
        @SerializedName("pm25")
        private int pm25;
        @SerializedName("precipitation")
        private PrecipitationBean precipitation;
        @SerializedName("wind")
        private WindBean wind;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public String getSkyCon() {
            return skyCon;
        }

        public void setSkyCon(String skyCon) {
            this.skyCon = skyCon;
        }

        public double getCloudRate() {
            return cloudRate;
        }

        public void setCloudRate(double cloudRate) {
            this.cloudRate = cloudRate;
        }

        public int getAqi() {
            return aqi;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }

        public double getHumidity() {
            return humidity;
        }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        public double getPres() {
            return pres;
        }

        public void setPres(double pres) {
            this.pres = pres;
        }

        public int getPm25() {
            return pm25;
        }

        public void setPm25(int pm25) {
            this.pm25 = pm25;
        }

        public PrecipitationBean getPrecipitation() {
            return precipitation;
        }

        public void setPrecipitation(PrecipitationBean precipitation) {
            this.precipitation = precipitation;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        public static class PrecipitationBean {
            /**
             * nearest : {"status":"ok","distance":21.86,"intensity":0.25}
             * local : {"status":"ok","intensity":0,"dataSource":"radar"}
             */

            @SerializedName("nearest")
            private NearestBean nearest;
            @SerializedName("local")
            private LocalBean local;

            public NearestBean getNearest() {
                return nearest;
            }

            public void setNearest(NearestBean nearest) {
                this.nearest = nearest;
            }

            public LocalBean getLocal() {
                return local;
            }

            public void setLocal(LocalBean local) {
                this.local = local;
            }

            public static class NearestBean {
                /**
                 * status : ok
                 * distance : 21.86
                 * intensity : 0.25
                 */

                @SerializedName("status")
                private String status;
                @SerializedName("distance")
                private double distance;
                @SerializedName("intensity")
                private double intensity;

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public double getDistance() {
                    return distance;
                }

                public void setDistance(double distance) {
                    this.distance = distance;
                }

                public double getIntensity() {
                    return intensity;
                }

                public void setIntensity(double intensity) {
                    this.intensity = intensity;
                }
            }

            public static class LocalBean {
                /**
                 * status : ok
                 * intensity : 0
                 * dataSource : radar
                 */

                @SerializedName("status")
                private String status;
                @SerializedName("intensity")
                private double intensity;
                @SerializedName("datasource")
                private String dataSource;

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public double getIntensity() {
                    return intensity;
                }

                public void setIntensity(int intensity) {
                    this.intensity = intensity;
                }

                public String getDataSource() {
                    return dataSource;
                }

                public void setDataSource(String dataSource) {
                    this.dataSource = dataSource;
                }
            }
        }

        public static class WindBean {
            /**
             * direction : 89.3
             * speed : 21.88
             */

            @SerializedName("direction")
            private double direction;
            @SerializedName("speed")
            private double speed;

            public double getDirection() {
                return direction;
            }

            public void setDirection(double direction) {
                this.direction = direction;
            }

            public double getSpeed() {
                return speed;
            }

            public void setSpeed(double speed) {
                this.speed = speed;
            }
        }
    }
}
