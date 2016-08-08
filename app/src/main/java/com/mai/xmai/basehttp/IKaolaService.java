package com.mai.xmai.basehttp;

import com.mai.xmai_fast_lib.basehttp.HttpResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by speed on 2016/2/18.
 */
public interface IKaolaService {

    @FormUrlEncoded
    @POST("block/get")
    public Observable<HttpResponse<DayBriefReport>> getDayBriefReport(@Field("uid") String id, @Field("startDate") String startDate, @Field("type") String type);

}