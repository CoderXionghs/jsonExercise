package core;

import org.apache.log4j.Logger;

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
    }
}