package util;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

/**
 * Created by xhs on 2016/4/20.
 */

public class JsonUtils
{

    /**
 * 从json字符串转换成java bean，能够正确处理java.util.Date,java.sql.timeStamp类型
 *
 * @param json
 * @param cls
 * @return
 */
public static Object jsonToBean(String json, Class cls)
{
    String[] formats = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" };
    //如果json串中，对应的字段代表的是timestamp类型，就调用下面这个函数
    JSONUtils.getMorpherRegistry().registerMorpher(new TimestampMorpher(formats));

    //注册java.Util.Date的转换函数，如果转换失败，则返回一个null对象
    JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpherEx(formats,(java.util.Date)null));

    JSONObject jsonObject = JSONObject.fromObject(json);
    return JSONObject.toBean(jsonObject, cls);
}


    /**
     * 从json字符串转换成java bean，能够正确处理java.util.Date,java.sql.timeStamp类型
     *
     * @param json
     * @param cls
     * @return
     */
    public static Object jsonToBean(String json, Class cls,Map mapcls)
    {
        String[] formats = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" };
        //如果json串中，对应的字段代表的是timestamp类型，就调用下面这个函数
        JSONUtils.getMorpherRegistry().registerMorpher(new TimestampMorpher(formats));

        //注册java.Util.Date的转换函数，如果转换失败，则返回一个null对象
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpherEx(formats,(java.util.Date)null));

        JSONObject jsonObject = JSONObject.fromObject(json);
        return JSONObject.toBean(jsonObject, cls, mapcls );
    }
    public static JSONObject beanToJson(Object obj, String dateFormat)
    {
        JsonConfig config = new JsonConfig();
        // 如果遇到Timestamp类型，就调用自定义的JsonDateValueProcessor转换函数
        config.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor(dateFormat));
        config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor(dateFormat));
        JSONObject json = JSONObject.fromObject(obj, config);
        return json;
    }

}
