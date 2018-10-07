package ru.geekbrains.android3_5.mvp.model.image.cache;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;

import io.reactivex.Observable;


public interface ImageCache {
    void saveImage(String url, Context context, Bitmap resource);

    Observable<File> getImage(String url);
}
