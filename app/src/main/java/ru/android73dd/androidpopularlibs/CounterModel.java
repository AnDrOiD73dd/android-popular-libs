package ru.android73dd.androidpopularlibs;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;


public class CounterModel
{
    List<Integer> counters;
    List<PublishSubject<Integer>> subjects;

    public CounterModel()
    {
        counters = new ArrayList<>();
        counters.add(0);
        counters.add(0);
        counters.add(0);
        subjects = new ArrayList<>();
        PublishSubject<Integer> subject1 = PublishSubject.create();
        PublishSubject<Integer> subject2 = PublishSubject.create();
        PublishSubject<Integer> subject3 = PublishSubject.create();
        subjects.add(subject1);
        subjects.add(subject2);
        subjects.add(subject3);
    }

    public void calculate(int index)
    {
        int value = counters.get(index) + 1;
        counters.set(index, value);
        subjects.get(index).onNext(value);
    }

    public PublishSubject<Integer> getSubject(int index) {
        return subjects.get(index);
    }

    public List<PublishSubject<Integer>> getSubjects() {
        return subjects;
    }
}
