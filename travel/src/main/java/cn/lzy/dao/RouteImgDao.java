package cn.lzy.dao;

import cn.lzy.domain.RouteImg;

import java.util.List;

/**
 * 旅游线路图片RouteImgDao接口
 */
public interface RouteImgDao {
    /**
     * 根据rid查询旅游线路图片
     * @param rid
     * @return
     */
    List<RouteImg> findImgsByRid(int rid);
}
