package cn.lzy.web.servlet;

import cn.lzy.domain.ResultInfo;
import cn.lzy.domain.User;
import cn.lzy.service.UserService;
import cn.lzy.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    //声明UserService业务对象
    private UserService userService = new UserServiceImpl();

    /**
     * 注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置request编码格式
        request.setCharacterEncoding("utf-8");
        /**
         * 1.校验验证码
         */
        //1.1获取页面传递的验证码
        String checkCode = request.getParameter("checkCode");
        //1.2获取session中的验证码
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");//清除session中的验证码，验证码只能用一次。
        //1.3进行验证码的比对
        //注意，下面checkcode_server == null加入这个条件是因为，假如访问页面存在，但是服务器重启了，session中没有值为null,用户直接提交，就会报空指针异常，所以加上这个，这种访问情况一般不存在，呵呵
        if(checkCode=="" || checkcode_server == null || !checkcode_server.equalsIgnoreCase(checkCode)){//验证码不正确
            //创建结果信息对象
            ResultInfo ri = new ResultInfo(false, "验证码错误");

            //转成json对象，并响应给客户端
            writeValue(ri, response);
            return;//不再往下执行
        }

        /**
         * 2.验证码正确，开始验证用户名
         */
        //创建User对象，用于封装页面注册信息
        User user = new User();
        try {
            BeanUtils.populate(user, request.getParameterMap());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service中的方法，注册
        ResultInfo ri = userService.regist(user);//注册，并返回结果信息

        //转成json对象，并响应给客户端
        writeValue(ri, response);

    }

    /**
     * 激活
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取激活码
        String code = request.getParameter("code");

        //调用service中的方法，根据激活码，设置对应用户的激活状态为“Y”
        boolean flag = userService.active(code);

        String msg = null;
        //如果激活成功，跳转到登录页面
        if(flag){
            msg = "激活成功，请<a href='"+ request.getContextPath() +"/login.html'>登录</a>";//这里使用的是绝对路径
        }else{
            //如果激活失败，给出提示信息
            msg = "激活失败，请联系管理员";
        }

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
    }

    /**
     * 登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置request编码格式
        request.setCharacterEncoding("utf-8");
        /**
         * 1.校验验证码
         */
        //1.1获取页面传递的验证码
        String checkCode = request.getParameter("checkCode");
        //1.2获取session中的验证码
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");//清除session中的验证码，验证码只能用一次。
        //1.3 获取请求参数：是否记住用户名和密码
        String remember_login = request.getParameter("remember_login");
        //1.3进行验证码的比对
        //注意，下面checkcode_server == null加入这个条件是因为，假如访问页面存在，但是服务器重启了，session中没有值为null,用户直接提交，就会报空指针异常，所以加上这个，这种访问情况一般不存在，呵呵
        if(checkCode=="" || checkcode_server == null || !checkcode_server.equalsIgnoreCase(checkCode)){//验证码不正确
            //创建结果信息对象
            ResultInfo ri = new ResultInfo(false, "验证码错误");

            //转成json对象，并响应给客户端
            writeValue(ri, response);
            return;//不再往下执行
        }

        /**
         * 2.验证码正确，开始验证用户名和密码
         */
        //创建User对象，用于封装页面登录信息
        User user = new User();
        try {
            BeanUtils.populate(user, request.getParameterMap());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service中的方法，登录
        ResultInfo ri = userService.login(user);//登录，并返回结果信息

        //登录成功，则将当前用户信息存入session
        if(ri.isFlag()){
            request.getSession().setAttribute("USER_SESSION", (User)ri.getResultObj());

            if(remember_login != null){//页面勾选了“记住密码”，用户需要记住用户名和密码，则将用户名和密码放入cookie中
                Cookie cookie1 = new Cookie("username", user.getUsername());
                Cookie cookie2 = new Cookie("password", user.getPassword());
                cookie1.setMaxAge(60*60*24*30*6);//设置有效期半年
                cookie2.setMaxAge(60*60*24*7);//设置有效期一周
                cookie1.setPath(request.getContextPath());
                cookie2.setPath(request.getContextPath());
                response.addCookie(cookie1);//发送cookie到浏览器端保存
                response.addCookie(cookie2);//发送cookie到浏览器端保存
            }else{//页面没有勾选“记住密码”，删除密码Cookie(但是用户名Cookie保留)
                Cookie cookie = new Cookie("password", null);
                cookie.setPath(request.getContextPath());
                cookie.setMaxAge(0);//设置cookie的有效时间，表示删除同名的cookie数据。
                response.addCookie(cookie);//发送cookie到浏览器端保存
            }
        }

        //转成json对象，并响应给客户端
        writeValue(ri, response);
    }

    /**
     * 退出
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //注销当前session
        request.getSession().invalidate();//销毁session

        //跳转到登录页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * 当前登录用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void currentLoginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //创建结果信息对象
        ResultInfo ri = new ResultInfo();

        //查询session中的登录用户
        Object user_session = request.getSession().getAttribute("USER_SESSION");
        if(user_session == null){
            //如果尚未用户登录
            ri.setFlag(false);
        }else{
            //有用户登录
            ri.setFlag(true);
            ri.setResultObj(user_session);
        }

        //转成json对象，并响应给客户端
        writeValue(ri, response);
    }
}
