package ru.geekbrains.android3_5.mvp.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3_5.mvp.model.api.ApiService;
import ru.geekbrains.android3_5.mvp.model.repo.IUserCache;
import ru.geekbrains.android3_5.mvp.model.repo.IUserRepo;
import ru.geekbrains.android3_5.mvp.model.repo.UserRepo;

@Module(includes = {ApiModule.class, CacheModule.class})
public class RepoModule {

    @Provides
    public IUserRepo usersRepo(IUserCache cache, ApiService api) {
        return new UserRepo(cache, api);
    }
}
