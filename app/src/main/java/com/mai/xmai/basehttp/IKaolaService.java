package com.mai.xmai.basehttp;

import com.mai.xmai_fast_lib.basehttp.HttpResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by speed on 2016/2/18.
 */
public interface IKaolaService {

    @FormUrlEncoded
    @POST("block/get")
    public Observable<HttpResponse<DayBriefReport>> getDayBriefReport(@Field("uid") String id, @Field("startDate") String startDate, @Field("type") String type);

}