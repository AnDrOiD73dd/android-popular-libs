package ru.android73dd.androidpopularlibs.presentation.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.android73dd.androidpopularlibs.model.FileManager;
import ru.android73dd.androidpopularlibs.presentation.view.ConvertImageView;

@InjectViewState
public class ConvertImagePresenter extends MvpPresenter<ConvertImageView> {

    private final FileManager fileManager;
    private final Scheduler androidMainThreadScheduler;
    private Disposable disposable;

    public ConvertImagePresenter(Scheduler scheduler, FileManager fileManager) {
        this.androidMainThreadScheduler = scheduler;
        this.fileManager = fileManager;
    }

    public void onChooseImageClick() {
        getViewState().checkStoragePermission();
    }

    public void onCancelClick() {
        disposable.dispose();
        getViewState().hideCancelButton();
        getViewState().updateStatus("");
    }

    public void onImageSelected(String path) {
        fileManager.convertImage(path)
                .subscribeOn(Schedulers.io())
                .observeOn(androidMainThreadScheduler)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        getViewState().updateStatus("Converting image in progress...");
                        getViewState().showCancelButton();
                    }

                    @Override
                    public void onComplete() {
                        getViewState().hideCancelButton();
                        getViewState().updateStatus("Image was successfully converted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().hideCancelButton();
                        getViewState().updateStatus("Error");
                    }
                });
    }

    public void storagePermissionIsAlreadyGranted() {
        getViewState().showChooseImages();
    }

    public void storagePermissionNotGranted() {
        getViewState().requestStoragePermission();
    }

    public void onGrantStoragePermissionSuccess() {
        getViewState().showChooseImages();
    }

    public void onGrantStoragePermissionFail() {
    }
}
