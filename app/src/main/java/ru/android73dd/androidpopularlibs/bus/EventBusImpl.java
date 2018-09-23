package ru.android73dd.androidpopularlibs.bus;

import android.support.annotation.NonNull;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


public class EventBusImpl implements EventBus {

    public static final String UPDATE_UI_EVENT = "event to update UI";

    private final Subject<Object> bus = PublishSubject.create();

    public EventBusImpl() {
    }

    @Override
    public void post(@NonNull Object event) {
        if (this.bus.hasObservers()) {
            this.bus.onNext(event);
        }
    }

    @Override
    public void postUpdateUi() {
        if (this.bus.hasObservers()) {
            this.bus.onNext(new EventClass<>(UPDATE_UI_EVENT));
        }
    }

    @Override
    public <T> Observable<T> observable(@NonNull Class<T> eventClass) {
        return this.bus
                .filter(o -> o != null)
                .filter(eventClass::isInstance)
                .cast(eventClass);
    }
}
