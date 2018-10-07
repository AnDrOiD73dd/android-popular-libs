package ru.geekbrains.android3_5;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import io.paperdb.Paper;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import ru.geekbrains.android3_5.mvp.model.entity.activeandroid.AARepository;
import ru.geekbrains.android3_5.mvp.model.entity.activeandroid.AAUser;
import timber.log.Timber;

public class App extends Application
{
    static private App instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        Timber.plant(new Timber.DebugTree());
        Paper.init(this);
        Configuration dbConfiguration = new Configuration.Builder(this)
                .setDatabaseName("MyDb.db")
                .addModelClass(AAUser.class)
                .addModelClass(AARepository.class)
                .create();
        ActiveAndroid.initialize(dbConfiguration);
//        ActiveAndroid.initialize(this);
        Realm.init(this);
    }

    public static App getInstance()
    {
        return instance;
    }
}
