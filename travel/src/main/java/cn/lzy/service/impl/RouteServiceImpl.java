package cn.lzy.service.impl;

import cn.lzy.dao.*;
import cn.lzy.dao.impl.*;
import cn.lzy.domain.*;
import cn.lzy.service.RouteService;

import java.util.List;

/**
 * 旅游线路Route业务逻辑接口实现类
 */
public class RouteServiceImpl implements RouteService {

    //声明并创建RouteDao对象
    private RouteDao routeDao = new RouteDaoImpl();
    //声明并创建SellerDao对象
    private SellerDao sellerDao = new SellerDaoImpl();
    //声明并创建CategoryDao对象
    private CategoryDao categoryDao = new CategoryDaoImpl();
    //声明并创建RouteImgDao对象
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    //声明并创建FavoriteDao对象
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 分页数据PageBean
     * @param cid  类别id
     * @param currentPage   当前页码
     * @param pageSize    每页最大显示条数
     * @param rname     旅游线路名称
     * @return
     */
    @Override
    public PageBean<Route> pageList(int cid, int currentPage, int pageSize, String rname) {

        //1.创建PageBean对象
        PageBean<Route> routePageBean = new PageBean<Route>();

        //2.查询数据
        //2.1 查询总记录数
        int totalCount = routeDao.findTotalCount(cid, rname);
        //2.2 计算总页数
        int totalPage = (totalCount%pageSize==0)?(totalCount/pageSize):(totalCount/pageSize+1);
        //2.3 分页条件查询后的数据集合
        //2.3.1 计算分页的起始索引
        int startIndex = (currentPage-1)*pageSize;
        List<Route> dataList = routeDao.findByPage(cid, rname, startIndex, pageSize);
        //2.4 根据cid查询分类名称
        if(cid != 0){
            Category category = categoryDao.findCategoryByCid(cid);
        }

        //3.封装数据到PageBean中
        routePageBean.setCurrentPage(currentPage);//当前页
        routePageBean.setPageSize(pageSize);//每页最大显示条数
        routePageBean.setTotalCount(totalCount);//总记录数
        routePageBean.setTotalPage(totalPage);//总页数
        routePageBean.setDataList(dataList);//分页数据

        return routePageBean;
    }

    /**
     * 根据rid查询旅游线路详情
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {

        //1.查询数据
        //根据rid查询旅游线路
        Route route = routeDao.findOne(rid);
        //根据rid查询旅游线路图片
        List<RouteImg> routeImgs = routeImgDao.findImgsByRid(rid);
        //根据sid查询商家信息
        Seller seller = sellerDao.findSellerBySid(route.getSid());
        //根据cid查询分类名称
        Category category = categoryDao.findCategoryByCid(route.getCid());
        //根据rid查询对应的收藏次数count值
        int count = routeDao.findCountByRid(rid);

        //2.封装数据到Route对象中
        route.setRouteImgList(routeImgs);
        route.setSeller(seller);
        route.setCategory(category);
        route.setCOUNT(count);

        return route;
    }

    /**
     * 改变route表的收藏次数记录
     * @param rid
     * @param i
     */
    @Override
    public void changeFavoriteCount(int rid, int i) {
        routeDao.changeFavoriteCount(rid, i);
    }

    /**
     * 热门推荐（查询route表收藏量count前i的旅游路线）
     * @return
     * @param i
     */
    @Override
    public List<Route> hot(int i) {
        return routeDao.findTop(i);
    }

    /**
     * 查询收藏数量>0的旅游线路，进入收藏排行榜
     * @return
     */
    @Override
    public List<Route> favoriteRank() {
        return routeDao.favoriteRank();
    }

    /**
     *  分页（条件）查询收藏排行榜（查询收藏数量>0的旅游线路，进入收藏排行榜）
     * @param currentPage
     * @param pageSize
     * @param rname
     * @param lowerPrice
     * @param upperPrice
     * @return
     */
    @Override
    public PageBean<Route> favoriteRankByPage(int currentPage, int pageSize, String rname, int lowerPrice, int upperPrice) {
        //1.创建PageBean对象
        PageBean<Route> favoriteRankPageBean = new PageBean<Route>();

        //2.查询数据
        //2.1 查询收藏排行榜总记录数
        int totalCount = routeDao.findFavoriteRankTotalCount(rname, lowerPrice, upperPrice);
        //2.2 计算查询收藏排行榜总页数
        int totalPage = (totalCount%pageSize==0)?(totalCount/pageSize):(totalCount/pageSize+1);
        //2.3 计算分页的起始索引
        int startIndex = (currentPage-1)*pageSize;
        //2.4调用service方法，分页（条件）查询收藏排行榜（查询收藏数量>0的旅游线路，进入收藏排行榜）
        List<Route> dataList = routeDao.findFavoriteRankByPage(rname, lowerPrice, upperPrice, startIndex, pageSize);

        //3.封装数据到PageBean中
        favoriteRankPageBean.setTotalCount(totalCount);//总记录数
        favoriteRankPageBean.setTotalPage(totalPage);//总页数
        favoriteRankPageBean.setCurrentPage(currentPage);//当前页
        favoriteRankPageBean.setPageSize(pageSize);//每页最大显示条数
        favoriteRankPageBean.setDataList(dataList);//分页数据

        return favoriteRankPageBean;
    }

    /**
     * 查询route表上架时间rdate近期前4的旅游路线
     * @param i
     * @return
     */
    @Override
    public List<Route> newer(int i) {
        return routeDao.findNewer(i);
    }

    /**
     * 查询route表上架时间rdate近期，且isThemeTour=1的前4的旅游路线）
     * @param i
     * @return
     */
    @Override
    public List<Route> theme(int i) {
        return routeDao.findTheme(i);
    }

    /**
     * 查询route表上架时间rdate近期，且前i的rname含有“特价”的旅游路线
     * @param content
     * @param i
     * @return
     */
    @Override
    public List<Route> lowPriceTravel(String content, int i) {
        return routeDao.findTopByContent(content, i);
    }

}
