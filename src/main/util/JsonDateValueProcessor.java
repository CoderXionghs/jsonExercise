package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * javaBean to json的处理类，当从JavaBean 转json对象时，调用这个函数 Created by xhs on 2016/4/20.
 */
public class JsonDateValueProcessor implements JsonValueProcessor
{
    public static final String Default_DATE_PATTERN ="yyyy-MM-dd HH:mm:ss";
    private DateFormat sdf;

    public JsonDateValueProcessor(String datePattern)
    {
        try
        {
            sdf = new SimpleDateFormat(datePattern);  //这句代码本省是不会抛出异常的，所以多余
        }
        catch (Exception e)
        {
            sdf = new SimpleDateFormat(Default_DATE_PATTERN);//采用默认的格式
        }
    }

    public Object processArrayValue(Object value, JsonConfig config)
    {
        return process(value);
    }

    public Object processObjectValue(String key, Object value, JsonConfig config)
    {
        return process(value);
    }

    private Object process(Object value)
    {
        if (value instanceof java.util.Date) // 判断是否是util.Date的同类或者子类
        {
            return sdf.format(value);
        }
        return value == null ? "" : value.toString();
    }
}
