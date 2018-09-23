package ru.android73dd.androidpopularlibs.model;

import io.reactivex.Completable;

public interface FileManager {
    Completable convertImage(String imagePath);
}
