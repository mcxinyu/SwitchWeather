package io.github.mcxinyu.switchweather.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.github.mcxinyu.switchweather.api.WeatherApiHelper;
import io.github.mcxinyu.switchweather.model.V5City;
import io.github.mcxinyu.switchweather.model.V5City.HeWeather5Bean.BasicBean;
import io.github.mcxinyu.switchweather.util.QueryPreferences;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by huangyuefeng on 2017/6/6.
 * Contact me : mcxinyu@foxmail.com
 */
public class SearchDataHelper {

    public interface OnFindSuggestionsListener {
        void onResults(List<SearchV5CitySuggestion> results);
    }

    public static List<SearchV5CitySuggestion> getHistorySuggestion(Context context, int count) {
        List<BasicBean> history = QueryPreferences.getSearchHistory(context);

        List<SearchV5CitySuggestion> suggestions = new ArrayList<>();

        for (int i = 0; i < history.size(); i++) {
            SearchV5CitySuggestion suggestion = new SearchV5CitySuggestion(history.get(i));
            suggestion.setIsHistory(true);
            suggestions.add(suggestion);
            if (suggestions.size() == count) {
                break;
            }
        }

        return suggestions;
    }

    public static void findSuggestions(final Context context,
                                       final String query,
                                       final int limit,
                                       final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {

        try {
            Thread.sleep(simulatedDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final List<SearchV5CitySuggestion> suggestionList = new ArrayList<>();

        WeatherApiHelper.searchCity(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<V5City, Boolean>() {
                    @Override
                    public Boolean call(V5City v5City) {
                        return v5City != null;
                    }
                })
                .flatMap(new Func1<V5City, Observable<V5City.HeWeather5Bean>>() {
                    @Override
                    public Observable<V5City.HeWeather5Bean> call(V5City v5City) {
                        return Observable.from(v5City.getHeWeather5());
                    }
                })
                .limit(limit)
                .subscribe(new Observer<V5City.HeWeather5Bean>() {
                    @Override
                    public void onCompleted() {
                        if (listener != null) {
                            listener.onResults(suggestionList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(V5City.HeWeather5Bean heWeather5Bean) {
                        SearchV5CitySuggestion suggestion =
                                new SearchV5CitySuggestion(heWeather5Bean.getBasic());
                        suggestionList.add(suggestion);
                        QueryPreferences.setSearchHistory(context, suggestion.getBody());
                    }
                });
    }
}
