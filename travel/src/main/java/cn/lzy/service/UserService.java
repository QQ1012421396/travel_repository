package cn.lzy.service;

import cn.lzy.domain.ResultInfo;
import cn.lzy.domain.User;

/**
 * User业务逻辑接口
 */
public interface UserService {


    /**
     * 注册用户
     * @param user  用户填写的注册信息
     * @return  ResultInfo结果信息对象，用于以json形式返回给页面
     */
    ResultInfo regist(User user);

    /**
     * 激活用户
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 登录
     * @param user
     * @return
     */
    ResultInfo login(User user);
}
