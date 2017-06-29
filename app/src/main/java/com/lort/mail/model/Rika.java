package com.lort.mail.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lort.mail.Task;
import com.noodle.Noodle;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by megaman on 27.06.2017.
 */

public class Rika {
    public static final String BASE_URL = "fgbsfgbsb";
    private Gson gson;
    private Noodle noodle;
    private RikaApi rikaApi;
    private WebSocketEcho echo = new WebSocketEcho();
    WebSocket webSocket;

    public Rika(Context applicationContext) {
        noodle = Noodle.with(applicationContext)
                .build();

        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "url")
                .build();


        webSocket = client.newWebSocket(request, echo);
        client.dispatcher().executorService().shutdown();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        rikaApi = retrofit.create(RikaApi.class);
    }

    public Flowable<String> login(String user, String password) {
        return rikaApi.login(user, password)
                .subscribeOn(Schedulers.io());
    }

    public Flowable<Task> open() {
        return echo.getProcessor().map(new Function<String, Task>() {
            @Override
            public Task apply(@NonNull String s) throws Exception {
                return gson.fromJson(s, Task.class);
            }
        }).flatMap(new Function<Task, Flowable<Task>>() {
            @Override
            public Flowable<Task> apply(@NonNull Task task) throws Exception {
                return noodle.collectionOf(Task.class).put(task)
                        .toRxObservable().toFlowable(BackpressureStrategy.BUFFER);
            }
        });
    }

    public boolean send(Task task) {
        return webSocket.send(gson.toJson(task));
    }

    public Flowable<List<Task>> getTasks() {
        return noodle.collectionOf(Task.class)
                .all().toRxObservable()
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    static class WebSocketEcho extends WebSocketListener {
        private PublishProcessor<String> processor = PublishProcessor.create();

        public PublishProcessor<String> getProcessor() {
            return processor;
        }

        @Override
        public void onOpen(WebSocket webSocket, Response response) {

        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            processor.onNext(text);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            processor.onComplete();
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            processor.onError(t);
        }
    }
}
