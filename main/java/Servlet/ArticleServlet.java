package Servlet;

import Dao.ArticleDao;
import Dao.UserDao;
import Entity.Article;
import Entity.User;


import View.HtmlGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ArticleServlet extends HttpServlet {

    //获取文章列表 获取文章详细内容
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=utf-8");
        // 1. 判定用户的登陆状态. 如果用户尚未登陆, 就要提示用户来登陆.
        HttpSession httpSession = req.getSession(false);
        if (httpSession == null) {
            String html = HtmlGenerator.getMessagePage("您尚未登陆",
                    "login.html");
            resp.getWriter().write(html);
            return;
        }
        User user = (User) httpSession.getAttribute("user");
        // 2. 判断请求中是否存在 articleId 参数.
        String articleIdStr = req.getParameter("articleId");
        if (articleIdStr == null) {
            //  a) 没有这个参数就去执行获取文章列表操作articleDao中查找文章列表不需要id
            getAllArticleList(user, resp);

        } else {
            //  b) 有这个参数就去执行获取文章详情操作.
            getOneArticle(Integer.parseInt(articleIdStr), user, resp);

        }
    }
        private void getAllArticleList(User user, HttpServletResponse resp) throws IOException  {
            // 1. 查找数据库
            ArticleDao articleDao = new ArticleDao();
            List<Article> articleList = articleDao.findArticleList();
            // 2. 构造页面
            String html = HtmlGenerator.getArticleListPage(articleList, user);
            resp.getWriter().write(html);


        }
        private void getOneArticle(int articleId, User user, HttpServletResponse resp) throws IOException {
            // 1. 查找数据库
            ArticleDao articleDao = new ArticleDao();
            Article article = articleDao.findArticleByArticleId(articleId);
            if (article == null) {
                //文章未找到
                String html = HtmlGenerator.getMessagePage("文章不存在",
                        "article");
                resp.getWriter().write(html);
                return;
            }
            // 2. 根据作者id 找到作者信息, 进一步得到作者姓名
            UserDao userDao = new UserDao();
            User author = userDao.findUserById(article.getUserId());
            // 3. 构造页面
            String html = HtmlGenerator.getArticleDetailPage(article, user, author);
            resp.getWriter().write(html);

        }

    //实现新增文章
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=utf-8");
        // 1. 判定用户的登陆状态. 如果用户尚未登陆, 就要提示用户来登陆.
        HttpSession httpSession = req.getSession(false);
        if (httpSession == null) {
            String html = HtmlGenerator.getMessagePage("您尚未登陆",
                    "login.html");
            resp.getWriter().write(html);
            return;
        }
        User user = (User) httpSession.getAttribute("user");

        // 2. 从请求中读取浏览器提交的数据(title, content), 并进行简单校验
        String title = req.getParameter("articleTitle");
        String content = req.getParameter("articleContent");
        if (title == null || "".equals(title)
                || content == null || "".equals(content)) {
            String html = HtmlGenerator.getMessagePage("提交的标题或者正文为空!",
                    "article");
            resp.getWriter().write(html);
            return;
        }
        // 3. 把数据插入到数据库中.
        ArticleDao articleDao = new ArticleDao();
        Article article = new Article();
        article.setArticleTitle(title);
        article.setArticleContent(content);
        article.setUserId(user.getUserId());
        articleDao.add(article);
        // 4. 返回一个插入成功的页面.
        String html = HtmlGenerator.getMessagePage("发布成功！","article");
        resp.getWriter().write(html);
        return;
    }
}
