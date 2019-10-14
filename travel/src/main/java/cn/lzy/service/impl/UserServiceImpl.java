package cn.lzy.service.impl;

import cn.lzy.dao.UserDao;
import cn.lzy.dao.impl.UserDaoImpl;
import cn.lzy.domain.ResultInfo;
import cn.lzy.domain.User;
import cn.lzy.service.UserService;
import cn.lzy.util.MailUtils;
import cn.lzy.util.UuidUtil;

import java.net.URLEncoder;

/**
 * User业务逻辑接口实现类
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user  用户填写的注册信息
     * @return ResultInfo结果信息对象，用于以json形式返回给页面
     */
    @Override
    public ResultInfo regist(User user) {
        /**
         * 1.判断传入的user用户名是否已经存在
         */
        User u = userDao.findUserByUsername(user.getUsername());

        //创建ResultInfo对象，用于封装结果信息
        ResultInfo ri = new ResultInfo();
        if(u != null) {//说明该用户名已经存在，注册失败
            ri.setFlag(false);
            ri.setErrorMsg("该用户名已经存在");
        }else{//该用户名合法，注册成功
            /**
             * 2.保存用户注册信息
             */
            //2.1设置激活码
            user.setCode(UuidUtil.getUuid());
            //2.2设置激活状态
            user.setStatus("N");//尚未激活
            userDao.add(user);

            ri.setFlag(true);

            /**
             * 3.发送激活邮件
             */
            //本地访问
            String content = "恭喜，注册成功！请登录您的注册邮箱进行激活您的账号，激活后才能登录。<a href='http://192.168.255.128:8080/travel/user/active?code="+user.getCode()+"' target='_blank'>点击激活</a>";
            //外网访问（我的个人域名http://liuzhiyong.imwork.net）
//            String content = "恭喜，注册成功！请登录您的注册邮箱进行激活您的账号，激活后才能登录。<a href='http://"+"liuzhiyong.imwork.net"+"/travel/user/active?code="+user.getCode()+"' target='_blank'>点击激活</a>";
            MailUtils.sendEmail(user.getEmail(), content);

        }
        return ri;
    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //根据code查询用户
        User user = userDao.findUserByCode(code);
        if(user != null){
            //用户存在，修改该用户的激活状态status为“Y”
            userDao.updateStatus(user);
            return true;
        }else{
            //激活码对应用户不存在
            return false;
        }
    }

    /**
     * 登录
     * @param u
     * @return
     */
    @Override
    public ResultInfo login(User u) {
        //创建ResultInfo对象，用于封装结果信息
        ResultInfo ri = new ResultInfo();

        //根据登录信息，查询
        User user = userDao.findUserByUsernameAndPassword(u);
        if(user == null) {//如果根据该用户名和密码查询的用户不存在
            ri.setFlag(false);
            ri.setErrorMsg("用户名或密码错误");
        }else if("N".equals(user.getStatus())){//如果用户名存在，但是尚未激活
            ri.setFlag(false);
            ri.setErrorMsg("您尚未激活，请先去注册邮箱激活");
        }else{
            //登录成功
            ri.setFlag(true);
            ri.setResultObj(user);
        }
        return ri;
    }
}
