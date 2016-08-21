package com.about.switchweather.DataBase;

/**
 * Created by 跃峰 on 2016/8/21.
 */
public class WeatherDbSchema {
    public static final class ConditionTable{
        public static final String NAME = "condition";

        public static final class Columns{
            public static final String UUID = "uuid";
            public static final String CODE = "code";
            public static final String TEXT = "txt";
            public static final String TEXT_EN = "txt_en";
            public static final String ICON = "icon";
        }
    }
}
