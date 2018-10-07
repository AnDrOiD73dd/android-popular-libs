package ru.geekbrains.android3_5.mvp.model.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.realm.Realm;
import ru.geekbrains.android3_5.Utils;
import ru.geekbrains.android3_5.mvp.model.entity.realm.RealmImage;


public class RealmImageCache implements ImageCache {
    @Override
    public void saveImage(String url, Context context, Bitmap resource) {
        File storageDir = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String sha1 = Utils.SHA1(url);
        Realm realm = Realm.getDefaultInstance();
        RealmImage realmImage = realm.where(RealmImage.class).equalTo("url", sha1).findFirst();

        try {
            File file = File.createTempFile("IMG", ".png", storageDir);
            String imagePath = file.getAbsolutePath();

            FileOutputStream out = new FileOutputStream(file);
            resource.compress(Bitmap.CompressFormat.PNG, 100, out);

            if (realmImage == null) {
                realm.executeTransaction(innerRealm -> {
                    RealmImage newRealmImage = innerRealm.createObject(RealmImage.class, sha1);
                    newRealmImage.setImagePath(imagePath);
                });
            } else {
                realm.executeTransaction(innerRealm -> realmImage.setImagePath(imagePath));
            }
            realm.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Observable<File> getImage(String url) {
        return Observable.create(emitter -> {
            String sha1 = Utils.SHA1(url);
            Realm realm = Realm.getDefaultInstance();
            RealmImage realmImage = realm.where(RealmImage.class).equalTo("url", sha1).findFirst();

            if (realmImage == null) {
                emitter.onError(new RuntimeException("No such image in cache"));
            } else {
                emitter.onNext(new File(realmImage.getImagePath()));
                emitter.onComplete();
            }
            realm.close();
        });
    }
}
