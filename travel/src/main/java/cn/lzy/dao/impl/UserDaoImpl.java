package cn.lzy.dao.impl;

import cn.lzy.dao.UserDao;
import cn.lzy.domain.User;
import cn.lzy.util.JdbcUtils_druid;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * UserDao实现类
 */
public class UserDaoImpl implements UserDao {

    /**
     * 创建JdbcTemplate对象
     */
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils_druid.getDataSource());

    /**
     *根据username查询User对象
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        //定义sql
        String sql = "select * from user where username = ?";
        User user = null;
        try{
            //因为如果查询没有结果，会抛出异常，所以需要catch后返回null
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (Exception e){
//            e.printStackTrace();
        }
        return user;
    }


    /**
     * 添加User
     * @param user
     */
    @Override
    public void add(User user) {
        //定义sql
        String sql = "insert into user values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //执行sql
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
    }

    /**
     * 根据激活码查询用户
     * @param code
     * @return
     */
    @Override
    public User findUserByCode(String code) {
        //定义sql
        String sql = "select * from user where code = ?";
        //执行sql
        User user = null;
        try {
            //因为如果查询没有结果，会抛出异常，所以需要catch后返回null
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return user;
    }

    /**
     * 更改该用户的激活状态status为“Y”
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        //定义sql
        String sql = "update user set status = 'Y' where uid = ?";
        //执行sql
        jdbcTemplate.update(sql, user.getUid());
    }

    /**
     * 根据用户名和密码查询用户
     * @param u
     * @return
     */
    @Override
    public User findUserByUsernameAndPassword(User u) {
        //定义sql
        String sql = "select * from user where username = ? and password = ?";
        User user = null;
        try {
            //执行sql
            //因为如果查询没有结果，会抛出异常，所以需要catch后返回null
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), u.getUsername(), u.getPassword());
        } catch (DataAccessException e) {
            // e.printStackTrace();
        }
        return user;
    }
}
