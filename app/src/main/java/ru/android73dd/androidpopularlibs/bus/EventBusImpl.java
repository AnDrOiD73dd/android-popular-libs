package ru.android73dd.androidpopularlibs.bus;


import android.support.annotation.NonNull;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class EventBusImpl implements EventBus {

    private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    public EventBusImpl() {
    }

    @Override
    public void post(@NonNull Object event) {
        if (this.bus.hasObservers()) {
            this.bus.onNext(event);
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
