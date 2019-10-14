package cn.lzy.dao.impl;

import cn.lzy.dao.RouteImgDao;
import cn.lzy.domain.RouteImg;
import cn.lzy.util.JdbcUtils_druid;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * 旅游线路图片RouteImgDao接口实现类
 */
public class RouteImgDaoImpl implements RouteImgDao {

    //声明并创建JdbcTemplate对象
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils_druid.getDataSource());

    /**
     * 根据rid查询旅游线路图片
     * @param rid
     * @return
     */
    @Override
    public List<RouteImg> findImgsByRid(int rid) {
        //定义SQL
        String sql = "select * from route_img where rid = ?";
        //执行SQL
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
    }
}
