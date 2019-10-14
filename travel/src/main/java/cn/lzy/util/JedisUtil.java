package cn.lzy.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Jedis工具类
 */
public class JedisUtil {

    //声明JedisPool连接池对象
    private static JedisPool jedisPool;

    static{
        //读取配置文件
        InputStream is = JedisUtil.class.getClassLoader().getResourceAsStream("jedis.properties");
        //创建Properties对象
        Properties pro = new Properties();
        //关联文件
        try {
            pro.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取数据，设置到JedisPoolConfig配置对象中
        JedisPoolConfig jpc = new JedisPoolConfig();
        jpc.setMaxTotal(Integer.parseInt(pro.getProperty("maxTotal")));
        jpc.setMaxIdle(Integer.parseInt(pro.getProperty("maxIdle")));

        //初始化JedisPool连接池对象
        jedisPool = new JedisPool(jpc, pro.getProperty("host"), Integer.parseInt(pro.getProperty("port")));
    }

    /**
     * 获取Jedis连接对象
     * @return
     */
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

    /**
     *关闭Jedis连接对象
     */
    public static void close(Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }
}
