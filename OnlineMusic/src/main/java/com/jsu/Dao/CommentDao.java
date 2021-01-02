package com.jsu.Dao;

import com.jsu.Bean.Comment;
import com.jsu.Bean.Music;
import com.jsu.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {
    /*
    根据歌名id查找评论
    @param id
     */
    public List<Comment> find(int id){
        Connection conn = DBUtil.getConnection();
        String sql="select * from comment where mid=?";
        int cid,uid;
        String description, userName;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Comment> commentList=new ArrayList<Comment>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()){
                cid = rs.getInt("cid");
                description = rs.getString("cdes");
                uid= rs.getInt("uid");
                userName = new UserDao().find(uid);
                Comment cm=new Comment(cid,description,id,userName);
                commentList.add(cm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, pstmt, conn);
        }
        return commentList;
    }

    public void save(String description,int musicId,int userId){
        Connection conn = DBUtil.getConnection();
        String sql="insert into comment(cid,cdes,mid,uid) values(null,?,?,?)";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,description);
            pstmt.setInt(2, musicId);
            pstmt.setInt(3, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, pstmt, conn);
        }
    }
}
