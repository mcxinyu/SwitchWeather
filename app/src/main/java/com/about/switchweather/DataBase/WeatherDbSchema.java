package com.about.switchweather.database;

/**
 * Created by 跃峰 on 2016/8/21.
 * 数据库方案架构
 */
public class WeatherDbSchema {

    public static final class CityTable {
        public static final String NAME = "City";

        public static final class Columns{
            public static final String ID = "id";
            public static final String CITY_EN = "cityEn";
            public static final String CITY_ZH = "cityZh";
            public static final String COUNTRY_CODE = "countryCode";
            public static final String COUNTRY_EN = "countryEn";
            public static final String COUNTRY_ZH = "countryZh";
            public static final String PROVINCE_EN = "provinceEn";
            public static final String PROVINCE_ZH = "provinceZh";
            public static final String LEADER_EN = "leaderEn";
            public static final String LEADER_ZH = "leaderZh";
            public static final String LAT = "lat";
            public static final String LON = "lon";
        }
    }

    public static final class ConditionTable{
        public static final String NAME = "Condition";

        public static final class Columns{
            public static final String CODE = "code";
            public static final String TEXT = "txt";
            public static final String TEXT_EN = "txt_en";
            public static final String ICON = "icon";
        }
    }

    public static final class DailyForecastTable {
        public static final String NAME = "DailyForecast";

        public static final class Columns{
            public static final String DATE = "date";
            public static final String CITY_ID = "city_id";
            public static final String CITY = "city";
            public static final String HUM = "hum";
            public static final String PCPN = "pcpn";
            public static final String POP = "pop";
            public static final String PRES = "pres";
            public static final String UV = "uv";
            public static final String VIS = "vis";

            public static final class Astro {
                public static final String MR = "daily_forecast_astro_mr";
                public static final String MS = "daily_forecast_astro_ms";
                public static final String SR = "daily_forecast_astro_sr";
                public static final String SS = "daily_forecast_astro_ss";
            }

            public static final class Cond {
                public static final String CODE_D = "daily_forecast_cond_code_d";
                public static final String CODE_N = "daily_forecast_cond_code_n";
                public static final String TXT_D = "daily_forecast_cond_txt_d";
                public static final String TXT_N = "daily_forecast_cond_txt_n";
            }

            public static final class Tmp {
                public static final String MAX = "daily_forecast_tmp_max";
                public static final String MIN = "daily_forecast_tmp_min";
            }

            public static final class Wind {
                public static final String DEG = "daily_forecast_wind_deg";
                public static final String DIR = "daily_forecast_wind_dir";
                public static final String SC = "daily_forecast_wind_sc";
                public static final String SPD = "daily_forecast_wind_spd";
            }
        }
    }

    public static final class HourlyForecastTable {
        public static final String NAME = "HourlyForecast";

        public static final class Columns {
            public static final String DATE = "date";
            public static final String CITY_ID = "city_id";
            public static final String CITY = "city";
            public static final String HUM = "hum";
            public static final String POP = "pop";
            public static final String PRES = "pres";
            public static final String TMP = "tmp";

            public static final class Cond {
                public static final String CODE = "cond_code";
                public static final String TXT = "cond_txt";
            }

            public static final class Wind {
                public static final String DEG = "wind_deg";
                public static final String DIR = "wind_dir";
                public static final String SC = "wind_sc";
                public static final String SPD = "wind_spd";
            }
        }
    }

    public static final class WeatherInfoTable {
        public static final String NAME = "WeatherInfo";

        public static final class Columns {
            public static final String status = "status";

            public static final class Aqi {
                public static final String AQI = "aqi";
                public static final String CO = "aqi_co";
                public static final String NO2 = "aqi_no2";
                public static final String O3 = "aqi_o3";
                public static final String PM10 = "aqi_pm10";
                public static final String PM25 = "aqi_pm25";
                public static final String QLTY = "aqi_qlty";
                public static final String SO2 = "aqi_so2";
            }

            public static final class Basic {
                public static final String CITY = "basic_city";
                public static final String CNTY = "basic_cnty";
                public static final String ID = "basic_id";
                public static final String LAT = "basic_lat";
                public static final String LON = "basic_lon";

                public static final class Update {
                    public static final String LOC = "update_loc";
                    public static final String UTC = "update_utc";
                }
            }

            public static final class Now {
                public static final String FL = "now_fl";
                public static final String HUM = "now_hum";
                public static final String PCPN = "now_pcpn";
                public static final String PRES = "now_pres";
                public static final String TMP = "now_tmp";
                public static final String VIS = "now_vis";

                public static final class Cond {
                    public static final String CODE = "now_cond_code";
                    public static final String TXT = "now_cond_txt";
                }

                public static final class Wind {
                    public static final String DEG = "now_wind_deg";
                    public static final String DIR = "now_wind_dir";
                    public static final String SC = "now_wind_sc";
                    public static final String SPD = "now_wind_spd";
                }
            }

            public static final class Suggestion {

                public static final class Air {
                    public static final String BRF = "suggestion_air_brf";
                    public static final String TXT = "suggestion_air_txt";
                }

                public static final class Comf {
                    public static final String BRF = "suggestion_comf_brf";
                    public static final String TXT = "suggestion_comf_txt";
                }

                public static final class Cw {
                    public static final String BRF = "suggestion_cw_brf";
                    public static final String TXT = "suggestion_cw_txt";
                }

                public static final class Drsg {
                    public static final String BRF = "suggestion_drsg_brf";
                    public static final String TXT = "suggestion_drsg_txt";
                }

                public static final class Flu {
                    public static final String BRF = "suggestion_flu_brf";
                    public static final String TXT = "suggestion_flu_txt";
                }

                public static final class Sport {
                    public static final String BRF = "suggestion_sport_brf";
                    public static final String TXT = "suggestion_sport_txt";
                }

                public static final class Trav {
                    public static final String BRF = "suggestion_trav_brf";
                    public static final String TXT = "suggestion_trav_txt";
                }

                public static final class Uv {
                    public static final String BRF = "suggestion_uv_brf";
                    public static final String TXT = "suggestion_uv_txt";
                }
            }

            public static final class DailyForecast {
                public static final String DATE = "daily_forecast_date";
                // public static final String HUM = "daily_forecast_hum";
                // public static final String PCPN = "daily_forecast_pcpn";
                // public static final String POP = "daily_forecast_pop";
                // public static final String PRES = "daily_forecast_pres";
                public static final String UV = "daily_forecast_uv";
                // public static final String VIS = "daily_forecast_vis";

                public static final class Astro {
                    // public static final String MR = "daily_forecast_astro_mr";
                    // public static final String MS = "daily_forecast_astro_ms";
                    public static final String SR = "daily_forecast_astro_sr";
                    public static final String SS = "daily_forecast_astro_ss";
                }

                public static final class Cond {
                    public static final String CODE_D = "daily_forecast_cond_code_d";
                    public static final String CODE_N = "daily_forecast_cond_code_n";
                    public static final String TXT_D = "daily_forecast_cond_txt_d";
                    public static final String TXT_N = "daily_forecast_cond_txt_n";
                }

                public static final class Tmp {
                    public static final String MAX = "daily_forecast_tmp_max";
                    public static final String MIN = "daily_forecast_tmp_min";
                }

                // public static final class Wind {
                //     public static final String DEG = "daily_forecast_wind_deg";
                //     public static final String DIR = "daily_forecast_wind_dir";
                //     public static final String SC = "daily_forecast_wind_sc";
                //     public static final String SPD = "daily_forecast_wind_spd";
                // }
            }
        }
    }
}
