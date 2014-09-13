package com.yaya.douban.common.http;

import java.util.Collection;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * json解析工具类
 * 
 * @author doublejian
 * 
 */
public class JsonSerializer {
  private static JsonSerializer instance = new JsonSerializer();

  private ObjectMapper impl;

  public static JsonSerializer getInstance() {
    return instance;
  }

  private JsonSerializer() {
    impl = new ObjectMapper();
    impl.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
        false);
  }

  public String serialize(Object object) {
    try {
      return impl.writeValueAsString(object);
    } catch (Exception e) {
      return null;
    }
  }

  public <T> T deserialize(String json, Class<T> clazz) {
    try {
      return impl.readValue(json, clazz);
    } catch (Exception e) {
      return null;
    }
  }

  public <T extends Collection<?>, V> Object deserialize(String json,
      Class<T> collection, Class<V> data) {
    try {
      return impl.readValue(json, impl.getTypeFactory()
          .constructCollectionType(collection, data));
    } catch (Exception e) {
      return null;
    }
  }
}
