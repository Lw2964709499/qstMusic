package com.jsu.Dao;

import com.jsu.Bean.User;
import com.jsu.util.DBUtil;

import java.sql.*;
import java.util.Date;

public class UserDao {
    /**
     * 根据id查找用户名字
     * @param id
     * @return
     */
    public String find(int id){
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String username=null;
        String sql = "SELECT uname FROM user WHERE uid=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next())
               username=rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, pstmt, conn);
        }
        return username;
    }
    /**
     * 验证Email是否已被注册
     *
     * @return
     */

    public boolean isExistEmail(String email) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM user WHERE email=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, pstmt, conn);
        }
        return false;
    }

    /**
     * 计算user表里有多少条数据
     *
     */
    public int getSum(){
        Connection conn = DBUtil.getConnection();
        String sql = "select count(*) from user";
        Statement s = null;
        int sum = 0;
        try {
            s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                sum = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.closeJDBC(null, s, conn);
        }
        return sum;
    }




    /**
     * 注册用户登录
     *
     * @param email
     * @param password
     * @return
     */
    public User login(String email, String password) {
        int userID = 0;
        String name;
        int authority;
        String photo;
        User user=null;
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM user WHERE email='"+email+"' and password='"+password+"'";
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                userID = rs.getInt("uid");
                name=rs.getString("uname");
                authority=rs.getInt("authority");
                photo=rs.getString("uphoto");
                user=new User(userID,name,email,password,authority,photo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, pstmt, conn);
        }
        return user;
    }
    /**
     * 用户信息注册保存
     *
     * @param email
     * @param password
     */
    public void save(String email, String password,String username) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO user(uid,uname,email,password,authority,uphoto) VALUES(null,?,?,?,?,null)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setInt(4,2);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, pstmt, conn);
        }
    }
    public void updateUser(String photo,int uid){
        Connection conn = DBUtil.getConnection();
        String sql="update user set uphoto=? where uid=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, photo);
            pstmt.setInt(2,uid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, pstmt, conn);
        }
    }
    public void updateUser(String photo,String name,int uid){
        Connection conn = DBUtil.getConnection();
        String sql="update user set uname=?,uphoto=? where uid=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, photo);
            pstmt.setInt(3,uid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, pstmt, conn);
        }
    }
}
