package Servlet;

import Dao.UserDao;
import Entity.User;
import View.HtmlGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=utf-8");
        String name = req.getParameter("userName");
        String password = req.getParameter("password");
        // 1. 获取到前端提交的数据(用户名, 密码), 校验是否合法.
        if(name == null|| "".equals(name) || password == null|| "".equals(password)){
            String html = HtmlGenerator.getMessagePage("用户名或者密码为空！",
                    "register.html");
            resp.getWriter().write(html);
            return;
        }

        // 2. 拿着用户名在数据库中查一下, 看看当前用户名是否已经存在. 如果存在, 认为注册失败(用户名不能重复)
        UserDao userDao = new UserDao();
        User existUser = userDao.findUserByName(name);

        if(existUser != null){
            String html = HtmlGenerator.getMessagePage("用户名重复！","register.html");
            resp.getWriter().write(html);
            return;
        }
        // 3. 根据前端提交的数据, 构造 User 对象并插入到数据库中.
        User user = new User();
        user.setUserName(name);
        user.setPassword(password);
        userDao.add(user);

        // 4. 返回一个结果页面, 提示当前注册成功.
        String html = HtmlGenerator.getMessagePage("注册成功！",
                "login.html");
        resp.getWriter().write(html);

    }
}
