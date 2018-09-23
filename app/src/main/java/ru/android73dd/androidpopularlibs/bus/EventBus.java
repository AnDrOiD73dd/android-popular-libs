package ru.android73dd.androidpopularlibs.bus;


import android.support.annotation.NonNull;

import io.reactivex.Observable;


public interface EventBus {

    void post(@NonNull Object event);

    void postUpdateUi();

    <T> Observable<T> observable(@NonNull Class<T> eventClass);
}
