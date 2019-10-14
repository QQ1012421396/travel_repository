package cn.lzy.dao.impl;

import cn.lzy.dao.CategoryDao;
import cn.lzy.domain.Category;
import cn.lzy.util.JdbcUtils_druid;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * CategoryDao接口实现类
 */
public class CategoryDaoImpl implements CategoryDao {

    /**
     * 声明并创建JdbcTemplate对象
     */
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils_druid.getDataSource());

    /**
     * 查询所有分类
     * @return
     */
    @Override
    public List<Category> findAll() {

        //定义sql
        String sql = "select * from category";
        //执行sql
        List<Category> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        Collections.sort(list, new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                return o1.getCid()-o2.getCid();//升序排序
            }
        });

        return list;
    }

    /**
     * 根据cid查询分类名称
     * @param cid
     * @return
     */
    @Override
    public Category findCategoryByCid(int cid) {
        //定义SQL
        String sql = "select * from category where cid = ?";
        //执行SQL
        Category category = null;
        try{
            category = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Category>(Category.class), cid);
        }catch (Exception e){

        }
        return category;
    }
}
