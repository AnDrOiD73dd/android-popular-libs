package ru.geekbrains.android3_5.mvp.model.entity.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "repositories")
public class AARepository extends Model
{
    @Column(name = "github_id")
    private String githubId;

    @Column(name = "name")
    private String name;

    @Column(name = "user")
    private AAUser user;

    public String getGithubId() {
        return githubId;
    }

    public void setGithubId(String id) {
        this.githubId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AAUser getUser() {
        return user;
    }

    public void setUser(AAUser user) {
        this.user = user;
    }
}
