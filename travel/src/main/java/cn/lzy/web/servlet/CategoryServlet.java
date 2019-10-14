package cn.lzy.web.servlet;

import cn.lzy.domain.Category;
import cn.lzy.domain.ResultInfo;
import cn.lzy.domain.User;
import cn.lzy.service.CategoryService;
import cn.lzy.service.UserService;
import cn.lzy.service.impl.CategoryServiceImpl;
import cn.lzy.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    //创建CategoryService业务对象
    private CategoryService categoryService = new CategoryServiceImpl();

    /**
     * 查询所有
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //查询所有分类
        List<Category> list = categoryService.findAll();

        //转成json对象，并响应给客户端
        writeValue(list, response);
    }
}
