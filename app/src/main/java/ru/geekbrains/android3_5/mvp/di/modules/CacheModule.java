package ru.geekbrains.android3_5.mvp.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3_5.mvp.model.repo.IUserCache;
import ru.geekbrains.android3_5.mvp.model.repo.RealmUserCache;

@Module
public class CacheModule {

    @Provides
    public IUserCache cache(){
        return new RealmUserCache();
    }
}
