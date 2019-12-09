package com.iknow.module.datasource.network.convert;

import android.text.TextUtils;

import androidx.annotation.Keep;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.iknow.module.datasource.network.EmptyResponse;
import com.iknow.module.datasource.network.Result;
import com.iknow.module.datasource.network.exception.NetworkException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 脱壳转换
 */
@Keep
final class IknowResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private final Type type;


    IknowResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, Type type) {
        this.gson = gson;
        this.adapter = adapter;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        String body = value.string();
        if (TextUtils.isEmpty(body)) {
            throw new JsonParseException("The body is empty that can't convert Object!");
        }

        try {
            JSONObject jb = new JSONObject(body);
        } catch (JSONException e) {
            throw new IOException(e);
        }

        System.out.println("body = " + body);

        Result response;
        try {
            response = gson.fromJson(body, Result.class);
        } catch (JsonSyntaxException e) {
            throw e;
        }

        if (null == response || null == response.getErrorCode()) {
            throw new JsonParseException("This is not rule body!");
        }
        if (!response.isSuccess()) {
            throw new NetworkException(response.getErrorMsg(), response.getErrorCode());
        }
        if (type == EmptyResponse.class) {
            value.close();
            return (T) new EmptyResponse();
        }
        if (null == response.getData()) {
            return null;
        }
        InputStream inputStream = new ByteArrayInputStream(gson.toJson(response.getData()).getBytes());
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        JsonReader jsonReader = gson.newJsonReader(reader);
        try {
            T result = adapter.read(jsonReader);
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
            return result;
        } finally {
            value.close();
        }
    }

}
