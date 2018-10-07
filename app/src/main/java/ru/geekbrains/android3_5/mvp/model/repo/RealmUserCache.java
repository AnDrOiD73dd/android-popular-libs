package ru.geekbrains.android3_5.mvp.model.repo;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import ru.geekbrains.android3_5.mvp.model.entity.Repository;
import ru.geekbrains.android3_5.mvp.model.entity.User;
import ru.geekbrains.android3_5.mvp.model.entity.realm.RealmRepository;
import ru.geekbrains.android3_5.mvp.model.entity.realm.RealmUser;

public class RealmUserCache implements IUserCache {

    public static final String FIELD_LOGIN = "login";

    @Override
    public User getUser(String username) {
        Realm realm = Realm.getDefaultInstance();
        RealmUser realmUser = loadUser(realm, username);
        if (realmUser == null) {
            realm.close();
            return null;
        }
        User user = new User(realmUser.getLogin(), realmUser.getAvatarUrl(), realmUser.getReposUrl());
        realm.close();
        return user;
    }

    @Override
    public void saveUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        final RealmUser realmUser = loadUser(realm, user.getLogin());
        if (realmUser == null) {
            realm.executeTransaction(innerRealm -> {
                RealmUser newRealmUser = innerRealm.createObject(RealmUser.class, user.getLogin());
                newRealmUser.setAvatarUrl(user.getAvatarUrl());
                newRealmUser.setReposUrl(user.getReposUrl());
            });
        } else {
            realm.executeTransaction(innerRealm -> {
                realmUser.setLogin(user.getLogin());
                realmUser.setAvatarUrl(user.getAvatarUrl());
                realmUser.setReposUrl(user.getReposUrl());
            });
        }
        realm.close();
    }

    private RealmUser loadUser(Realm realm, String username) {
        return realm.where(RealmUser.class).equalTo(FIELD_LOGIN, username).findFirst();
    }

    @Override
    public boolean isContainsUser(String username) {
        Realm realm = Realm.getDefaultInstance();
        RealmUser user = loadUser(realm, username);
        realm.close();
        return user != null;
    }

    @Override
    public List<Repository> getRepos(String username) {
        Realm realm = Realm.getDefaultInstance();
        RealmUser realmUser = loadUser(realm, username);
        List<Repository> repositories = new ArrayList<>();
        if (realmUser == null) {
            realm.close();
            return null;
        } else {
            for (RealmRepository realmRepository : realmUser.getRepos()) {
                repositories.add(new Repository(realmRepository.id, realmRepository.name));
            }
        }
        realm.close();
        return repositories;
    }

    @Override
    public void saveRepos(String username, List<Repository> repositories) {
        Realm realm = Realm.getDefaultInstance();
        RealmUser realmUser = loadUser(realm, username);
        if (realmUser == null) {
            realm.close();
            return;
        }
        realm.executeTransaction(innerRealm -> {
            if (realmUser.getRepos() != null) {
                realmUser.getRepos().deleteAllFromRealm();
            }
            realmUser.setRepos(new RealmList<>());
            for (Repository repository : repositories) {
                RealmRepository realmRepository = innerRealm.createObject(RealmRepository.class, repository.getId());
                realmRepository.name = repository.getName();
                realmUser.getRepos().add(realmRepository);
            }
        });
        realm.close();
    }

    @Override
    public void deleteRepos(String username) {
        Realm realm = Realm.getDefaultInstance();
        final RealmUser realmUser = loadUser(realm, username);
        if (realmUser == null || realmUser.getRepos() == null) {
            realm.close();
            return;
        }
        realmUser.getRepos().deleteAllFromRealm();
        realm.close();
    }
}
