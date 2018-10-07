package ru.geekbrains.android3_5.mvp.model.repo;

import java.util.List;

import ru.geekbrains.android3_5.mvp.model.entity.Repository;
import ru.geekbrains.android3_5.mvp.model.entity.User;

public interface IUserCache {

    User getUser(String username);

    void saveUser(User user);

    boolean isContainsUser(String username);

    List<Repository> getRepos(String username);

    void saveRepos(String username, List<Repository> repositories);

    void deleteRepos(String username);
}
