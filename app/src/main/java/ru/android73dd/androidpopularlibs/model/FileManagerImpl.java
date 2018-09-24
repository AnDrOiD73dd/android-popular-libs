package ru.android73dd.androidpopularlibs.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import io.reactivex.Completable;
import io.reactivex.Single;


public class FileManagerImpl implements FileManager {

    public static final String PNG_SUFFIX = ".png";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss";
    private final File storageDir;

    public FileManagerImpl(File externalFilesDir) {
        this.storageDir = externalFilesDir;
    }

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

    @Override
    public Single<String> convertImage(InputStream stream) {
        return Single.create(emitter -> {
            try {
                Thread.sleep(new Random().nextInt(3000) + 1000);
                byte[] originalBytes = getByteArrayFromStream(stream);
                byte[] convertedBytes = convertToPng(originalBytes);
                File file = createImageFile(storageDir, generateFileName(), PNG_SUFFIX, convertedBytes);
                emitter.onSuccess(file.getAbsolutePath());
            } catch (Exception e) {
                if (!emitter.isDisposed()) {
                    emitter.onError(e);
                }
            }
        });
    }

    private byte[] getByteArrayFromStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while (inputStream.available() > 0 && (len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private byte[] convertToPng(byte[] rawByteArr) throws IOException {
        ByteArrayOutputStream convertStream = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap bitmap = BitmapFactory.decodeByteArray(rawByteArr, 0, rawByteArr.length, options);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, convertStream);
        byte[] convertByteArray = convertStream.toByteArray();
        convertStream.close();
        bitmap.recycle();
        return convertByteArray;
    }

    private File createImageFile(File directoryPath, String filename, String suffix, byte[] byteArr) throws IOException {
        File file = File.createTempFile(filename, suffix, directoryPath);
        FileOutputStream out = new FileOutputStream(file);
        out.write(byteArr);
        out.flush();
        out.close();
        return file;
    }

    private String generateFileName() {
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault()).format(new Date());
    }
}
