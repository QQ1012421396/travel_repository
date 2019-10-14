package cn.lzy.dao;

import cn.lzy.domain.Category;

import java.util.List;

/**
 * CategoryDao接口
 */
public interface CategoryDao {

    /**
     * 查询所有分类
     * @return
     */
    List<Category> findAll();

    /**
     * 根据cid查询分类名称
     * @param cid
     * @return
     */
    Category findCategoryByCid(int cid);
}
