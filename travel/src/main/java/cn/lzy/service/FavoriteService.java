package cn.lzy.service;

/**
 * 收藏业务逻辑接口
 */
public interface FavoriteService {

    /**
     * 判断用户是否收藏该rid的旅游线路
     * @param rid
     * @param uid
     * @return
     */
    Boolean isFavorite(int rid, int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void addFavorite(int rid, int uid);
}
