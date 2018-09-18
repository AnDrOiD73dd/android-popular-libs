package ru.android73dd.androidpopularlibs;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView>
{
    private CounterModel model;
    private Observer obServerValue1;
    private Observer obServerValue2;
    private Observer obServerValue3;
    private Disposable disposable1;
    private Disposable disposable2;
    private Disposable disposable3;
    private List<PublishSubject<Integer>> subjects;

    public MainPresenter()
    {
        this.model = new CounterModel();
        subjects = model.getSubjects();
        obServerValue1 = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable1 = d;
            }

            @Override
            public void onNext(Object o) {
                getViewState().updateButtonOneText(String.valueOf(o));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        obServerValue2 = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable2 = d;
            }

            @Override
            public void onNext(Object o) {
                getViewState().updateButtonTwoText(String.valueOf(o));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        obServerValue3 = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable3 = d;
            }

            @Override
            public void onNext(Object o) {
                getViewState().updateButtonThreeText(String.valueOf(o));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);
        model.getSubject(0).subscribe(obServerValue1);
        model.getSubject(1).subscribe(obServerValue2);
        model.getSubject(2).subscribe(obServerValue3);
    }

    @Override
    public void detachView(MainView view) {
        disposable1.dispose();
        disposable2.dispose();
        disposable3.dispose();
        super.detachView(view);
    }

    public void onButtonOneClick() {
        model.calculate(0);
    }

    public void onButtonTwoClick() {
        model.calculate(1);
    }

    public void onButtonThreeClick() {
        model.calculate(2);
    }
}
