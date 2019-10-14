package cn.lzy.dao.impl;

import cn.lzy.dao.FavoriteDao;
import cn.lzy.domain.Favorite;
import cn.lzy.util.JdbcUtils_druid;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 收藏表FavoriteDao接口实现类
 */
public class FavoriteDaoImpl implements FavoriteDao {

    /**
     * 声明并创建JdbcTemplate对象
     */
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils_druid.getDataSource());

    /**
     * 判断用户是否收藏该rid的旅游线路
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Boolean isFavorite(int rid, int uid) {

        //定义SQL
        String sql = "select * from favorite where rid = ? and uid = ?";

        List<Favorite> list = null;
        //执行SQL
        list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);

        return list.size()>0?true:false;
    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void addFavorite(int rid, int uid) {
        //定义SQL
        String sql = "insert into favorite values(?, ?, ?)";

        //时间格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //执行SQL
        jdbcTemplate.update(sql, rid, sdf.format(new Date()), uid);
    }
}
