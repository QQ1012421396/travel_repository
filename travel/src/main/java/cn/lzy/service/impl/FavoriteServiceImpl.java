package cn.lzy.service.impl;

import cn.lzy.dao.FavoriteDao;
import cn.lzy.dao.impl.FavoriteDaoImpl;
import cn.lzy.service.FavoriteService;

/**
 * FavoriteService业务逻辑接口实现类
 */
public class FavoriteServiceImpl implements FavoriteService {

    //声明并创建RouteDao对象
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 判断用户是否收藏该rid的旅游线路
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Boolean isFavorite(int rid, int uid) {

        return favoriteDao.isFavorite(rid, uid);
    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void addFavorite(int rid, int uid) {
        favoriteDao.addFavorite(rid, uid);

    }
}
