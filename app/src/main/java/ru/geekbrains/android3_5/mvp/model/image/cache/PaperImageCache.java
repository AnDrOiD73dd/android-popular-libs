package ru.geekbrains.android3_5.mvp.model.image.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.paperdb.Paper;
import io.reactivex.Observable;
import ru.geekbrains.android3_5.Utils;

public class PaperImageCache implements ImageCache {

    private static final String TABLE_IMAGES = "images";

    @Override
    public void saveImage(String url, Context context, Bitmap resource) {
        String sha1 = Utils.SHA1(url);
        File storageDir = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File file = File.createTempFile("IMG", ".png", storageDir);
            FileOutputStream out = new FileOutputStream(file);
            resource.compress(Bitmap.CompressFormat.PNG, 100, out);
            String imagePath = file.getAbsolutePath();
            Paper.book(TABLE_IMAGES).write(sha1, imagePath);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Observable<File> getImage(String url) {
        return Observable.create(emitter -> {
            String sha1 = Utils.SHA1(url);
            if (Paper.book(TABLE_IMAGES).contains(sha1)) {
                String imagePath = Paper.book(TABLE_IMAGES).read(sha1);
                emitter.onNext(new File(imagePath));
                emitter.onComplete();
            } else {
                emitter.onError(new RuntimeException("No such image in cache"));
            }
        });
    }
}
