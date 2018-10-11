package ru.geekbrains.android3_7;

import com.activeandroid.ActiveAndroid;

import io.paperdb.Paper;
import io.realm.Realm;
import ru.geekbrains.android3_7.mvp.di.AppComponent;
import ru.geekbrains.android3_7.mvp.di.DaggerAppComponent;
import ru.geekbrains.android3_7.mvp.di.modules.AppModule;

public class App extends com.activeandroid.app.Application {
    private static App instance;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Paper.init(this);
        Realm.init(this);
        ActiveAndroid.initialize(this);

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
