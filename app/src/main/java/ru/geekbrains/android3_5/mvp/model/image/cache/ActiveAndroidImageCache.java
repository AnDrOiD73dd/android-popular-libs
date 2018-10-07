package ru.geekbrains.android3_5.mvp.model.image.cache;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;

import io.reactivex.Observable;

public class ActiveAndroidImageCache implements ImageCache {
    @Override
    public void saveImage(String url, Context context, Bitmap resource) {

    }

    @Override
    public Observable<File> getImage(String url) {
        return null;
    }
}
