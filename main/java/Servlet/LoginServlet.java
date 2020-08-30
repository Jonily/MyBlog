package Servlet;

import Dao.UserDao;
import Entity.User;
import View.HtmlGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=utf-8");
        String name = req.getParameter("userName");
        String password = req.getParameter("password");
        // 1. 获取到用户名和密码. 并进行简单校验
        if(name == null || "".equals(name) || password == null || "".equals(password)){
            String html = HtmlGenerator.getMessagePage("用户名或者密码为空！","login.html");
            resp.getWriter().write(html);
            return;
        }
        // 2. 数据库中查找, 看用户是否存在.
        // 3. 对比密码是否匹配

        UserDao userDao = new UserDao();
        User user = userDao.findUserByName(name);
        if(user == null || !password.equals(user.getPassword())){
            String html = HtmlGenerator.getMessagePage("用户名或者密码错误！","login.html");
            resp.getWriter().write(html);
            return;
        }
        // 4. 匹配成功则认为登陆成功, 创建一个 Session
        HttpSession httpSession = req.getSession(true);
        httpSession.setAttribute("user",user);
        // 5. 返回一个登陆成功的提示页面
        String html = HtmlGenerator.getMessagePage("登陆成功!",
                "article");
        resp.getWriter().write(html);

    }
}
