package ru.android73dd.androidpopularlibs.model;

import java.io.InputStream;

import io.reactivex.Completable;
import io.reactivex.Single;


public interface FileManager {
    Completable convertImage(String imagePath);

    Single<String> convertImage(InputStream stream);
}
