package util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.ezmorph.MorphException;
import net.sf.ezmorph.object.AbstractObjectMorpher;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.omg.CORBA.TIMEOUT;

/**
 * json对象转java bean的过程中调用，正确处理转Timestamp对象
 * Created by xhs on 2016/4/20.
 */

public class TimestampMorpher extends AbstractObjectMorpher
{
    /*** 日期字符串格式 */
    private String[] formats;
    private boolean lenient; // 设置是否严格解析，false表示严格解析，默认是严格解析
    private Locale locale;
    private Timestamp defaultValue; // 默认的日期值，表示转换失败时，默认指定什么值

    public TimestampMorpher(String[] formats)
    {
        this(formats,Locale.getDefault(),false);
    }
    public TimestampMorpher(String[] formats, Timestamp defaultValue)
    {
        this(formats,defaultValue,Locale.getDefault(),false);
    }
    public TimestampMorpher(String[] formats, boolean lenient)
    {
        this(formats,Locale.getDefault(),lenient);
    }
    public TimestampMorpher(String[] formats, Locale locale)
    {
        this(formats,locale,false);
    }

    public TimestampMorpher(String[] formats, Locale locale, boolean lenient)
    {
        if ((formats == null) || (formats.length == 0))
        {
            throw new MorphException("invalid array of formats");
        }
        this.formats = formats;
        if (locale == null)
            this.locale = Locale.getDefault();
        else
        {
            this.locale = locale;
        }
        this.lenient = lenient;
    }

    /**
     * 4个参数的构造函数
     *
     * @param formats
     * @param defaultValue 默认的java.util.Date值，如果都转化失败时，就返回这个值
     * @param locale
     * @param lenient
     */
    public TimestampMorpher(String[] formats, Timestamp defaultValue, Locale locale, boolean lenient)
    {
        super(true);// 表示采用默认值，即设置useDefault=true
        if ((formats == null) || (formats.length == 0))
        {
            throw new MorphException("invalid array of formats");
        }
        this.formats = formats;
        if (locale == null)
            this.locale = Locale.getDefault();
        else
        {
            this.locale = locale;
        }
        this.lenient = lenient;
        setDefaultValue(defaultValue);// 设置默认值
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
        for (int i = 0; i < this.formats.length; ++i)
        {
            if (dateParser == null)
            {
                dateParser = new SimpleDateFormat(this.formats[i], this.locale); // 创建格式类
            }
            else
            {
                dateParser.applyPattern(this.formats[i]); // 赋予新的格式
            }
            dateParser.setLenient(this.lenient); // 默认是false，表示严格解析
            try
            {
                return new Timestamp( dateParser.parse(strValue.toLowerCase()).getTime() ); // 才用这种格式解析失败，则抛出异常，否则直接返回成功的值
            }
            catch (ParseException localParseException)
            {
            }
        }
        // isUseDefault(),只是返回this.useDefault，如果该值为true表示设置了默认值
        if (super.isUseDefault())
        {
            return this.defaultValue;
        }
        throw new MorphException("Unable to parse the date " + value);
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof DateMorpherEx))
        {
            return false;
        }
        TimestampMorpher other = (TimestampMorpher) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.formats, other.formats);
        builder.append(this.locale, other.locale);
        builder.append(this.lenient, other.lenient);
        if ((super.isUseDefault()) && (other.isUseDefault()))
        {
            builder.append(getDefaultValue(), other.getDefaultValue());
            return builder.isEquals();
        }
        if ((!super.isUseDefault()) && (!other.isUseDefault()))
        {
            return builder.isEquals();
        }
        return false;
    }

    public int hashCode()
    {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.formats);
        builder.append(this.locale);
        builder.append(this.lenient);
        if (super.isUseDefault())
        {
            builder.append(getDefaultValue());
        }
        return builder.toHashCode();
    }

    public Timestamp getDefaultValue()
    {
        if (this.defaultValue != null)
            return (Timestamp) this.defaultValue.clone();
        else
            return this.defaultValue;
    }

    public void setDefaultValue(Timestamp defaultValue)
    {
        if (defaultValue != null)
            this.defaultValue = ((Timestamp) defaultValue.clone());
        else
            this.defaultValue = null;
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
