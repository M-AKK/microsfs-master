package net.wenz.service.fs.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通过反射实现对象与json相互转换
 */
public class JsonUtil {
    public static final String DATE_FORMAT_DEFAULT_19 = "yyyy-MM-dd HH:mm:ss";
    /**
     * json转对象
     * @param clazz ,对象
     * @param json ,完整json
     * @return
     */
    public static <T>List<T> toObjectList(Class<T> clazz, String json) {
        List<Map> list = toObject(ArrayList.class, json);
        List<T> result = new ArrayList<T>();
        for (Map map : list) {
            //result.add(toObject(map, clazz));
        }
        return result;
    }
    /**
     * json转对象
     * clazz ,对象
     * json ,完整json
     * @return
     */
    public static List<Map> toMapList(String json) {
        return toObject(ArrayList.class, json);
    }
    /**
     * 将值赋值实体类
     */
    public static <T> T toObject(Map<String, Object> map, Class<T> clzz) {
        return GSonUtils.toObject(map, clzz);
    }
    /**
     * 简单json转对象
     * @param clazz ,对象
     * @param json , {"id":"10000001","loginname":"admin1","loginpsw":"12345678",
     *			"signdate":"Fri May 17 09:35:01 CST 2013","status":"1"}
     * @return
     */
    public static <T>T toObject(Class<T> clazz, String json) {
        if (json == null) {
            throw new IllegalArgumentException("无法解析的json语句.");
        }
        return GSonUtils.toObject(json, clazz);
    }

    /**
     * 将对象转换为json格式字符串
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        return GSonUtils.toJson(obj);
    }

    /**
     * 将Json串转换为Map<String, String>对象
     */
    public static Map toMap(String json) {
        if (json == null || "".equals(json)) {
            return new HashMap();
        }
        return GSonUtils.toObject(json, HashMap.class);
    }
}
