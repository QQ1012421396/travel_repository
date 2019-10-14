package cn.lzy.dao.impl;

import cn.lzy.dao.RouteDao;
import cn.lzy.domain.Route;
import cn.lzy.util.JdbcUtils_druid;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 旅游线路RouteDao接口实现类
 */
public class RouteDaoImpl implements RouteDao {

    //声明并创建JdbcTemplate
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils_druid.getDataSource());

    /**
     *查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public int findTotalCount(int cid, String rname) {
        //定义SQL
        String sql = "SELECT COUNT(*) FROM route WHERE 1=1";
        //参数
        List<Object> parameters = new ArrayList<Object>();
        //拼接SQL
        StringBuilder sb = new StringBuilder(sql);

        if(cid != 0){
            sb.append(" AND cid = ?");
            parameters.add(cid);
        }


        if(rname!=null && !rname.isEmpty() && !"null".equals(rname)){
//        if(rname!=null && !rname.isEmpty() && !rname.equals("null")){//√
//        if(rname!=null && !rname.isEmpty() && rname!="null"){//这个会出错。
            sb.append(" AND rname LIKE ?");
            parameters.add("%" + rname + "%");
        }
        //执行SQL
        return jdbcTemplate.queryForObject(sb.toString(), parameters.toArray(), Integer.class);
    }

    /**
     *分页条件查询后的数据集合
     * @param cid
     * @param rname
     * @param startIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, String rname, int startIndex, int pageSize) {
        //定义SQL
        String sql = "SELECT * FROM route WHERE 1=1";
        //参数
        List<Object> parameters = new ArrayList<Object>();
        //拼接SQL
        StringBuilder sb = new StringBuilder(sql);

        if(cid != 0){
            sb.append(" AND cid = ?");
            parameters.add(cid);
        }


        if(rname!=null && !rname.isEmpty() && !"null".equals(rname)){
//        if(rname!=null && !rname.isEmpty() && !rname.equals("null")){//√
//        if(rname!=null && !rname.isEmpty() && rname!="null"){//这个会出错
            sb.append(" AND rname LIKE ?");
            parameters.add("%" + rname + "%");
        }

        sb.append(" LIMIT ?, ?");
        parameters.add(startIndex);
        parameters.add(pageSize);

        //执行SQL
        return jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class), parameters.toArray());
    }

    /**
     * 根据rid查询旅游线路
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {
        //定义SQL
        String sql = "select * from route where rid = ?";

        Route route = null;
        //执行SQL
        try {
            route = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        }catch (Exception e){

        }

        return route;
    }

    /**
     * 改变route表的收藏次数记录
     * @param rid
     * @param i
     */
    @Override
    public void changeFavoriteCount(int rid, int i) {
        //定义SQL
        String sql = "update route set count = ? where rid = ?";
        //查询该rid对应的收藏次数count值
        int count = findCountByRid(rid);
        //执行SQL
        jdbcTemplate.update(sql, count+i, rid);
    }

    /**
     * 查询该rid对应的收藏次数count值
     * @param rid
     * @return
     */
    @Override
    public int findCountByRid(int rid) {
        //定义SQL
        String sql = "select * from route where rid = ?";
        //执行SQL
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid).getCOUNT();
    }

    /**
     * 查询route表收藏量count前i的旅游路线
     * @return
     */
    @Override
    public List<Route> findTop(int i) {
        //定义SQL
        String sql = "SELECT * FROM route ORDER BY COUNT DESC LIMIT 0,?";

        //执行SQL
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), i);
    }

    /**
     * 查询收藏数量>0的旅游线路，进入收藏排行榜
     * @return
     */
    @Override
    public List<Route> favoriteRank() {
        //定义SQL
        String sql = "SELECT * FROM route WHERE COUNT > 0 ORDER BY COUNT DESC";
        //执行SQL
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
    }

    /**
     * 分页（条件）查询收藏排行榜（查询收藏数量>0的旅游线路，进入收藏排行榜）
     * @param rname
     * @param lowerPrice
     * @param upperPrice
     * @param startIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<Route> findFavoriteRankByPage(String rname, int lowerPrice, int upperPrice, int startIndex, int pageSize) {
        //定义SQL
        String sql = "SELECT * FROM route WHERE 1=1";
        //参数
        List<Object> parameters = new ArrayList<Object>();
        //拼接SQL
        StringBuilder sb = new StringBuilder(sql);

        //如果有rname参数
        if(rname!=null && !rname.isEmpty() && !"null".equals(rname)){
            sb.append(" AND rname LIKE ?");
            parameters.add("%" + rname + "%");
        }

        //如果只有下限金额参数输入
        if(lowerPrice > 0 && upperPrice == 0){
            sb.append(" AND PRICE >= ?");
            parameters.add(lowerPrice);
        }
        //如果只有上限金额参数输入
        if(upperPrice > 0 && lowerPrice == 0){
            sb.append(" AND PRICE <= ?");
            parameters.add(upperPrice);
        }
        //如果下限金额和上限金额参数都有输入
        if(lowerPrice > 0 && upperPrice > 0){
            sb.append(" AND PRICE BETWEEN ? AND ?");
            parameters.add(lowerPrice);
            parameters.add(upperPrice);
        }

        sb.append(" AND COUNT > 0 ORDER BY COUNT DESC LIMIT ?, ?");
        parameters.add(startIndex);
        parameters.add(pageSize);

        //执行SQL
        return jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class), parameters.toArray());
    }

    /**
     * 查询收藏排行榜总记录数
     * @param rname
     * @param lowerPrice
     * @param upperPrice
     * @return
     */
    @Override
    public int findFavoriteRankTotalCount(String rname, int lowerPrice, int upperPrice) {
        //定义SQL
        String sql = "SELECT COUNT(*) FROM route WHERE 1=1";
        //参数
        List<Object> parameters = new ArrayList<Object>();
        //拼接SQL
        StringBuilder sb = new StringBuilder(sql);

        //如果有rname参数
        if(rname!=null && !rname.isEmpty() && !"null".equals(rname)){
            sb.append(" AND rname LIKE ?");
            parameters.add("%" + rname + "%");
        }

        //如果只有下限金额参数输入
        if(lowerPrice > 0 && upperPrice == 0){
            sb.append(" AND PRICE >= ?");
            parameters.add(lowerPrice);
        }
        //如果只有上限金额参数输入
        if(upperPrice > 0 && lowerPrice == 0){
            sb.append(" AND PRICE <= ?");
            parameters.add(upperPrice);
        }
        //如果下限金额和上限金额参数都有输入
        if(lowerPrice > 0 && upperPrice > 0){
            sb.append(" AND PRICE BETWEEN ? AND ?");
            parameters.add(lowerPrice);
            parameters.add(upperPrice);
        }

        sb.append(" AND COUNT > 0");

        //执行SQL
        return jdbcTemplate.queryForObject(sb.toString(), parameters.toArray(), Integer.class);
    }

    /**
     * 查询route表上架时间rdate近期前4的旅游路线
     * @param i
     * @return
     */
    @Override
    public List<Route> findNewer(int i) {
        //定义SQL
        String sql = "SELECT * FROM route ORDER BY rdate DESC LIMIT 0,?";

        //执行SQL
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), i);
    }

    /**
     * 查询route表上架时间rdate近期，且isThemeTour=1的前4的旅游路线）
     * @param i
     * @return
     */
    @Override
    public List<Route> findTheme(int i) {
        //定义SQL
        String sql = "SELECT * FROM route where isThemeTour=1 ORDER BY rdate DESC LIMIT 0,?";

        //执行SQL
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), i);
    }

    /**
     * 查询route表上架时间rdate近期，且前i的rname含有“特价”的旅游路线
     * @param content
     * @param i
     * @return
     */
    @Override
    public List<Route> findTopByContent(String content, int i) {
        //定义SQL
        String sql = "SELECT * FROM route where rname like ? ORDER BY rdate DESC LIMIT 0,?";

        //执行SQL
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), "%"+content+"%", i);
    }
}
