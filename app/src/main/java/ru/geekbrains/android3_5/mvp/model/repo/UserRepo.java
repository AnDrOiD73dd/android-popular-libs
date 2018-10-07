package ru.geekbrains.android3_5.mvp.model.repo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3_5.mvp.model.NetworkStatus;
import ru.geekbrains.android3_5.mvp.model.api.ApiHolder;
import ru.geekbrains.android3_5.mvp.model.entity.Repository;
import ru.geekbrains.android3_5.mvp.model.entity.User;

public class UserRepo implements IUserRepo {

    private IUserCache cache;

    public UserRepo(IUserCache cache) {
        this.cache = cache;
    }

    @Override
    public Observable<User> getUser(String username) {
        if (NetworkStatus.isOnline()) {
            return ApiHolder.getInstance().getApi().getUser(username)
                    .subscribeOn(Schedulers.io())
                    .map(user -> {
                        cache.saveUser(user);
                        return user;
                    });
        } else {
            return Observable.create(emitter -> {
                User user = cache.getUser(username);
                if (user == null) {
                    emitter.onError(new RuntimeException("No such user in cache"));
                } else {
                    emitter.onNext(user);
                    emitter.onComplete();
                }
            });
        }
    }

    @Override
    public Observable<List<Repository>> getUserRepos(String username, String url) {
        if (NetworkStatus.isOnline()) {
            return ApiHolder.getInstance().getApi().getUserRepos(url)
                    .subscribeOn(Schedulers.io())
                    .map(repos -> {
                        cache.deleteRepos(username);
                        cache.saveRepos(username, repos);
                        return repos;
                    });
        } else {
            return Observable.create(emitter -> {
                List<Repository> repositories = cache.getRepos(username);
                if (repositories == null) {
                    emitter.onError(new RuntimeException("No such repositories in cache"));
                } else {
                    emitter.onNext(repositories);
                    emitter.onComplete();
                }
            });
        }
    }
}
