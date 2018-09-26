package ru.android73dd.androidpopularlibs.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {
    void updateButtonOneText(String text);

    void updateButtonTwoText(String text);

    void updateButtonThreeText(String text);

    void showInfo(String s);
}
