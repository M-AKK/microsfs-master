package net.wenz.service.fs.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GSonUtils {
    public static final String DATE_FORMAT_DEFAULT_19 = "yyyy-MM-dd HH:mm:ss";
    public static SimpleDateFormat dataFormat = new SimpleDateFormat(DATE_FORMAT_DEFAULT_19);


    /**
     * 将对象转换为json字符串。 如果对象（或其持有的对象）的某个属性为日期类型，
     * 转换时将使用本系统默认的日期字符串格式"yyyy-MM-dd HH:mm:ss"（参见
     * @BaseConstants.COMMON_DATE_FORMAT_DEFAULT）。
     *
     * 输出的Json字符串将按照大家习惯的分行缩进方式进行排版。
     *
     * @param obj
     *            需要准换的对象。
     *
     * @return 返回转换后的Json 字符串
     */
    public static String toJson(Object obj) {
        return toJson(obj, null, null, true);
    }


    /**
     * 将对象转换为json字符串。 如果对象（或其持有的对象）的某个属性为日期类型，
     * 转换时将使用本系统默认的日期字符串格式"yyyy-MM-dd HH:mm:ss"（参见
     * @BaseConstants.COMMON_DATE_FORMAT_DEFAULT）。
     *
     * 输出的Json字符串将按照大家习惯的分行缩进方式进行排版。
     *
     * @param obj
     *            需要准换的对象。
     *
     * @return 返回转换后的Json 字符串
     */
    public static String toJson(Object obj, Class<?> adaptingType, TypeAdapter<?> adapter) {
        return toJson(obj, adaptingType, adapter, true);
    }


    /**
     * 将对象转换为json字符串。 如果对象（或其持有的对象）的某个属性为日期类型，
     * 转换时将使用本系统默认的日期字符串格式"yyyy-MM-dd HH:mm:ss"（参见
     * @BaseConstants.COMMON_DATE_FORMAT_DEFAULT）。
     *
     * 在序列化过程中，会忽略hibernate受管实体中所有Join的Field.
     *
     * 输出的Json字符串将按照大家习惯的分行缩进方式进行排版。
     *
     * @param obj
     *            需要准换的对象。
     *
     * @return 返回转换后的Json 字符串
     */
    public static String toJsonIgnoreJoins(Object obj) {
        return toJsonIgnoreJoins(obj, true);
    }


    /**
     * 将对象转换为json字符串。
     *
     * 输出的Json字符串将按照大家习惯的分行缩进方式进行排版。
     *
     * @param obj
     *            需要准换的对象。
     * @param dateFormat
     *            如果对象（或其持有的对象）的某个属性为日期类型，转换时将使用本参数作为日期时间格式。
     *
     * @return 返回转换后的Json 字符串
     */
    public static String toJson(Object obj, String dateFormat) {

        return toJson(obj, null, null, dateFormat, true);
    }

    /**
     * 将对象转换为json字符串。
     *
     * 输出的Json字符串将按照大家习惯的分行缩进方式进行排版。
     *
     * @param obj
     *            需要准换的对象。
     * @param dateFormat
     *            如果对象（或其持有的对象）的某个属性为日期类型，转换时将使用本参数作为日期时间格式。
     *
     * @return 返回转换后的Json 字符串
     */
    public static String toJson(Object obj, String dateFormat, boolean isPrettyPrinting) {

        return toJson(obj, null, null, dateFormat, isPrettyPrinting);
    }


    /**
     * 将对象转换为json字符串。 如果对象（或其持有的对象）的某个属性为日期类型，
     * 转换时将使用本系统默认的日期字符串格式"yyyy-MM-dd HH:mm:ss"（参见
     * @BaseConstants.COMMON_DATE_FORMAT_DEFAULT）。
     *
     * @param obj
     *            需要准换的对象。
     * @param isPrettyPrinting
     *            一个转换规则标志，该标志表明：“是否要将转换的结果按照大家习惯的分行缩进方式进行排版。”
     * @return 返回转换后的Json 字符串
     */
    public static String toJson(Object obj, boolean isPrettyPrinting) {

        return toJson(obj, DATE_FORMAT_DEFAULT_19, isPrettyPrinting);
    }

    /**
     * 将对象转换为json字符串。 如果对象（或其持有的对象）的某个属性为日期类型，
     * 转换时将使用本系统默认的日期字符串格式"yyyy-MM-dd HH:mm:ss"（参见
     * @BaseConstants.COMMON_DATE_FORMAT_DEFAULT）。
     *
     * @param obj  需要准换的对象。
     * @param isPrettyPrinting   一个转换规则标志，该标志表明：“是否要将转换的结果按照大家习惯的分行缩进方式进行排版。”
     * @return 返回转换后的Json 字符串
     */
    public static String toJson(Object obj, Class<?> adaptingType, TypeAdapter<?> adapter, boolean isPrettyPrinting) {

        return toJson(obj, adaptingType, adapter, DATE_FORMAT_DEFAULT_19, isPrettyPrinting);
    }



    /**
     * 将对象转换为json字符串。 如果对象（或其持有的对象）的某个属性为日期类型，
     * 转换时将使用本系统默认的日期字符串格式"yyyy-MM-dd HH:mm:ss"（参见
     * @BaseConstants.COMMON_DATE_FORMAT_DEFAULT）。
     *
     * @param obj 需要准换的对象。
     * @param isPrettyPrinting 一个转换规则标志，该标志表明：“是否要将转换的结果按照大家习惯的分行缩进方式进行排版。”
     * @return 返回转换后的Json 字符串
     */
    public static String toJsonIgnoreJoins(Object obj, boolean isPrettyPrinting) {
        return toJsonIgnoreJoins(obj, DATE_FORMAT_DEFAULT_19, isPrettyPrinting);
    }

    @SuppressWarnings({ "unchecked"})
    public static String toJsonAsDebug(Object... objs) {

        List<Map> objMaps = new ArrayList<Map>();

        for (int i = 0; i < objs.length; i++) {
            Object obj = objs[i];

            Map objInMap = new HashMap();
            if (obj == null) {
                objInMap.put("obj:[" + i + "]", "null");
            } else {
                try {
                    objInMap = BeanUtils.describe(obj);
                } catch (Exception e) {
                    objInMap.put("GSonUtils.toJsonAsDebug.error",
                            "message:"+e.getMessage());
                    objInMap.put("obj:[" + i + "]", obj.toString());
                }
            }
            objMaps.add(objInMap);
        }

        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.setDateFormat(DATE_FORMAT_DEFAULT_19);
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.setExclusionStrategies(new DefaultTypeStrategy());

        Gson gson = gsonBuilder.create();

        String json = "";

        if (objMaps.size() > 1) {
            json = gson.toJson(objMaps);
        } else if (objMaps.size() == 1) {
            json = gson.toJson(objMaps.get(0));
        }
        return json;
    }

    /**
     * 将对象转换为json字符串。
     *
     * @param obj 需要准换的对象。
     * @param dateFormat  转换日期类型属性时使用的日期格式（JSF标准）。例如“yyyy-MM-dd HH:mm:ss”
     * @param isPrettyPrinting  一个转换规则标志，该标志表明：“是否要将转换的结果按照大家习惯的分行缩进方式进行排版。”
     * @param strategies
     * @return 返回转换后的Json 字符串
     */
    public static String toJsonImpl(Object obj, Class<?> adaptingType, TypeAdapter<?> adapter, String dateFormat,
                                    boolean isPrettyPrinting, ExclusionStrategy[] strategies) {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.setDateFormat(DATE_FORMAT_DEFAULT_19);

        if (isPrettyPrinting) {
            gsonBuilder.setPrettyPrinting();
        }

        if(strategies != null && strategies.length > 0) {
            gsonBuilder.setExclusionStrategies(strategies);
        }

        if(adaptingType != null && adapter != null) {
            gsonBuilder.registerTypeAdapter(adaptingType, adapter);
        }

        Gson gson = gsonBuilder.create();

        String json = gson.toJson(obj);
        return json;
    }

    /**
     * 将对象转换为json字符串。
     *
     * @param obj 需要准换的对象。
     * @param dateFormat 转换日期类型属性时使用的日期格式（JSF标准）。例如“yyyy-MM-dd HH:mm:ss”
     * @param isPrettyPrinting 一个转换规则标志，该标志表明：“是否要将转换的结果按照大家习惯的分行缩进方式进行排版。”
     * @return 返回转换后的Json 字符串
     */
    public static String toJsonIgnoreJoins(Object obj, String dateFormat,
                                           boolean isPrettyPrinting) {
        ExclusionStrategy strategy = new DefaultTypeStrategy();

        return toJsonImpl(obj, null, null, dateFormat, isPrettyPrinting, new ExclusionStrategy[]{strategy});
    }

    /**
     * 将对象转换为json字符串。
     *
     * @param obj 需要准换的对象。
     * @param dateFormat 转换日期类型属性时使用的日期格式（JSF标准）。例如“yyyy-MM-dd HH:mm:ss”
     * @param isPrettyPrinting 一个转换规则标志，该标志表明：“是否要将转换的结果按照大家习惯的分行缩进方式进行排版。”
     * @return 返回转换后的Json 字符串
     */
    public static String toJson(Object obj, Class<?> adaptingType, TypeAdapter<?> adapter, String dateFormat,
                                boolean isPrettyPrinting ) {
        return toJsonImpl(obj, adaptingType, adapter, dateFormat, isPrettyPrinting, null);
    }


    /**
     * 将Json字符串转换为指定类型的对象。如果对象（或其持有的对象）的某个属性为日期类型， 转换时将使用指定的日期格式对日期字符串进行解析。
     *
     * @param <T> 类型参数，帮助编译期对返回值类型进行检查。
     * @param json 要解析的json字符串
     * @param clazz 与本方法的类型参数一致的Class模板，共调用者指定返回值的类型
     * @param dateFormat 解析日期类型属性时使用的日期格式（JSF标准）。例如“yyyy-MM-dd HH:mm:ss”
     *
     * @return 返回解析后的对象。
     */
    public static <T> T toObject(String json, Class<T> clazz, String dateFormat) {
        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat(dateFormat);

        gsonBuilder.setDateFormat(dateFormat);

        Gson gson = gsonBuilder.create();

        T result = gson.fromJson(json, clazz);
        return result;
    }


    /**
     * 将Json字符串转换为指定类型的对象。如果对象（或其持有的对象）的某个属性为日期类型， 转换时将使用指定的日期格式对日期字符串进行解析。
     *
     * @param <T> 类型参数，帮助编译期对返回值类型进行检查。
     * @param json  要解析的json字符串
     * @param clazz 与本方法的类型参数一致的Class模板，共调用者指定返回值的类型
     * dateFormat 解析日期类型属性时使用的日期格式（JSF标准）。例如“yyyy-MM-dd HH:mm:ss”
     *
     * @return 返回解析后的对象。
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat(GSonUtils.DATE_FORMAT_DEFAULT_19);
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.setExclusionStrategies(new DefaultTypeStrategy());

        Gson gson = gsonBuilder.create();
        T result = gson.fromJson(json, clazz);
        return result;
    }
    /**
     * 将值赋值实体类
     */
    public static <T> T toObject(Map<String, Object> map, Class<T> clzz) {
        try {
            T t = clzz.newInstance();
            List<Field> fieldes = new ArrayList<Field>();
            getFields(clzz, t, fieldes);

            String fieldName = null;
            Object value = null;
            for (String key : map.keySet()) {
                for (Field field : fieldes) {
                    fieldName = field.getName();
                    try {
                        if (fieldName.toUpperCase().equals(key.toUpperCase())) {
                            value = map.get(key);
                            if (value != null && !value.getClass().equals(field.getType())) {
                                if (field.getType().getName().indexOf("Long") > -1) {
                                    value = Long.valueOf(value.toString());
                                } else if (field.getType().getName().indexOf("Double") > -1) {
                                    value = Double.valueOf(value.toString());
                                } else if (field.getType().getName().indexOf("Float") > -1) {
                                    value = Float.valueOf(value.toString());
                                } else if (field.getType().getName().indexOf("Integer") > -1) {
                                    value = Integer.valueOf(value.toString());
                                } else if (field.getType().getName().indexOf("Date") > -1) {
                                    value = dataFormat.format(value.toString());
                                } else if (field.getType().getName().indexOf("String") > -1) {
                                    value = value.toString();
                                }
                            }
                            clzz.getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), field.getType())
                                    .invoke(t, value);
                            break;
                        }
                    } catch (Exception e) {
                    }
                }
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    /**
     * 获取需要转化的属性(包括父类的属性 )
     */
    @SuppressWarnings("unchecked")
    private static void getFields(Class clzz, Object obj, List<Field> list) {
        if ("java.lang.Object".equals(clzz.getName())) {
            return;
        }
        Field []fields = clzz.getDeclaredFields();
        for (Field f : fields) {
            list.add(f);
        }
        fields = clzz.getFields();
        for (Field f : fields) {
            list.add(f);
        }
        getFields(clzz.getSuperclass(), obj, list);
    }
}
