package cn.lzy.dao.impl;

import cn.lzy.dao.SellerDao;
import cn.lzy.domain.Seller;
import cn.lzy.util.JdbcUtils_druid;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 商家SellerDao接口实现类
 */
public class SellerDaoImpl implements SellerDao {

    //声明并创建JdbcTemplate对象
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils_druid.getDataSource());

    /**
     * 根据sid查询商家信息
     * @param sid
     * @return
     */
    @Override
    public Seller findSellerBySid(int sid) {

        //定义SQL
        String sql = "select * from seller where sid = ?";

        //执行SQL
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
    }
}
