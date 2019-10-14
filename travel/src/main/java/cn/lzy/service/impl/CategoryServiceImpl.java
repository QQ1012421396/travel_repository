package cn.lzy.service.impl;

import cn.lzy.dao.CategoryDao;
import cn.lzy.dao.impl.CategoryDaoImpl;
import cn.lzy.domain.Category;
import cn.lzy.service.CategoryService;
import cn.lzy.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Category业务逻辑接口实现类
 */
public class CategoryServiceImpl implements CategoryService {

    //声明并创建CategoryDao对象
    private CategoryDao categoryDao = new CategoryDaoImpl();

    /**
     * 查询所有分类
     * @return
     */
    @Override
    public List<Category> findAll() {
        Jedis jedis = null;
        List<Category> list = null;
        try{
            //获取Jedis客户端对象
            jedis = JedisUtil.getJedis();
            //使用sortedset有序集合。查询sortedset中的分数(cid)和值(cname)
            Set<Tuple> category_redis = jedis.zrangeWithScores("CATEGORY_REDIS", 0, -1);

            //先判断redis中的集合是否为空
            if(category_redis != null && category_redis.size() != 0){

//            System.out.println("从redis中查询。。。。。。。。。。。。。");
                list = new ArrayList<Category>();
                //如果缓存中存在，则直接从缓存中查询
                for (Tuple tuple : category_redis) {
                    list.add(new Category((int)tuple.getScore(), tuple.getElement()));
                }

            }else{

//            System.out.println("从数据库中查询。。。。。。。。。。。。。");
                //如果缓存中不存在，则直接从从数据库中查询，并将查询后的结果存入redis缓存中
                list = categoryDao.findAll();
                for (Category category : list) {
                    jedis.zadd("CATEGORY_REDIS", category.getCid(), category.getCname());
                }
            }
        }catch (Exception e){

        }finally{
            //关闭Jedis
            JedisUtil.close(jedis);
        }

        return list;
    }
}
