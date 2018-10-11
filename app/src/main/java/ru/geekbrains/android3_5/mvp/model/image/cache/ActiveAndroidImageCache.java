package ru.geekbrains.android3_5.mvp.model.image.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.activeandroid.query.Select;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import ru.geekbrains.android3_5.mvp.model.entity.activeandroid.AAImage;

public class ActiveAndroidImageCache implements ImageCache {
    @Override
    public void saveImage(String url, Context context, Bitmap resource) {
        if (resource == null) {
            return;
        }
        File storageDir = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File file = File.createTempFile("IMG", ".png", storageDir);
            FileOutputStream out = new FileOutputStream(file);
            resource.compress(Bitmap.CompressFormat.PNG, 100, out);
            String imagePath = file.getAbsolutePath();

            AAImage aaImage = loadImage(url);
            if (aaImage == null) {
                aaImage = new AAImage();
            }
            aaImage.setUrl(url);
            aaImage.setPath(imagePath);
            aaImage.save();

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Observable<File> getImage(String url) {
        return Observable.create(emitter -> {
            AAImage aaImage = loadImage(url);
            if (aaImage == null) {
                emitter.onError(new RuntimeException("No such image in cache"));
            } else {
                emitter.onNext(new File(aaImage.getPath()));
                emitter.onComplete();
            }
        });
    }

    private AAImage loadImage(String url) {
        return new Select()
                .from(AAImage.class)
                .where("url = ?", url)
                .executeSingle();
    }
}
