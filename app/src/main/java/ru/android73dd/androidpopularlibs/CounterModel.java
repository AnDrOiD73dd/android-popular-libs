package ru.android73dd.androidpopularlibs;

import java.util.ArrayList;
import java.util.List;

public class CounterModel
{
    List<Integer> counters;

    public CounterModel()
    {
        counters = new ArrayList<>();
        counters.add(0);
        counters.add(0);
        counters.add(0);
    }

    public Integer calculate(int index)
    {
        counters.set(index, counters.get(index) + 1);
        return counters.get(index);
    }
}
