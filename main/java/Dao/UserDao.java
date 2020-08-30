package Dao;

import DButil.DButil;
import Entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    //新增用户（注册）
    public void add(User user){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = "insert into user values (null ,?,?)";

        try {
            connection = DButil.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setString(2,user.getPassword());

            int ret = preparedStatement.executeUpdate();
            if (ret != 1) {
                System.out.println("插入新用户失败!");
                return;
            }
            System.out.println("插入新用户成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.getClose(connection,preparedStatement,null);
        }
    }

    //按照名字查找用户（登录）
    public User findUserByName(String name){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql="select * from user where userName=?";
        try{
            assert connection != null;
            connection = DButil.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("password"));
                return user;


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.getClose(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //通过id查找用户
    public User findUserById(int id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql = "select * from user where userId = ?";
        try{
            connection = DButil.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("password"));
                return user;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.getClose(connection,preparedStatement,resultSet);
        }
        return null;

    }

   /* public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUserName("hhh");
        user.setPassword("123");

        userDao.add(user);

        System.out.println(findUserByName("hhh"));
        System.out.println(findUserById(1));
    }*/

}
