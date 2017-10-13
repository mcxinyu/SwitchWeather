package io.github.mcxinyu.switchweather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huangyuefeng on 2017/8/30.
 * Contact me : mcxinyu@foxmail.com
 */
public class CaiYunWeatherForecast {

    @SerializedName("status")
    private String status;
    @SerializedName("lang")
    private String lang;
    @SerializedName("result")
    private ResultBean result;
    @SerializedName("server_time")
    private int serverTime;
    @SerializedName("api_status")
    private String apiStatus;
    @SerializedName("tzshift")
    private int tzShift;
    @SerializedName("api_version")
    private String apiVersion;
    @SerializedName("unit")
    private String unit;
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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getServerTime() {
        return serverTime;
    }

    public void setServerTime(int serverTime) {
        this.serverTime = serverTime;
    }

    public String getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }

    public int getTzShift() {
        return tzShift;
    }

    public void setTzShift(int tzShift) {
        this.tzShift = tzShift;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public static class ResultBean {

        @SerializedName("hourly")
        private HourlyBean hourly;
        @SerializedName("minutely")
        private MinutelyBean minutely;
        @SerializedName("daily")
        private DailyBean daily;
        @SerializedName("primary")
        private int primary;

        public HourlyBean getHourly() {
            return hourly;
        }

        public void setHourly(HourlyBean hourly) {
            this.hourly = hourly;
        }

        public MinutelyBean getMinutely() {
            return minutely;
        }

        public void setMinutely(MinutelyBean minutely) {
            this.minutely = minutely;
        }

        public DailyBean getDaily() {
            return daily;
        }

        public void setDaily(DailyBean daily) {
            this.daily = daily;
        }

        public int getPrimary() {
            return primary;
        }

        public void setPrimary(int primary) {
            this.primary = primary;
        }

        public static class HourlyBean {

            @SerializedName("status")
            private String status;
            @SerializedName("description")
            private String description;
            @SerializedName("skycon")
            private List<SkyConBean> skyCon;
            @SerializedName("cloudrate")
            private List<CloudRateBean> cloudRate;
            @SerializedName("aqi")
            private List<AqiBean> aqi;
            @SerializedName("humidity")
            private List<HumidityBean> humidity;
            @SerializedName("pm25")
            private List<Pm25Bean> pm25;
            @SerializedName("precipitation")
            private List<PrecipitationBean> precipitation;
            @SerializedName("wind")
            private List<WindBean> wind;
            @SerializedName("temperature")
            private List<TemperatureBean> temperature;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public List<SkyConBean> getSkyCon() {
                return skyCon;
            }

            public void setSkyCon(List<SkyConBean> skyCon) {
                this.skyCon = skyCon;
            }

            public List<CloudRateBean> getCloudRate() {
                return cloudRate;
            }

            public void setCloudRate(List<CloudRateBean> cloudRate) {
                this.cloudRate = cloudRate;
            }

            public List<AqiBean> getAqi() {
                return aqi;
            }

            public void setAqi(List<AqiBean> aqi) {
                this.aqi = aqi;
            }

            public List<HumidityBean> getHumidity() {
                return humidity;
            }

            public void setHumidity(List<HumidityBean> humidity) {
                this.humidity = humidity;
            }

            public List<Pm25Bean> getPm25() {
                return pm25;
            }

            public void setPm25(List<Pm25Bean> pm25) {
                this.pm25 = pm25;
            }

            public List<PrecipitationBean> getPrecipitation() {
                return precipitation;
            }

            public void setPrecipitation(List<PrecipitationBean> precipitation) {
                this.precipitation = precipitation;
            }

            public List<WindBean> getWind() {
                return wind;
            }

            public void setWind(List<WindBean> wind) {
                this.wind = wind;
            }

            public List<TemperatureBean> getTemperature() {
                return temperature;
            }

            public void setTemperature(List<TemperatureBean> temperature) {
                this.temperature = temperature;
            }

            public static class SkyConBean {
                /**
                 * value : PARTLY_CLOUDY_DAY
                 * datetime : 2017-08-30 16:00
                 */

                @SerializedName("value")
                private String value;
                @SerializedName("datetime")
                private String datetime;

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getDatetime() {
                    return datetime;
                }

                public void setDatetime(String datetime) {
                    this.datetime = datetime;
                }
            }

            public static class CloudRateBean {
                /**
                 * value : 0.64
                 * datetime : 2017-08-30 16:00
                 */

                @SerializedName("value")
                private double value;
                @SerializedName("datetime")
                private String datetime;

                public double getValue() {
                    return value;
                }

                public void setValue(double value) {
                    this.value = value;
                }

                public String getDatetime() {
                    return datetime;
                }

                public void setDatetime(String datetime) {
                    this.datetime = datetime;
                }
            }

            public static class AqiBean {
                /**
                 * value : 205
                 * datetime : 2017-08-30 16:00
                 */

                @SerializedName("value")
                private int value;
                @SerializedName("datetime")
                private String datetime;

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }

                public String getDatetime() {
                    return datetime;
                }

                public void setDatetime(String datetime) {
                    this.datetime = datetime;
                }
            }

            public static class HumidityBean {
                /**
                 * value : 0.57
                 * datetime : 2017-08-30 16:00
                 */

                @SerializedName("value")
                private double value;
                @SerializedName("datetime")
                private String datetime;

                public double getValue() {
                    return value;
                }

                public void setValue(double value) {
                    this.value = value;
                }

                public String getDatetime() {
                    return datetime;
                }

                public void setDatetime(String datetime) {
                    this.datetime = datetime;
                }
            }

            public static class Pm25Bean {
                /**
                 * value : 97
                 * datetime : 2017-08-30 16:00
                 */

                @SerializedName("value")
                private int value;
                @SerializedName("datetime")
                private String datetime;

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }

                public String getDatetime() {
                    return datetime;
                }

                public void setDatetime(String datetime) {
                    this.datetime = datetime;
                }
            }

            public static class PrecipitationBean {
                /**
                 * value : 0
                 * datetime : 2017-08-30 16:00
                 */

                @SerializedName("value")
                private double value;
                @SerializedName("datetime")
                private String datetime;

                public double getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }

                public String getDatetime() {
                    return datetime;
                }

                public void setDatetime(String datetime) {
                    this.datetime = datetime;
                }
            }

            public static class WindBean {
                /**
                 * direction : 178.87
                 * speed : 10.74
                 * datetime : 2017-08-30 16:00
                 */

                @SerializedName("direction")
                private double direction;
                @SerializedName("speed")
                private double speed;
                @SerializedName("datetime")
                private String datetime;

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

                public String getDatetime() {
                    return datetime;
                }

                public void setDatetime(String datetime) {
                    this.datetime = datetime;
                }
            }

            public static class TemperatureBean {
                /**
                 * value : 32
                 * datetime : 2017-08-30 16:00
                 */

                @SerializedName("value")
                private double value;
                @SerializedName("datetime")
                private String datetime;

                public double getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }

                public String getDatetime() {
                    return datetime;
                }

                public void setDatetime(String datetime) {
                    this.datetime = datetime;
                }
            }
        }

        public static class MinutelyBean {
            /**
             * status : ok
             * description : 不会有雨，最近的降雨带在东北63公里外呢
             * probability : [0.0535126887,0.1027696803,0.1439363807,0.166939348]
             * dataSource : radar
             * precipitation_2h : [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
             * precipitation : [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
             */

            @SerializedName("status")
            private String status;
            @SerializedName("description")
            private String description;
            @SerializedName("datasource")
            private String dataSource;
            @SerializedName("probability")
            private List<Double> probability;
            @SerializedName("precipitation_2h")
            private List<Double> precipitation2h;
            @SerializedName("precipitation")
            private List<Double> precipitation;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getDataSource() {
                return dataSource;
            }

            public void setDataSource(String dataSource) {
                this.dataSource = dataSource;
            }

            public List<Double> getProbability() {
                return probability;
            }

            public void setProbability(List<Double> probability) {
                this.probability = probability;
            }

            public List<Double> getPrecipitation2h() {
                return precipitation2h;
            }

            public void setPrecipitation2h(List<Double> precipitation2h) {
                this.precipitation2h = precipitation2h;
            }

            public List<Double> getPrecipitation() {
                return precipitation;
            }

            public void setPrecipitation(List<Double> precipitation) {
                this.precipitation = precipitation;
            }
        }

        public static class DailyBean {

            @SerializedName("status")
            private String status;
            @SerializedName("coldRisk")
            private List<ColdRiskBean> coldRisk;
            @SerializedName("temperature")
            private List<TemperatureBeanX> temperature;
            @SerializedName("skycon")
            private List<SkyconBeanX> skyCon;
            @SerializedName("cloudrate")
            private List<CloudrateBeanX> cloudRate;
            @SerializedName("aqi")
            private List<AqiBeanX> aqi;
            @SerializedName("humidity")
            private List<HumidityBeanX> humidity;
            @SerializedName("astro")
            private List<AstroBean> astro;
            @SerializedName("ultraviolet")
            private List<UltravioletBean> ultraviolet;
            @SerializedName("pm25")
            private List<Pm25BeanX> pm25;
            @SerializedName("dressing")
            private List<DressingBean> dressing;
            @SerializedName("carWashing")
            private List<CarWashingBean> carWashing;
            @SerializedName("precipitation")
            private List<PrecipitationBeanX> precipitation;
            @SerializedName("wind")
            private List<WindBeanX> wind;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public List<ColdRiskBean> getColdRisk() {
                return coldRisk;
            }

            public void setColdRisk(List<ColdRiskBean> coldRisk) {
                this.coldRisk = coldRisk;
            }

            public List<TemperatureBeanX> getTemperature() {
                return temperature;
            }

            public void setTemperature(List<TemperatureBeanX> temperature) {
                this.temperature = temperature;
            }

            public List<SkyconBeanX> getSkyCon() {
                return skyCon;
            }

            public void setSkyCon(List<SkyconBeanX> skyCon) {
                this.skyCon = skyCon;
            }

            public List<CloudrateBeanX> getCloudRate() {
                return cloudRate;
            }

            public void setCloudRate(List<CloudrateBeanX> cloudRate) {
                this.cloudRate = cloudRate;
            }

            public List<AqiBeanX> getAqi() {
                return aqi;
            }

            public void setAqi(List<AqiBeanX> aqi) {
                this.aqi = aqi;
            }

            public List<HumidityBeanX> getHumidity() {
                return humidity;
            }

            public void setHumidity(List<HumidityBeanX> humidity) {
                this.humidity = humidity;
            }

            public List<AstroBean> getAstro() {
                return astro;
            }

            public void setAstro(List<AstroBean> astro) {
                this.astro = astro;
            }

            public List<UltravioletBean> getUltraviolet() {
                return ultraviolet;
            }

            public void setUltraviolet(List<UltravioletBean> ultraviolet) {
                this.ultraviolet = ultraviolet;
            }

            public List<Pm25BeanX> getPm25() {
                return pm25;
            }

            public void setPm25(List<Pm25BeanX> pm25) {
                this.pm25 = pm25;
            }

            public List<DressingBean> getDressing() {
                return dressing;
            }

            public void setDressing(List<DressingBean> dressing) {
                this.dressing = dressing;
            }

            public List<CarWashingBean> getCarWashing() {
                return carWashing;
            }

            public void setCarWashing(List<CarWashingBean> carWashing) {
                this.carWashing = carWashing;
            }

            public List<PrecipitationBeanX> getPrecipitation() {
                return precipitation;
            }

            public void setPrecipitation(List<PrecipitationBeanX> precipitation) {
                this.precipitation = precipitation;
            }

            public List<WindBeanX> getWind() {
                return wind;
            }

            public void setWind(List<WindBeanX> wind) {
                this.wind = wind;
            }

            public static class ColdRiskBean {
                /**
                 * index : 4
                 * desc : 极易发
                 * datetime : 2017-08-30
                 */

                @SerializedName("index")
                private String index;
                @SerializedName("desc")
                private String desc;
                @SerializedName("datetime")
                private String datetime;

                public String getIndex() {
                    return index;
                }

                public void setIndex(String index) {
                    this.index = index;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getDatetime() {
                    return datetime;
                }

                public void setDatetime(String datetime) {
                    this.datetime = datetime;
                }
            }

            public static class TemperatureBeanX {
                /**
                 * date : 2017-08-30
                 * max : 34.67
                 * avg : 29.61
                 * min : 27
                 */

                @SerializedName("date")
                private String date;
                @SerializedName("max")
                private double max;
                @SerializedName("avg")
                private double avg;
                @SerializedName("min")
                private double min;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public double getMax() {
                    return max;
                }

                public void setMax(double max) {
                    this.max = max;
                }

                public double getAvg() {
                    return avg;
                }

                public void setAvg(double avg) {
                    this.avg = avg;
                }

                public double getMin() {
                    return min;
                }

                public void setMin(int min) {
                    this.min = min;
                }
            }

            public static class SkyconBeanX {
                /**
                 * date : 2017-08-30
                 * value : RAIN
                 */

                @SerializedName("date")
                private String date;
                @SerializedName("value")
                private String value;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class CloudrateBeanX {
                /**
                 * date : 2017-08-30
                 * max : 0.67
                 * avg : 0.62
                 * min : 0.2
                 */

                @SerializedName("date")
                private String date;
                @SerializedName("max")
                private double max;
                @SerializedName("avg")
                private double avg;
                @SerializedName("min")
                private double min;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public double getMax() {
                    return max;
                }

                public void setMax(double max) {
                    this.max = max;
                }

                public double getAvg() {
                    return avg;
                }

                public void setAvg(double avg) {
                    this.avg = avg;
                }

                public double getMin() {
                    return min;
                }

                public void setMin(double min) {
                    this.min = min;
                }
            }

            public static class AqiBeanX {
                /**
                 * date : 2017-08-30
                 * max : 205
                 * avg : 123.25
                 * min : 40
                 */

                @SerializedName("date")
                private String date;
                @SerializedName("max")
                private int max;
                @SerializedName("avg")
                private double avg;
                @SerializedName("min")
                private int min;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public int getMax() {
                    return max;
                }

                public void setMax(int max) {
                    this.max = max;
                }

                public double getAvg() {
                    return avg;
                }

                public void setAvg(double avg) {
                    this.avg = avg;
                }

                public int getMin() {
                    return min;
                }

                public void setMin(int min) {
                    this.min = min;
                }
            }

            public static class HumidityBeanX {
                /**
                 * date : 2017-08-30
                 * max : 0.72
                 * avg : 0.67
                 * min : 0.5
                 */

                @SerializedName("date")
                private String date;
                @SerializedName("max")
                private double max;
                @SerializedName("avg")
                private double avg;
                @SerializedName("min")
                private double min;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public double getMax() {
                    return max;
                }

                public void setMax(double max) {
                    this.max = max;
                }

                public double getAvg() {
                    return avg;
                }

                public void setAvg(double avg) {
                    this.avg = avg;
                }

                public double getMin() {
                    return min;
                }

                public void setMin(double min) {
                    this.min = min;
                }
            }

            public static class AstroBean {
                /**
                 * date : 2017-08-30
                 * sunset : {"time":"18:43"}
                 * sunrise : {"time":"06:05"}
                 */

                @SerializedName("date")
                private String date;
                @SerializedName("sunset")
                private SunsetBean sunset;
                @SerializedName("sunrise")
                private SunriseBean sunrise;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public SunsetBean getSunset() {
                    return sunset;
                }

                public void setSunset(SunsetBean sunset) {
                    this.sunset = sunset;
                }

                public SunriseBean getSunrise() {
                    return sunrise;
                }

                public void setSunrise(SunriseBean sunrise) {
                    this.sunrise = sunrise;
                }

                public static class SunsetBean {
                    /**
                     * time : 18:43
                     */

                    @SerializedName("time")
                    private String time;

                    public String getTime() {
                        return time;
                    }

                    public void setTime(String time) {
                        this.time = time;
                    }
                }

                public static class SunriseBean {
                    /**
                     * time : 06:05
                     */

                    @SerializedName("time")
                    private String time;

                    public String getTime() {
                        return time;
                    }

                    public void setTime(String time) {
                        this.time = time;
                    }
                }
            }

            public static class UltravioletBean {
                /**
                 * index : 3
                 * desc : 中等
                 * datetime : 2017-08-30
                 */

                @SerializedName("index")
                private String index;
                @SerializedName("desc")
                private String desc;
                @SerializedName("datetime")
                private String datetime;

                public String getIndex() {
                    return index;
                }

                public void setIndex(String index) {
                    this.index = index;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getDatetime() {
                    return datetime;
                }

                public void setDatetime(String datetime) {
                    this.datetime = datetime;
                }
            }

            public static class Pm25BeanX {
                /**
                 * date : 2017-08-30
                 * max : 121
                 * avg : 85.38
                 * min : 23
                 */

                @SerializedName("date")
                private String date;
                @SerializedName("max")
                private int max;
                @SerializedName("avg")
                private double avg;
                @SerializedName("min")
                private int min;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public int getMax() {
                    return max;
                }

                public void setMax(int max) {
                    this.max = max;
                }

                public double getAvg() {
                    return avg;
                }

                public void setAvg(double avg) {
                    this.avg = avg;
                }

                public int getMin() {
                    return min;
                }

                public void setMin(int min) {
                    this.min = min;
                }
            }

            public static class DressingBean {
                /**
                 * index : 2
                 * desc : 很热
                 * datetime : 2017-08-30
                 */

                @SerializedName("index")
                private String index;
                @SerializedName("desc")
                private String desc;
                @SerializedName("datetime")
                private String datetime;

                public String getIndex() {
                    return index;
                }

                public void setIndex(String index) {
                    this.index = index;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getDatetime() {
                    return datetime;
                }

                public void setDatetime(String datetime) {
                    this.datetime = datetime;
                }
            }

            public static class CarWashingBean {
                /**
                 * index : 3
                 * desc : 较不适宜
                 * datetime : 2017-08-30
                 */

                @SerializedName("index")
                private String index;
                @SerializedName("desc")
                private String desc;
                @SerializedName("datetime")
                private String datetime;

                public String getIndex() {
                    return index;
                }

                public void setIndex(String index) {
                    this.index = index;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getDatetime() {
                    return datetime;
                }

                public void setDatetime(String datetime) {
                    this.datetime = datetime;
                }
            }

            public static class PrecipitationBeanX {
                /**
                 * date : 2017-08-30
                 * max : 0.3641
                 * avg : 0.2096
                 * min : 0
                 */

                @SerializedName("date")
                private String date;
                @SerializedName("max")
                private double max;
                @SerializedName("avg")
                private double avg;
                @SerializedName("min")
                private double min;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public double getMax() {
                    return max;
                }

                public void setMax(double max) {
                    this.max = max;
                }

                public double getAvg() {
                    return avg;
                }

                public void setAvg(double avg) {
                    this.avg = avg;
                }

                public double getMin() {
                    return min;
                }

                public void setMin(int min) {
                    this.min = min;
                }
            }

            public static class WindBeanX {
                /**
                 * date : 2017-08-30
                 * max : {"direction":174.31,"speed":11.88}
                 * avg : {"direction":170.49,"speed":6.17}
                 * min : {"direction":4.17,"speed":0.57}
                 */

                @SerializedName("date")
                private String date;
                @SerializedName("max")
                private MaxBean max;
                @SerializedName("avg")
                private AvgBean avg;
                @SerializedName("min")
                private MinBean min;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public MaxBean getMax() {
                    return max;
                }

                public void setMax(MaxBean max) {
                    this.max = max;
                }

                public AvgBean getAvg() {
                    return avg;
                }

                public void setAvg(AvgBean avg) {
                    this.avg = avg;
                }

                public MinBean getMin() {
                    return min;
                }

                public void setMin(MinBean min) {
                    this.min = min;
                }

                public static class MaxBean {
                    /**
                     * direction : 174.31
                     * speed : 11.88
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

                public static class AvgBean {
                    /**
                     * direction : 170.49
                     * speed : 6.17
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

                public static class MinBean {
                    /**
                     * direction : 4.17
                     * speed : 0.57
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
    }
}
