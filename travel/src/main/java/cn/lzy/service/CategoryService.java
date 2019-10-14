package cn.lzy.service;

import cn.lzy.domain.Category;

import java.util.List;

/**
 * Category业务逻辑接口
 */
public interface CategoryService {

    /**
     * 查询所有分类
     * @return
     */
    List<Category> findAll();
}
