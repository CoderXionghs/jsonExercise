package core;

import entity.Student;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import util.JsonUtils;

import java.sql.Timestamp;

/**
 * Created by xhs on 2016/4/20.
 * @author  xionghs
 */
public class MainClass
{
    private static Logger logger = Logger.getLogger(MainClass.class);
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");


        Student s = new Student();
        s.setId(123456);
        s.setName("李四");
        s.setAge(20);
        Timestamp b = Timestamp.valueOf("1992-10-19 23:52:18");//设置Timestamp、Date类型的值
        s.setTimestamp(b);
        java.util.Date utilDate=new java.util.Date();
        s.setUtilDate(utilDate);

        //含有Timestamp、Date的bean转化为Json
        JSONObject jsonObj = JsonUtils.beanToJson(s, "yyyy-MM-dd HH:mm:ss");
        System.out.println(jsonObj.toString());

        //json转为bean（含有Timestamp、Date）
        jsonObj.put("timestamp","1992-10-19");
        System.out.println(jsonObj.toString());

        Student s1 = (Student) JsonUtils.jsonToBean(jsonObj.toString(), Student.class);
        System.out.println("timestamp:"+s1.getTimestamp().toString());
        System.out.println(s1.getUtilDate().toString());
    }
}