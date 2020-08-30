package View;

import Entity.Article;
import Entity.User;

import java.util.List;

public class HtmlGenerator {
    public static String getMessagePage(String message, String nextUrl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>");
        stringBuilder.append("<head>");
        stringBuilder.append("<meta charset=\"utf-8\">");
        stringBuilder.append("<title>提示页面</title>");
        stringBuilder.append("</head>");
        stringBuilder.append("<body>");

        stringBuilder.append("<h3 style=\"clear:both;text-align:center;\">");
        stringBuilder.append(message);
        stringBuilder.append("</h3>");

        stringBuilder.append(String.format("<a href=\"%s\" style=\"clear:both;text-align:center;\"> 点击这里进行跳转 </a>",
                nextUrl));

        stringBuilder.append("</body>");
        stringBuilder.append("</html>");

        return stringBuilder.toString();

    }
    // 按照字符串拼装的方式, 生成 html
    public static String getArticleListPage(List<Article> articles, User user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>");
        stringBuilder.append("<head>");
        stringBuilder.append("<meta charset=\"utf-8\">");
        stringBuilder.append("<title>提示页面</title>");
        stringBuilder.append("<style>");
        // style 标签内部就是写 CSS 的逻辑
        stringBuilder.append(".article {" +
                "color: #333;" +
                "text-decoration: none;" +
//                "display: inline-block;" +
                "width: 200px;" +
                "height: 50px;" +
                "}");
        stringBuilder.append(".article:hover {" +
                "color: white;" +
                "background-color: orange;" +
                "}");
        stringBuilder.append("body {" +
                "background-repeat: none;" +
                "background-position: 0 center;" +
                "}");
        stringBuilder.append("</style>");
        stringBuilder.append("</head>");
        stringBuilder.append("<body>");

        stringBuilder.append("<h3  style=\"clear:both;text-align:center; \"> 欢迎您!    " + user.getUserName() + "</h3>");
        stringBuilder.append("<hr>");
        // 要有一个文章列表. 显示每个文章的标题.
        for (Article article : articles) {
            stringBuilder.append(String.format("<div style=\"width: 200px; height: 50px; line-height: 50px\"> <a class=\"article\" href=\"article?articleId=%d\"> %s </a>" +
                            "<a href=\"deleteArticle?articleId=%d\"> 删除 </a></div>",
                    article.getArticleId(), article.getArticleTitle(), article.getArticleId()));
        }
        stringBuilder.append("<hr>");
        stringBuilder.append(String.format("<div>当前共有博客 %d 篇</div>", articles.size()));

        // 在这里新增发布文章的区域
        stringBuilder.append("<div> 发布文章 </div>");
        stringBuilder.append("<div>");
        stringBuilder.append("<form method=\"post\" action=\"article\">");
        stringBuilder.append("<input type=\"text\" style=\"width: 500px; margin-bottom: 10px;\" name=\"articleTitle\" placeholder=\"请输入标题\">");
        stringBuilder.append("<br>");
        stringBuilder.append("<textarea name=\"articleContent\" style=\"width: 500px; height: 300px;\"></textarea>");
        stringBuilder.append("<br>");
        stringBuilder.append("<input style=\"background-color: #FFA500;\" type=\"submit\" value=\"发布文章\">");
        stringBuilder.append("</form>");
        stringBuilder.append("</div>");

        stringBuilder.append("</body>");
        stringBuilder.append("</html>");
        return stringBuilder.toString();
    }


    public static String getArticleDetailPage(Article article, User user, User author) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>");
        stringBuilder.append("<head>");
        stringBuilder.append("<meta charset=\"utf-8\">");
        stringBuilder.append("<title>提示页面</title>");
        stringBuilder.append("<style>");
        // style 标签内部就是写 CSS 的逻辑
        stringBuilder.append("a {" +
                "color: #333;" +
                "text-decoration: none;" +
                "display: inline-block;" +
                "width: 200px;" +
                "height: 50px;" +
                "}");
        stringBuilder.append("a:hover {" +
                "color: white;" +
                "background-color: orange;" +
                "}");
        stringBuilder.append("body {" +
                "background-repeat: none;" +
                "background-position: 0 center;" +
                "}");
        stringBuilder.append("</style>");
        stringBuilder.append("</head>");
        stringBuilder.append("<body>");
        stringBuilder.append("<h3 style=\"clear:both;text-align:center;\"> 欢迎您! " + user.getUserName() + "</h3>");
        stringBuilder.append("<hr>");

        stringBuilder.append(String.format("<h1>%s</h1>", article.getArticleTitle()));
        stringBuilder.append(String.format("<h4>作者: %s</h4>", author.getUserName()));
        // 构造正文的地方.
        // HTML 中本来就不是用 \n 表示换行的.
        stringBuilder.append(String.format("<div>%s</div>", article.getArticleContent()
                .replace("\n", "<br>")));

        stringBuilder.append("</body>");
        stringBuilder.append("</html>");
        return stringBuilder.toString();
    }
}
