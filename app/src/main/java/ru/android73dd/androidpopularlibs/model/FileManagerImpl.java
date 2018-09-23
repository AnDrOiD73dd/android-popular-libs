package ru.android73dd.androidpopularlibs.model;

import java.io.File;
import java.util.Random;

import io.reactivex.Completable;

public class FileManagerImpl implements FileManager {

    public static final String PNG_SUFFIX = "png";

    @Override
    public Completable convertImage(String imagePath) {
        return Completable.create(emitter -> {
            try {
                Thread.sleep(new Random().nextInt(3000) + 1000);
                renameImage(imagePath);
                emitter.onComplete();
            } catch (Exception e) {
                if (!emitter.isDisposed()) {
                    emitter.onError(e);
                }
            }
        });
    }

    private void renameImage(String imagePath) throws Exception {
        File origImageFile = new File(imagePath);
        String fileName = cutSuffix(origImageFile.getName());
        StringBuilder filePathBuilder = new StringBuilder();
        filePathBuilder.append(origImageFile.getParent())
                .append("/")
                .append(fileName)
                .append(".")
                .append(PNG_SUFFIX);
        File newImageFile = new File(filePathBuilder.toString());
        if (origImageFile.exists()) {
            origImageFile.renameTo(newImageFile);
        } else {
            throw new RuntimeException("File already exists");
        }
        // Что я только не пытался сделать чтобы конвертировать,
        // 3 часа искал и пробовал различные советы - ничего у меня не заработало
//            FileOutputStream out = new FileOutputStream(filePathBuilder.toString());
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = false;
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//            out.close();
    }

    private String cutSuffix(String filename) {
        if (filename.indexOf(".") > 0) {
            filename = filename.substring(0, filename.lastIndexOf("."));
        }
        return filename;
    }
}
