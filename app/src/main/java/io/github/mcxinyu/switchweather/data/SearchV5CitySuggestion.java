package io.github.mcxinyu.switchweather.data;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import io.github.mcxinyu.switchweather.model.V5City;

/**
 * Created by huangyuefeng on 2017/6/6.
 * Contact me : mcxinyu@foxmail.com
 */
public class SearchV5CitySuggestion implements SearchSuggestion {

    private String cityId;
    private String city;
    private String prov;

    private String body;
    private boolean mIsHistory;

    public SearchV5CitySuggestion(String cityId, String city, String prov) {
        this.cityId = cityId;
        this.city = city;
        this.prov = prov;
        this.body = city + "," + prov + "," + cityId;
    }

    public SearchV5CitySuggestion(V5City.HeWeather5Bean.BasicBean v5City) {
        this.cityId = v5City.getId();
        this.city = v5City.getCity();
        this.prov = v5City.getProv();
        this.body = city + "," + prov + "," + cityId;
    }

    public SearchV5CitySuggestion(Parcel source) {
        this.body = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public boolean isHistory() {
        return mIsHistory;
    }

    public void setIsHistory(boolean history) {
        mIsHistory = history;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeInt(mIsHistory ? 1 : 0);
    }

    public static final Creator<SearchV5CitySuggestion> CREATOR = new Creator<SearchV5CitySuggestion>() {
        @Override
        public SearchV5CitySuggestion createFromParcel(Parcel source) {
            return new SearchV5CitySuggestion(source);
        }

        @Override
        public SearchV5CitySuggestion[] newArray(int size) {
            return new SearchV5CitySuggestion[size];
        }
    };
}
