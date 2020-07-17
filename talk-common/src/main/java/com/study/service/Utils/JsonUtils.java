package com.study.service.Utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Slf4j
public class JsonUtils {
    public static ObjectMapper getMapper() {
        return mapper;
    }

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static List<?> transformCollectionsFromJson(String data, Class<?> cls, Class<? extends Collection> collection) {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(collection, cls);
        try {
            return mapper.readValue(data, collectionType);
        } catch (IOException e) {
            log.error("--->>>String :{} To Collection:{} error, reason:{}", data, collection, e.getLocalizedMessage(), e);
            return null;
        }
    }

    public static String toJson(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("--->>>String :{} To Json error, reason:{}", o, e.getLocalizedMessage(), e);
            return null;
        }
    }

    public static <T> T  fromJson(String data, Class<T> cls) {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        try {
            return mapper.readValue(data,cls);
        } catch (IOException e) {
            log.error("--->>>String :{} To Object:{} error, reason:{}", data, cls, e.getLocalizedMessage(), e);
            return null;
        }
    }

}
