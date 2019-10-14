package cn.lzy.web.servlet;

import cn.lzy.domain.ResultInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * 完成分发
 * 其它子类Servlet继承父类BaseServlet，通过反射，调用子类Servlet中的方法
 */
public class BaseServlet extends GenericServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;


        //获取请求路径，比如http://localhost/travel/user/login
        String requestURL = request.getRequestURL().toString();

        //获取请求中对应的方法名，比如login
        String methodName = requestURL.substring(requestURL.lastIndexOf("/") + 1);

        try {
            //获取调用对象对应的方法
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //执行方法
            method.invoke(this, request, response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接将传入的对象序列化为json，并且写回客户端
     * @param obj
     * @param response
     */
    public void writeValue(Object obj, HttpServletResponse response) {
        try {
            ObjectMapper om = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            om.writeValue(response.getOutputStream(), obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
