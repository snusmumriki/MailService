package com.lort.mail.model;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by megaman on 27.06.2017.
 */

public interface RikaApi {
    @FormUrlEncoded
    @POST("srv/auth")
    Flowable<String> login(@Field("user") String user, @Field("password") String password);
}
