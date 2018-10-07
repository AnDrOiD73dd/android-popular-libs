package ru.geekbrains.android3_5.mvp.model.entity.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class RealmImage extends RealmObject {
    @PrimaryKey
    private String url;
    private String imagePath;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}