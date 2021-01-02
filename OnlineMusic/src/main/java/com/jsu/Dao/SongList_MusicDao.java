package com.jsu.Dao;

import com.jsu.Bean.Music;
import com.jsu.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongList_MusicDao {
    /**
     * 查找相应歌单的歌曲
     *
     * @param songListId
     * @return
     */
    public List<Music> find(int songListId) {
        List<Music> musicList = new ArrayList<Music>();
        Connection conn = DBUtil.getConnection();
        MusicDao mdao = new MusicDao();
        String sql = "select mid from songlist_music where slid=?";
        int mid, kid, playnum, commentnum;
        String mname, mphoto, singername, kname, playaddress;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int i=0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, songListId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                i++;
                mid = rs.getInt("mid");
                Music music = mdao.find(mid);
                musicList.add(music);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, pstmt, conn);
        }
        return musicList;
    }
    public void insertSM(int slid,int mid){
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO songlist_music(slmid,slid,mid) VALUES(null,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,slid);
            pstmt.setInt(2,mid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, pstmt, conn);
        }
    }
    public boolean isExist(int slid,int mid){
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM songlist_music WHERE slid=? and mid=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, slid);
            pstmt.setInt(2, mid);
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
}
