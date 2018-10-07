package ru.geekbrains.android3_5.mvp.model.repo;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.android3_5.mvp.model.entity.Repository;
import ru.geekbrains.android3_5.mvp.model.entity.User;
import ru.geekbrains.android3_5.mvp.model.entity.activeandroid.AARepository;
import ru.geekbrains.android3_5.mvp.model.entity.activeandroid.AAUser;

public class AAUserCache implements IUserCache {

    @Override
    public User getUser(String username) {
        AAUser aaUser = loadUser(username);
        if (aaUser == null) {
            return null;
        } else {
            return new User(aaUser.getLogin(), aaUser.getAvatarUrl(), aaUser.getReposUrl());
        }
    }

    @Override
    public void saveUser(User user) {
        if (user == null) {
            return;
        }
        AAUser aaUser = loadUser(user.getLogin());
        if (aaUser == null) {
            aaUser = new AAUser();
        }
        aaUser.setLogin(user.getLogin());
        aaUser.setAvatarUrl(user.getAvatarUrl());
        aaUser.setReposUrl(user.getReposUrl());
        aaUser.save();
    }

    @Override
    public boolean isContainsUser(String username) {
        return loadUser(username) != null;
    }

    @Override
    public List<Repository> getRepos(String username) {
        AAUser aaUser = loadUser(username);
        if (aaUser == null) {
            return null;
        }
        List<Repository> repos = new ArrayList<>();
        for (AARepository aaRepository : aaUser.repositories()) {
            repos.add(new Repository(aaRepository.getGithubId(), aaRepository.getName()));
        }
        return repos;
    }

    @Override
    public void saveRepos(String username, List<Repository> repositories) {
        AAUser aaUser = loadUser(username);
        if (aaUser == null) {
            return;
        }
        ActiveAndroid.beginTransaction();
        try {
            for (Repository repository : repositories) {
                AARepository aaRepository = new AARepository();
                aaRepository.setGithubId(repository.getId());
                aaRepository.setName(repository.getName());
                aaRepository.setUser(aaUser);
                aaRepository.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    @Override
    public void deleteRepos(String username) {
        AAUser aaUser = loadUser(username);
        if (aaUser != null) {
            new Delete().from(AARepository.class).where("user = ?", aaUser.getId()).execute();
        }
    }

    private AAUser loadUser(String username) {
        return new Select()
                .from(AAUser.class)
                .where("login = ?", username)
                .executeSingle();
    }
}
