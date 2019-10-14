package cn.lzy.dao;

import cn.lzy.domain.Route;

import java.util.List;

/**
 * 旅游线路RouteDao接口
 */
public interface RouteDao {

    /**
     * 查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    int findTotalCount(int cid, String rname);

    /**
     * 分页条件查询后的数据集合
     * @param cid
     * @param rname
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<Route> findByPage(int cid, String rname, int startIndex, int pageSize);

    /**
     * 根据rid查询旅游线路
     * @param rid
     * @return
     */
    Route findOne(int rid);

    /**
     * 改变route表的收藏次数记录
     * @param rid
     * @param i
     */
    void changeFavoriteCount(int rid, int i);

    /**
     * 查询该rid对应的收藏次数count值
     * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * 查询route表收藏量count前i的旅游路线
     * @param i
     * @return
     */
    List<Route> findTop(int i);

    /**
     * 查询收藏数量>0的旅游线路，进入收藏排行榜
     * @return
     */
    List<Route> favoriteRank();

    /**
     * 分页（条件）查询收藏排行榜（查询收藏数量>0的旅游线路，进入收藏排行榜）
     * @param rname
     * @param lowerPrice
     * @param upperPrice
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<Route> findFavoriteRankByPage(String rname, int lowerPrice, int upperPrice, int startIndex, int pageSize);

    /**
     * 查询收藏排行榜总记录数
     * @param rname
     * @param lowerPrice
     * @param upperPrice
     * @return
     */
    int findFavoriteRankTotalCount(String rname, int lowerPrice, int upperPrice);

    /**
     * 查询route表上架时间rdate近期前4的旅游路线
     * @param i
     * @return
     */
    List<Route> findNewer(int i);

    /**
     * 查询route表上架时间rdate近期，且isThemeTour=1的前4的旅游路线）
     * @param i
     * @return
     */
    List<Route> findTheme(int i);

    /**
     * 查询route表上架时间rdate近期，且前i的rname含有“特价”的旅游路线
     * @param content
     * @param i
     * @return
     */
    List<Route> findTopByContent(String content, int i);
}
