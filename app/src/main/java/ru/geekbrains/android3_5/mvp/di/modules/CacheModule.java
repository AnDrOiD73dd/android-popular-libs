package ru.geekbrains.android3_5.mvp.di.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3_5.mvp.model.image.cache.ImageCache;
import ru.geekbrains.android3_5.mvp.model.image.cache.PaperImageCache;
import ru.geekbrains.android3_5.mvp.model.image.cache.RealmImageCache;
import ru.geekbrains.android3_5.mvp.model.repo.IUserCache;
import ru.geekbrains.android3_5.mvp.model.repo.PaperUserCache;

@Module
public class CacheModule {

    @Provides
    public IUserCache cache() {
        return new PaperUserCache();
    }

    @Named("Realm")
    @Provides
    public ImageCache realmImageCache() {
        return new RealmImageCache();
    }

    @Named("Paper")
    @Provides
    public ImageCache paperImageCache() {
        return new PaperImageCache();
    }
}
