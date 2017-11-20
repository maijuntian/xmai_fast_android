package com.mai.xmai_fast_lib.basehttp;

import com.google.gson.Gson;
import com.mai.xmai_fast_lib.utils.MLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by mai on 16/11/29.
 */
public class MParams {

    private final List<String> mParamsNames = new ArrayList<>();
    private final List<Object> mParamsValues = new ArrayList<>();

    public int size() {
        if (mParamsNames != null) {
            return mParamsNames.size();
        }
        return 0;
    }

    private String name(int index) {
        return mParamsNames.get(index);
    }

    private Object value(int index) {
        return mParamsValues.get(index);
    }

    public Map<String, Object> getParams() {
        MLog.log("请求参数--->", toString());
        Map<String, Object> params = new HashMap<>();
        for (int i = 0, size = size(); i < size; i++) {
            params.put(name(i), value(i));
        }
        return params;
    }

    public Map<String, RequestBody> getFileParams() {
        Map<String, RequestBody> params = new HashMap<>();
        for (int i = 0, size = size(); i < size; i++) {
            Object value = value(i);
            if (value instanceof File) {
                params.put(name(i) + "\"; filename=\"file" + i + ".png", RequestBody.create(MediaType.parse("image/*"), (File) value));
            } else {
                params.put(name(i), RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(value)));
            }
        }
        return params;
    }

    public RequestBody getJsonRequestBody() {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0, size = size(); i < size; i++) {
            try {
                jsonObject.put(name(i), value(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
    }

    public RequestBody getJsonRequestBody(Object params) {

        return RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(params).toString());
    }

    public MParams add(String name, Object value) {
        mParamsNames.add(name);
        mParamsValues.add(value);
        return this;
    }

    @Override
    public String toString() {
        if (size() > 0) {
            StringBuilder sb = new StringBuilder("?");
            for (int i = 0, size = this.size(); i < size; i++) {
                sb.append(name(i) + "=" + String.valueOf(value(i)) + "&");
            }
            return sb.substring(0, sb.length() - 1);
        }
        return "";
    }
}
