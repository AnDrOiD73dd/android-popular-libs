package ru.android73dd.androidpopularlibs.bus;

public class EventClass<T> {

    private T message;

    public EventClass(T event) {
        message = event;
    }

    public T getMessage() {
        return message;
    }
}
