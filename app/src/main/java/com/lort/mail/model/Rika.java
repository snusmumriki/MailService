package com.lort.mail.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lort.mail.Form;
import com.lort.mail.Task;
import com.noodle.Noodle;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
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
    public static final String URL = "http://89.218.67.174:8080/";
    public static final String WS_URL = "ws://89.218.67.174:8080/ws";
    private Gson gson;
    private Noodle noodle;
    private RikaApi rikaApi;
    private WebSocket webSocket;

    public Rika(Context applicationContext) {
        noodle = Noodle.with(applicationContext)
                .addType(Task.class)
                .addType(Form.class)
                .build();

        noodle.collectionOf(Task.class).put(new Task()).now();

        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        rikaApi = retrofit.create(RikaApi.class);
    }

   /* public Flowable<String> login(String user, String password) {
        return rikaApi.login(user, password)
                .subscribeOn(Schedulers.io());
    }*/

    public Flowable<String> login(String user, String password) {
        return null;
    }

    public Flowable<Task> open() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(WS_URL)
                .build();

        MyWebSocketListener listener = new MyWebSocketListener();
        webSocket = client.newWebSocket(request, listener);
        //client.dispatcher().executorService().shutdown();
        return listener.getProcessor().map(s -> gson.fromJson(s, Task.class))
                .flatMap(task -> noodle.collectionOf(Task.class).put(task)
                        .toRxObservable().toFlowable(BackpressureStrategy.BUFFER))
                .subscribeOn(Schedulers.io());
    }

    public boolean send(Form form) {
        return webSocket.send(gson.toJson(form));
    }

    public Flowable<List<Task>> getTasks() {
        return noodle.collectionOf(Task.class)
                .all().toRxObservable()
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    public void putTask(Task task) {
        noodle.collectionOf(Task.class).put(new Task()).now();
    }

    /*public Flowable<List<Task>> getTasks() {
        return Flowable.just(new Task())
                .repeat(10).toList()
                .toFlowable();
    }*/

    private static class MyWebSocketListener extends WebSocketListener {
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
