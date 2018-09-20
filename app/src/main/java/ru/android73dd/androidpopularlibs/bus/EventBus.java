package ru.android73dd.androidpopularlibs.bus;


import android.support.annotation.NonNull;

import rx.Observable;

public interface EventBus {

    void post(@NonNull Object event);

    <T> Observable<T> observable(@NonNull Class<T> eventClass);
}
