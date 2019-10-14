package cn.lzy.service;

import cn.lzy.domain.PageBean;
import cn.lzy.domain.Route;

import java.util.List;

/**
 * 旅游线路Route业务逻辑接口
 */
public interface RouteService {

    /**
     * 分页数据PageBean
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    PageBean<Route> pageList(int cid, int currentPage, int pageSize, String rname);

    /**
     * 根据rid查询旅游线路详情
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
     * 热门推荐（查询route表收藏量count前i的旅游路线）
     * @return
     * @param i
     */
    List<Route> hot(int i);

    /**
     * 查询收藏数量>0的旅游线路，进入收藏排行榜
     * @return
     */
    List<Route> favoriteRank();

    /**
     * 分页（条件）查询收藏排行榜（查询收藏数量>0的旅游线路，进入收藏排行榜）
     * @param currentPage
     * @param pageSize
     * @param rname
     * @param lowerPrice
     * @param upperPrice
     * @return
     */
    PageBean<Route> favoriteRankByPage(int currentPage, int pageSize, String rname, int lowerPrice, int upperPrice);

    /**
     * 查询route表上架时间rdate近期前4的旅游路线
     * @param i
     * @return
     */
    List<Route> newer(int i);

    /**
     * 查询route表上架时间rdate近期，且isThemeTour=1的前4的旅游路线）
     * @param i
     * @return
     */
    List<Route> theme(int i);

    /**
     * 查询route表上架时间rdate近期，且前i的rname含有“特价”的旅游路线
     *
     * @param content
     * @param i
     * @return
     */
    List<Route> lowPriceTravel(String content, int i);
}
