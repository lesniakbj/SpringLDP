package com.calabrio.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;

public class JsonUtil {
    private static final Gson jsonTransformer;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        jsonTransformer = builder.create();
    }

    public static String toJson(Object obj) {
        return jsonTransformer.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws ParseException {
        T response = jsonTransformer.fromJson(json, clazz);

        if(response == null) {
            throw new ParseException("Error parsing JSON!", -1);
        }

        return response;
    }
}
