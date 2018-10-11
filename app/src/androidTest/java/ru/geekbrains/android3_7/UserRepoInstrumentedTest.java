package ru.geekbrains.android3_7;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import ru.geekbrains.android3_7.di.DaggerTestComponent;
import ru.geekbrains.android3_7.di.TestComponent;
import ru.geekbrains.android3_7.mvp.di.modules.ApiModule;
import ru.geekbrains.android3_7.mvp.model.api.IUserRepo;
import ru.geekbrains.android3_7.mvp.model.entity.User;

import static  org.junit.Assert.assertEquals;

public class UserRepoInstrumentedTest {

    @Inject
    IUserRepo usersRepo;

    private static MockWebServer mockWebServer;

    @BeforeClass
    public static void setupClass() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        mockWebServer.shutdown();
    }

    @Before
    public void setup() {
        TestComponent component = DaggerTestComponent
                .builder()
                .apiModule(new ApiModule() {
                               @Override
                               public String baseUrl() {
                                   return mockWebServer.url("/").toString();
                               }
                           }
                ).build();
        component.inject(this);
    }

    @Test
    public void getUser(){
        User user = new User("someuser", "someurl");
        mockWebServer.enqueue(createUserResponse(user.getLogin(), user.getAvatarUrl()));
        TestObserver<User> observer = new TestObserver<>();
        usersRepo.getUser(user.getLogin()).subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertValueCount(1);
        assertEquals(observer.values().get(0).getLogin(), user.getLogin());
        assertEquals(observer.values().get(0).getAvatarUrl(), user.getAvatarUrl());

    }

    private MockResponse createUserResponse(String login, String avatarUrl){
        String body = "{\"login\":\"" + login + "\", \"avatar_url\":\"" + avatarUrl + "\"}";
        return new MockResponse()
                .setBody(body);
    }

}
