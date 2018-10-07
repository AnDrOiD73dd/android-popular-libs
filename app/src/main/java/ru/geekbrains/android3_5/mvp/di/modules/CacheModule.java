package ru.geekbrains.android3_5.mvp.di.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3_5.mvp.model.image.ImageCache;
import ru.geekbrains.android3_5.mvp.model.image.RealmImageCache;
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
    public ImageCache imageCache() {
        return new RealmImageCache();
    }
}
