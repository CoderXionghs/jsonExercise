package util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import net.sf.ezmorph.MorphException;
import net.sf.ezmorph.object.AbstractObjectMorpher;

/**
 * json对象转java bean的过程中调用，正确处理转Timestamp对象
 * Created by xhs on 2016/4/20.
 */

public class TimestampMorpher extends AbstractObjectMorpher
{
    /*** 日期字符串格式 */
    private String[] formats;

    public TimestampMorpher(String[] formats)
    {
        this.formats = formats;
    }

    public Object morph(Object value)
    {
        if (value == null)
        {
            return null;
        }
        //如果Timestamp是value的同类或者父类
        if (Timestamp.class.isAssignableFrom(value.getClass()))
        {
            return (Timestamp) value;
        }
        if (!supports(value.getClass()))
        {
            throw new MorphException(value.getClass() + " 是不支持的类型");
        }
        String strValue = (String) value;
        SimpleDateFormat dateParser = null;
        for (int i = 0; i < formats.length; i++)
        {
            if (null == dateParser)
            {
                dateParser = new SimpleDateFormat(formats[i]);
            }
            else
            {
                dateParser.applyPattern(formats[i]);
            }
            try
            {
                return new Timestamp(dateParser.parse(strValue.toLowerCase()).getTime());
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Class morphsTo()
    {
        return Timestamp.class;   //这一句很重要
    }

    public boolean supports(Class clazz)
    {
        return String.class.isAssignableFrom(clazz);
    }

}
