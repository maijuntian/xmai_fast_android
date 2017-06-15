package com.mai.xmai_fast_lib.basehttp;

import android.content.Context;
import android.widget.Toast;

import com.mai.xmai_fast_lib.R;
import com.mai.xmai_fast_lib.exception.NetWorkException;
import com.mai.xmai_fast_lib.exception.ServerException;
import com.mai.xmai_fast_lib.utils.MLog;
import com.mai.xmai_fast_lib.utils.NetUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
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
        Toast.makeText(context, R.string.network_not_available, Toast.LENGTH_SHORT).show();
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
     */
    protected <M> Observable<M> checkNetWork(Observable<M> observable, final Context context) {
        return observable.doOnSubscribe(new Action0() {
            @Override
            public void call() {
                if (!NetUtils.isNetworkAvailable(context)) {
                    throw new NetWorkException("The network is not available");
                }
            }
        });
    }

    protected void timeout(Context context) {
        Toast.makeText(context, R.string.time_out, Toast.LENGTH_SHORT).show();
    }

    /**
     * loading框
     *
     * @param observable
     * @param context
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
                } else if (throwable instanceof SocketTimeoutException) {
                    timeout(context);
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
     */
    protected int getTimeOut() {
        return TIME_OUT;
    }

    public BaseRetrofitService() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                MLog.log(message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        ParameterizedType pt = (ParameterizedType) this.getClass()
                .getGenericSuperclass();

        Retrofit.Builder builder = new Retrofit.Builder();


        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(getTimeOut(), TimeUnit.MILLISECONDS)
                .addInterceptor(logging)
                .build();
        mService = builder.baseUrl(getBaseUrl()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(client).addConverterFactory(GsonConverterFactory.create()).build().create((Class<T>) pt.getActualTypeArguments()[0]);
    }

}
