package xhs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

/**
 * Created by xhs on 2016/4/18.
 */
public class Test
{
    private static Logger logger = Logger.getLogger(Test.class);

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // System.out.println("This is println message.");

        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");
    }

}
