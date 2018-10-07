package ru.geekbrains.android3_5.mvp.di.modules;

import android.widget.ImageView;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3_5.mvp.model.image.ImageCache;
import ru.geekbrains.android3_5.mvp.model.image.ImageLoader;
import ru.geekbrains.android3_5.mvp.model.image.android.ImageLoaderGlide;

@Module(includes = {CacheModule.class})
public class ImageLoaderModule {

    @Provides
    public ImageLoader<ImageView> getImageLoader(@Named("Realm") ImageCache cache) {
        return new ImageLoaderGlide(cache);
    }
}
