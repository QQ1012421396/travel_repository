package cn.lzy.dao;

import cn.lzy.domain.User;

/**
 * UserDao接口
 */
public interface UserDao {

    /**
     *根据username查询User对象
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 添加User
     * @param user
     */
    void add(User user);

    /**
     * 根据code查询用户
     * @param code
     */
    User findUserByCode(String code);

    /**
     * 修改用户激活状态
     * @param user
     */
    void updateStatus(User user);

    /**
     * 根据用户名和密码查询用户
     * @param u
     * @return
     */
    User findUserByUsernameAndPassword(User u);
}
