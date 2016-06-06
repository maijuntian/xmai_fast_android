package com.mai.xmai_fast_lib.basehttp;

import android.content.Context;
import android.widget.Toast;

import com.mai.xmai_fast_lib.exception.NetWorkException;
import com.mai.xmai_fast_lib.exception.ServerException;
import com.mai.xmai_fast_lib.utils.MLog;
import com.mai.xmai_fast_lib.utils.NetUtils;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @param <T> okHttp接口
 * @author mai
 */
public abstract class BaseRetrofitService<T> {

    private static final int TIME_OUT = 15000;
    protected T mService;

    protected abstract String getBaseUrl();

    protected void notNetWork(Context context) {
        Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
    }

    protected void serverError(Context context, int code, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    protected abstract void showDialog(Context context);

    protected abstract void dismissDialog();

    /**
     * 检测网络可用
     *
     * @param observable
     * @param context
     * @return
     */
    protected <M> Observable<M> checkNetWork(Observable<M> observable, final Context context) {
        return observable.doOnSubscribe(new Action0() {
            @Override
            public void call() {
                if (!NetUtils.isNetworkAvailable(context)) {
                    throw new NetWorkException("网络不可用");
                }
            }
        });
    }

    /**
     * loading框
     *
     * @param observable
     * @param context
     * @return
     */
    protected <M> Observable<M> showDialog(Observable<M> observable, final Context context) {
        return observable.doOnSubscribe(new Action0() {
            @Override
            public void call() {
                showDialog(context);
            }
        }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                dismissDialog();
            }
        });
    }

    /**
     * 检查返回值
     *
     * @param observable
     * @param <M>
     * @return
     */
    protected <M> Observable<M> checkResult(Observable<HttpResponse<M>> observable) {
        return observable.map(new Func1<HttpResponse<M>, M>() {
            @Override
            public M call(HttpResponse<M> response) {
                MLog.log("访问结果", response.toString());
                if (response.getCode() != 200) {
                    ServerException serverException = new ServerException(response.getMsg());
                    serverException.setCode(response.getCode());
                    throw serverException;
                }
                return response.getResult();
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 检查出错
     *
     * @param observable
     * @param context
     * @param <M>
     * @return
     */
    protected <M> Observable<M> checkError(Observable<M> observable, final Context context) {
        return observable.doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                dismissDialog();
                if (throwable instanceof NetWorkException) {
                    notNetWork(context);
                } else if (throwable instanceof ServerException) {
                    ServerException serverException = (ServerException) throwable;
                    serverError(context, serverException.getCode(), serverException.getMessage());
                } else {
                    throwable.printStackTrace();
                }
            }
        });
    }

    protected <M> Observable<M> checkAll(Observable<HttpResponse<M>> observable, Context context) {
        return checkError(showDialog(checkNetWork(checkResult(observable), context), context), context);
    }

    /**
     * 超时时间 （单位ms）
     *
     * @return
     */
    protected int getTimeOut() {
        return TIME_OUT;
    }

    public BaseRetrofitService() {

        ParameterizedType pt = (ParameterizedType) this.getClass()
                .getGenericSuperclass();

        Retrofit.Builder builder = new Retrofit.Builder();
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(getTimeOut(), TimeUnit.SECONDS);//设置超时时间
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                MLog.log("访问的地址", chain.request().toString());
                Response response = chain.proceed(chain.request());
                return response;
            }
        });
        client.setRetryOnConnectionFailure(true);
        mService = builder.baseUrl(getBaseUrl()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(client).addConverterFactory(GsonConverterFactory.create()).build().create((Class<T>) pt.getActualTypeArguments()[0]);
    }

}
