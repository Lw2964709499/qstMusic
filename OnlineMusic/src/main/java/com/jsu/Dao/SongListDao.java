package com.jsu.Dao;

import com.jsu.Bean.Music;
import com.jsu.Bean.SongList;
import com.jsu.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongListDao {
    /**
     * 查找用户的歌单
     *
     * @param userId
     * @return
     */
    public List<SongList> findSongList(int userId) {
        List<SongList> songList = new ArrayList<SongList>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select slid,slname from songlist where uid=?";
        int id;
        String name;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2);
                SongList sl = new SongList(id, name, userId);
                songList.add(sl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, pstmt, conn);
        }
        return songList;
    }

    public void insertSl(int uid) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO songlist(slid,slname,uid) VALUES(null,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "我喜欢");
            pstmt.setInt(2, uid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, pstmt, conn);
        }
    }

    public boolean isExsitSl(int uid, String name){
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM songlist WHERE slname=? and uid=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, uid);
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
    public boolean insertSl(int uid, String name) {
        List<SongList> songList=findSongList(uid);
        for(int i=0;i<songList.size();i++){
            if(name.equals(songList.get(i).getName()))
                return false;
        }
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO songlist(slid,slname,uid) VALUES(null,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, uid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, pstmt, conn);
        }
        return true;
    }

}
