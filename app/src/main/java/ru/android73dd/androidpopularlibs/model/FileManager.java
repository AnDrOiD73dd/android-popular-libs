package ru.android73dd.androidpopularlibs.model;

import java.io.InputStream;

import io.reactivex.Completable;


public interface FileManager {
    Completable convertImage(String imagePath);

    Completable convertImage(InputStream stream);
}
