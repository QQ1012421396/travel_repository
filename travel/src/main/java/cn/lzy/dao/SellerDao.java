package cn.lzy.dao;

import cn.lzy.domain.Seller;

/**
 * 商家SellerDao接口
 */
public interface SellerDao {

    /**
     * 根据sid查询商家信息
     * @param sid
     * @return
     */
    Seller findSellerBySid(int sid);
}
