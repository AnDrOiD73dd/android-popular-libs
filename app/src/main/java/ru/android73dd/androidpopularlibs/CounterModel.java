package ru.android73dd.androidpopularlibs;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;


public class CounterModel {
    private List<Integer> counters;

    public CounterModel() {
        counters = new ArrayList<>();
        counters.add(0);
        counters.add(0);
        counters.add(0);
    }

    public Observable<Integer> calculate(int index) {
        counters.set(index, counters.get(index) + 1);
        return Observable.just(counters.get(index));
    }
}
