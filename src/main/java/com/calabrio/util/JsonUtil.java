package com.calabrio.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;

import java.text.ParseException;

public class JsonUtil {
    private static final Logger log = Logger.getLogger(JsonUtil.class);
    private static final Gson jsonTransformer;

    private static final String EMPTY = "{}";

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.excludeFieldsWithoutExposeAnnotation();
        jsonTransformer = builder.create();
    }

    public static String toJson(Object obj) {
        try {
            log.debug(String.format("Object [%s] to parse", obj));
            String str = jsonTransformer.toJson(obj);
            log.debug(String.format("Object [%s] parsed to JSON [%s]", obj, str));
            return str;
        } catch (Exception e) {
            log.debug("Error trying to transform to JSON", e);
            return EMPTY;
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws Exception {
        log.debug(String.format("JSON [%s] to parse", json));
        T response = jsonTransformer.fromJson(json, clazz);
        log.debug(String.format("Object [%s] parsed from JSON [%s]", response, json));

        if(response == null) {
            throw new ParseException("Error parsing JSON!", -1);
        }

        return response;
    }
}
