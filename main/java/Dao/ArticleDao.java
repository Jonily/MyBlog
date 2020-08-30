package Dao;

import DButil.DButil;
import Entity.Article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDao {
    //1、新增文章（发布博客）
    public static void add(Article article){
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        String sql = "insert into article values (null,?,?,?)";
        try{
            connection = DButil.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,article.getArticleTitle());
            preparedStatement.setString(2,article.getArticleContent());
            preparedStatement.setInt(3,article.getUserId());

            int ret = preparedStatement.executeUpdate();
            if(ret != 1){
                System.out.println("文章添加失败！");
                return;
            }else {
                System.out.println("文章添加成功！");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.getClose(connection,preparedStatement,null);
        }
    }
    //2、查看文章列表（把所有文章信息都查出来，不查正文）\
    public List<Article> findArticleList(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Article> articleList = new ArrayList<>();


        String sql = "select articleId,articleTitle,userId from article";
        try{
            connection = DButil.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
             Article article = new Article();
             article.setArticleId(resultSet.getInt("articleId"));
             article.setArticleTitle(resultSet.getString("articleTitle"));
             article.setUserId(resultSet.getInt("userId"));

             articleList.add(article);

            }
            return articleList;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.getClose(connection,preparedStatement,resultSet);
        }
        return null;
    }
    //3、根据文章id查看指定文章详情(查看正文)
    public Article findArticleByArticleId(int articleId){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        String sql = "select * from article where articleId=?";
        try{
            connection = DButil.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,articleId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                Article article = new Article();
                article.setArticleId(resultSet.getInt("articleId"));
                article.setArticleTitle(resultSet.getString("articleTitle"));
                article.setArticleContent(resultSet.getString("articleContent"));
                article.setUserId(resultSet.getInt("userId"));
                return article;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.getClose(connection,preparedStatement,resultSet);
        }
        return null;

    }

    //4、删除指定文章（给定文章ID删除）
    public void  deleteArticle(int articleId){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql ="delete from article where articleId =?";
        try{
            connection = DButil.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,articleId);
            int ret = preparedStatement.executeUpdate();
            if(ret != 1){
                System.out.println("删除文章失败！");
                return;
            }else {
                System.out.println("删除文章成功！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.getClose(connection,preparedStatement,null);
        }
    }

   /*public static void main(String[] args) {
        ArticleDao articleDao = new ArticleDao();
        Article article = new Article();
        article.setArticleTitle("爱你就像爱生命");
        article.setArticleContent("一想到你我这张丑脸就泛起微笑！");
        article.setUserId(1);
        articleDao.add(article);
       *//* System.out.println(findArticleList());
        System.out.println(findArticleByArticleId(4));*//*
    }
*/
}
