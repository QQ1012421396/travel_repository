package cn.lzy.web.servlet;

import cn.lzy.domain.PageBean;
import cn.lzy.domain.ResultInfo;
import cn.lzy.domain.Route;
import cn.lzy.domain.User;
import cn.lzy.service.FavoriteService;
import cn.lzy.service.RouteService;
import cn.lzy.service.UserService;
import cn.lzy.service.impl.FavoriteServiceImpl;
import cn.lzy.service.impl.RouteServiceImpl;
import cn.lzy.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    //创建RouteService业务逻辑对象
    private RouteService routeService = new RouteServiceImpl();
    //创建FavoriteService业务逻辑对象
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    //创建UserService业务逻辑对象
    private UserService userService = new UserServiceImpl();

    /**
     * 分页查询旅游线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收参数
        //获取页面传递参数Category的cid
        String cidStr = request.getParameter("cid");
        //获取页面传递的参数：当前页码currentPage
        String currentPageStr = request.getParameter("currentPage");
        //获取页面传递的参数：每页最大显示条数pageSize
        String pageSizeStr = request.getParameter("pageSize");
        //获取页面传递的参数：线路名称rname
        String rname = request.getParameter("rname");

        //2.处理参数
        //类别id
        int cid = 0;
        if(cidStr != null && !cidStr.isEmpty()){//页面有按分类查询，则更改
            cid = Integer.parseInt(cidStr);
        }
        //当前页currentPage
        int currentPage = 1;//默认显示第1页
        if(currentPageStr !=null && !currentPageStr.isEmpty()){//页面有传递当前页，则更改
            currentPage = Integer.parseInt(currentPageStr);
        }
        //每页最大显示条数pageSize
        int pageSize = 5;//默认每页最大显示5条数据
        if(pageSizeStr !=null && !pageSizeStr.isEmpty()){//页面有传递每页最大显示条数，则更改
            pageSize = Integer.parseInt(pageSizeStr);
        }
        //处理线路名称rname
        if(rname != null && !rname.isEmpty() && !"null".equals(rname)){
            //解码
            rname = URLDecoder.decode(rname, "utf-8");
            rname = rname.trim();//去掉两端空格
        }

        //3.调用service中的分页查询方法获取分页数据PageBean
        PageBean<Route> routePageBean = routeService.pageList(cid, currentPage, pageSize, rname);

        //4.将pageBean 转成json对象，并响应给客户端
        writeValue(routePageBean, response);
    }

    /**
     * 查询旅游线路详情
     * @param request
     * @param response
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response){
        //1.接收参数
        //获取页面传递参数rid
        String ridStr = request.getParameter("rid");

        //2.处理参数
        int rid = 0;
        if(ridStr!=null && !ridStr.isEmpty()){
            rid = Integer.parseInt(ridStr);
        }

        //3.调用service中的方法根据rid查询旅游线路详情
        Route route = routeService.findOne(rid);

        //4.将route 转成json对象，并响应给客户端
        writeValue(route, response);
    }

    /**
     * 查询该旅游线路是否被当前用户收藏
     * @param request
     * @param response
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response){
        //1.接收参数
        //获取页面传递参数rid
        String ridStr = request.getParameter("rid");

        //2.处理参数
        int rid = 0;
        if(ridStr!=null && !ridStr.isEmpty()){
            rid = Integer.parseInt(ridStr);
        }

        //3.调用service中的方法根据rid查询该旅游线路是否被当前用户收藏
        //创建结果信息对象
        ResultInfo ri = new ResultInfo();
        //判断当前是否有用户登录
        User user = (User)request.getSession().getAttribute("USER_SESSION");
        if(user != null) {//如果有用户登录
            //判断当前登录用户是否已经收藏该rid的旅游线路
            Boolean flag = favoriteService.isFavorite(rid, user.getUid());
            if(flag){//该用户已收藏
                ri.setFlag(true);
            }else{//该用户未收藏
                ri.setFlag(false);
            }
        }else{//如果用户尚未登录
            ri.setFlag(false);
        }

        //4.将route 转成json对象，并响应给客户端
        writeValue(ri, response);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response){
        //1.接收参数
        //获取页面传递参数rid
        String ridStr = request.getParameter("rid");

        //2.处理参数
        int rid = 0;
        if(ridStr!=null && !ridStr.isEmpty()){
            rid = Integer.parseInt(ridStr);
        }

        //3.调用service中的方法，添加收藏
        //创建结果信息对象
        ResultInfo ri = new ResultInfo();
        //判断当前是否有用户登录
        User user = (User)request.getSession().getAttribute("USER_SESSION");
        if(user != null) {//如果有用户登录
            //添加收藏
            favoriteService.addFavorite(rid, user.getUid());
            //route表的收藏次数记录+1
            routeService.changeFavoriteCount(rid, 1);
            ri.setFlag(true);
        }else{//如果用户尚未登录
            ri.setFlag(false);
        }

        //4.将route 转成json对象，并响应给客户端
        writeValue(ri, response);
    }


    /**
     * 热门推荐（查询route表收藏量count前5的旅游路线）
     * @param request
     * @param response
     */
    public void hot(HttpServletRequest request, HttpServletResponse response){

        //调用service中的方法，查询热门推荐（前5）
        List<Route> hotList = routeService.hot(5);

        //将route 转成json对象，并响应给客户端
        writeValue(hotList, response);
    }

    /**
     * 查询收藏数量>0的旅游线路，进入收藏排行榜
     * @param request
     * @param response
     */
    public void favoriteRank(HttpServletRequest request, HttpServletResponse response){
        //调用service中的方法，查询收藏排行榜（查询收藏数量>0的旅游线路，进入收藏排行榜）
        List<Route> hotList = routeService.favoriteRank();

        //将route 转成json对象，并响应给客户端
        writeValue(hotList, response);
    }

    /**
     * 分页查询收藏数量>0的旅游线路，进入收藏排行榜
     * @param request
     * @param response
     */
    public void favoriteRankByPage(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        //1.接收参数
        //获取页面传递的参数：当前页码currentPage
        String currentPageStr = request.getParameter("currentPage");
        //获取页面传递的参数：每页最大显示条数pageSize
        String pageSizeStr = request.getParameter("pageSize");
        //获取页面传递的参数：线路名称rname
        String rname = request.getParameter("rname");
        //获取页面传递的参数：线路金额下限lowerPrice
        String lowerPriceStr = request.getParameter("lowerPrice");
        //获取页面传递的参数：线路金额上限upperPrice
        String upperPriceStr = request.getParameter("upperPrice");

        //2.处理参数
        //当前页currentPage
        int currentPage = 1;//默认显示第1页
        if(currentPageStr !=null && !currentPageStr.isEmpty()){//页面有传递当前页，则更改
            currentPage = Integer.parseInt(currentPageStr);
        }
        //每页最大显示条数pageSize
        int pageSize = 8;//默认每页最大显示8条数据
        if(pageSizeStr !=null && !pageSizeStr.isEmpty()){//页面有传递每页最大显示条数，则更改
            pageSize = Integer.parseInt(pageSizeStr);
        }
        //处理线路名称rname
        if(rname != null && !rname.isEmpty() && !"null".equals(rname)){
            rname = rname.trim();//去掉两端空格
        }
        //线路金额下限lowerPrice
        int lowerPrice = 0;
        if(lowerPriceStr !=null && !lowerPriceStr.isEmpty() && !"null".equals(lowerPriceStr)){//页面有传递金额下限值，则更改
            lowerPrice = Integer.parseInt(lowerPriceStr);
        }
        //线路金额下限lowerPrice
        int upperPrice = 0;
        if(upperPriceStr !=null && !upperPriceStr.isEmpty() && !"null".equals(upperPriceStr)){//页面有传递金额上限值，则更改
            upperPrice = Integer.parseInt(upperPriceStr);
        }
        //比较lowerPrice和upperPrice的值，因为用户可能输入的下限大于上限，调换过来（因为SQL语句中between and语法要求）
//        if(lowerPrice > upperPrice){
//            int temp = 0;
//            temp = lowerPrice;
//            lowerPrice = upperPrice;
//            upperPrice = temp;
//        }

        //调用service中的方法，分页（条件）查询收藏排行榜（查询收藏数量>0的旅游线路，进入收藏排行榜）
        PageBean<Route> pageBean = routeService.favoriteRankByPage(currentPage, pageSize, rname, lowerPrice, upperPrice);

        //将route 转成json对象，并响应给客户端
        writeValue(pageBean, response);
    }

    /**
     * 人气旅游（查询route表收藏量count前4的旅游路线）
     * @param request
     * @param response
     */
    public void popularTravel(HttpServletRequest request, HttpServletResponse response){

        //调用service中的方法，（查询route表收藏量count前4的旅游路线）
        List<Route> popularList = routeService.hot(4);

        //将route 转成json对象，并响应给客户端
        writeValue(popularList, response);
    }

    /**
     * 最新旅游（查询route表上架时间rdate近期前4的旅游路线）
     * @param request
     * @param response
     */
    public void newerTravel(HttpServletRequest request, HttpServletResponse response){

        //调用service中的方法，查询route表上架时间rdate近期前4的旅游路线
        List<Route> newerList = routeService.newer(4);

        //将route 转成json对象，并响应给客户端
        writeValue(newerList, response);
    }

    /**
     * 主题旅游（查询route表上架时间rdate近期，且isThemeTour=1的前4的旅游路线））
     * @param request
     * @param response
     */
    public void themeTravel(HttpServletRequest request, HttpServletResponse response){

        //调用service中的方法，查询route表上架时间rdate近期，且isThemeTour=1的前4的旅游路线）
        List<Route> themeList = routeService.theme(4);

        //将route 转成json对象，并响应给客户端
        writeValue(themeList, response);
    }


    /**
     * 国内游（发送ajax请求加载数据，查询route表上架时间rdate近期，且前6的旅游路线）
     * @param request
     * @param response
     */
    public void homeTravel(HttpServletRequest request, HttpServletResponse response){

        //调用service中的方法，查询route表上架时间rdate近期前6的旅游路线
        List<Route> newerList = routeService.newer(6);

        //将route 转成json对象，并响应给客户端
        writeValue(newerList, response);
    }

    /**
     * 特价游（发送ajax请求加载数据，查询route表上架时间rdate近期，且前6的rname含有“特价”的旅游路线）
     * @param request
     * @param response
     */
    public void lowPriceTravel(HttpServletRequest request, HttpServletResponse response){

        //调用service中的方法，查询route表上架时间rdate近期，且前6的rname含有“特价”的旅游路线
        List<Route> newerList = routeService.lowPriceTravel("特价", 6);

        //将route 转成json对象，并响应给客户端
        writeValue(newerList, response);
    }



}
