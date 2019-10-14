package cn.lzy.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Jdbc工具类（使用druid连接池）
 */
public class JdbcUtils_druid {
    /**
     * 1.声明静态连接池数据源成员变量
     */
    private static DataSource dataSource = null;

    /**
     * 2.创建连接池数据源对象
     */
    static{
        try {
            Properties properties = new Properties();
            InputStream is = JdbcUtils_druid.class.getClassLoader().getResourceAsStream("druid.properties");
            //加载druid配置文件输入流
            properties.load(is);
            //获取数据库连接池对象：通过工厂来来获取  DruidDataSourceFactory
            dataSource = DruidDataSourceFactory.createDataSource(properties);
            System.out.println(dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取数据源对象
     * @return
     */
    public static DataSource getDataSource(){
        return dataSource;
    }

}
