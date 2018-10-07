package ru.geekbrains.android3_5.mvp.model.repo;

import java.util.List;

import io.reactivex.Observable;
import ru.geekbrains.android3_5.mvp.model.entity.Repository;
import ru.geekbrains.android3_5.mvp.model.entity.User;

public interface IUserRepo {

    Observable<User> getUser(String username);

    Observable<List<Repository>> getUserRepos(String username, String url);
}
