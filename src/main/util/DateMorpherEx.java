package util;

import net.sf.ezmorph.object.AbstractObjectMorpher;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.ezmorph.MorphException;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 修改当Date字段的值为空时，抛出异常的问题，当值为空时，转换成null, 调用方法如下，必须是两个参数的哦 DateMorpherEx(formats,(Date)
 * null));//调用DateMorpherEx，defaultValue为null Created by xhs on 2016/4/20.
 */

public class DateMorpherEx extends AbstractObjectMorpher
{
    private Date defaultValue; // 默认的日期值，表示转换失败时，默认指定什么值
    private String[] formats; // 格式字符串数组
    private boolean lenient; // 设置是否严格解析，false表示严格解析，默认是严格解析
    private Locale locale;

    public DateMorpherEx(String[] formats)
    {
        this(formats,Locale.getDefault(),false);
    }

    public DateMorpherEx(String[] formats, boolean lenient)
    {
        this(formats,Locale.getDefault(),lenient);
    }

    public DateMorpherEx(String[] formats, Date defaultValue)
    {
        this(formats,defaultValue,Locale.getDefault(),false);
    }

    /**
     * 4个参数的构造函数
     * 
     * @param formats
     * @param defaultValue 默认的java.util.Date值，如果都转化失败时，就返回这个值
     * @param locale
     * @param lenient
     */
    public DateMorpherEx(String[] formats, Date defaultValue, Locale locale, boolean lenient)
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

    public DateMorpherEx(String[] formats, Locale locale)
    {
        this(formats,locale,false);
    }

    public DateMorpherEx(String[] formats, Locale locale, boolean lenient)
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
        DateMorpherEx other = (DateMorpherEx) obj;
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

    public Object morph(Object value)
    {
        if (value == null)
        {
            return null;
        }
        if (Date.class.isAssignableFrom(value.getClass()))
        {
            return (Date) value;
        }
        if (!supports(value.getClass())) // value是否是一个字符串类型
        {
            throw new MorphException(value.getClass() + " is not supported");
        }
        String strValue = (String) value; // 转换成String类型
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
                return dateParser.parse(strValue.toLowerCase()); // 才用这种格式解析失败，则抛出异常，否则直接返回成功的值
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

    public Class morphsTo()
    {
        return java.util.Date.class;
    }

    /**
     * 这个方法相对于原来的是修改了的
     * 如果从json串中，采用指定的所有格式转换都失败了，切指定了默认值，就调用这个函数，返回默认值
     * @return
     */
    public Date getDefaultValue()
    {
        if (this.defaultValue != null)
            return (Date) this.defaultValue.clone();
        else
            return this.defaultValue;
    }

    /**
     * 这个方法相对于原来的是修改了的
     * 
     * @return
     */
    public void setDefaultValue(Date defaultValue)
    {
        if (defaultValue != null)
            this.defaultValue = ((Date) defaultValue.clone());
        else
            this.defaultValue = null;
    }

    public boolean supports(Class clazz)
    {
        return String.class.isAssignableFrom(clazz);
    }
}
