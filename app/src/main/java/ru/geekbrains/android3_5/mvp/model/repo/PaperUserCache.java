package ru.geekbrains.android3_5.mvp.model.repo;

import java.util.List;

import io.paperdb.Paper;
import ru.geekbrains.android3_5.Utils;
import ru.geekbrains.android3_5.mvp.model.entity.Repository;
import ru.geekbrains.android3_5.mvp.model.entity.User;


public class PaperUserCache implements IUserCache {

    private static final String TABLE_USERS = "users";
    private static final String TABLE_REPOS = "repos";

    @Override
    public User getUser(String username) {
        return Paper.book(TABLE_USERS).read(username);
    }

    @Override
    public void saveUser(User user) {
        Paper.book(TABLE_USERS).write(user.getLogin(), user);
    }

    @Override
    public boolean isContainsUser(String username) {
        return Paper.book(TABLE_USERS).contains(username);
    }

    @Override
    public List<Repository> getRepos(String username) {
        String sha1 = Utils.SHA1(getUser(username).getReposUrl());
        return Paper.book(TABLE_REPOS).read(sha1);
    }

    @Override
    public void saveRepos(String username, List<Repository> repositories) {
        String sha1 = Utils.SHA1(getUser(username).getReposUrl());
        Paper.book(TABLE_REPOS).write(sha1, repositories);
    }

    @Override
    public void deleteRepos(String username) {
        String sha1 = Utils.SHA1(getUser(username).getReposUrl());
        Paper.book(TABLE_REPOS).delete(sha1);
    }
}
