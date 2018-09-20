package ru.android73dd.androidpopularlibs;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import ru.android73dd.androidpopularlibs.bus.EventBus;
import ru.android73dd.androidpopularlibs.bus.EventBusImpl;
import ru.android73dd.androidpopularlibs.bus.EventClass;


@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private final EventBus eventBus;
    private CounterModel model;

    public MainPresenter() {
        this.model = new CounterModel();
        eventBus = new EventBusImpl();
        this.eventBus
                .observable(EventClass.class)
                .subscribe(event -> getViewState().showInfo(String.valueOf(event.getMessage())));
    }

    public void onButtonOneClick() {
        Observable<Integer> observable = model.calculate(0);
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                getViewState().updateButtonOneText(String.valueOf(integer));
                eventBus.post(new EventClass("EventBus: Value1 has been changed"));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void onButtonTwoClick() {
        Observable<Integer> observable = model.calculate(1);
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                getViewState().updateButtonTwoText(String.valueOf(integer));
                eventBus.post(new EventClass("EventBus: Value2 has been changed"));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void onButtonThreeClick() {
        Observable<Integer> observable = model.calculate(2);
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                getViewState().updateButtonThreeText(String.valueOf(integer));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
