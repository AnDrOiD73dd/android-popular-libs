package ru.android73dd.androidpopularlibs.presentation.view;

import com.arellomobile.mvp.MvpView;

public interface ConvertImageView extends MvpView {

    void showCancelButton();

    void hideCancelButton();

    void showChooseImages();

    void updateStatus(String text);
}
