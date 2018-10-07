package ru.geekbrains.android3_5.mvp.model.entity.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "images_cache")
public class AAImage extends Model {

    @Column(name = "url")
    private String url;

    @Column(name = "path")
    private String path;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
