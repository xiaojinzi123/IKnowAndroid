package com.iknow.module.datasource.network.convert;

import android.text.TextUtils;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.iknow.module.datasource.network.exception.IKnowApiException;
import com.iknow.module.datasource.network.EmptyResponse;
import com.iknow.module.datasource.network.Result;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.*;
import java.lang.reflect.Type;

/**
 * 脱壳转换
 */
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
            throw new IKnowApiException(response.getErrorMsg(), response.getErrorCode());
        }
        if (type == EmptyResponse.class) {
            value.close();
            return (T) new EmptyResponse();
        }
        if (null == response.getData()) {
            throw new IKnowApiException(response.getErrorMsg());
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
