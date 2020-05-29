package ru.vlarp.musorg.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String toJSON(T object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.warn("JSON error.", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJSON(Class<T> type, String json) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            log.warn("JSON error.", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> fromJsonList(Class<T> type, String json) {
        try {
            return OBJECT_MAPPER.readValue(
                    json, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, type));
        } catch (IOException e) {
            log.warn("JSON error.", e);
            throw new RuntimeException(e);
        }
    }
}
