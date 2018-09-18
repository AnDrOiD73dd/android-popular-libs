package ru.android73dd.androidpopularlibs;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView>
{
    CounterModel model;

    public MainPresenter()
    {
        this.model = new CounterModel();
    }

    public void onButtonOneClick() {
        getViewState().updateButtonOneText(String.valueOf(model.calculate(0)));
    }

    public void onButtonTwoClick() {
        getViewState().updateButtonTwoText(String.valueOf(model.calculate(1)));
    }

    public void onButtonThreeClick() {
        getViewState().updateButtonThreeText(String.valueOf(model.calculate(2)));
    }
}
