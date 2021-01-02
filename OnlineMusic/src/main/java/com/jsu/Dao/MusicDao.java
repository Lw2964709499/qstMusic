package com.jsu.Dao;

import com.jsu.Bean.Music;
import com.jsu.Bean.MusicKind;
import com.jsu.Bean.SongList;
import com.jsu.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicDao {
    public List<Music> getAll(){
        Connection conn = DBUtil.getConnection();
        String sql = "select * from music";
        int id, kid, playnum, commentnum;
        List<Music> musiclist = new ArrayList<Music>();
        Music music = null;
        String mname, mphoto, singername, kname, playaddress;
        Statement s = null;
        int sum = 0;
        try {
            s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                id = rs.getInt("mid");
                singername = rs.getString("singername");
                kid = rs.getInt("kid");
                kname = new Music_KindDao().getName(kid);
                playnum = rs.getInt("playnum");
                commentnum = rs.getInt("commentnum");
                mname = rs.getString("mname");
                mphoto = rs.getString("mphoto");
                playaddress = rs.getString("playaddress");
                music = new Music(id, mname, singername,kid, kname, playaddress, playnum, commentnum, mphoto);
                musiclist.add(music);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, s, conn);
        }
        return musiclist;
    }
    /**
     * 获取歌曲
     * @param  rank
     */
    public List<Music> getSum(String rank) {
        Connection conn = DBUtil.getConnection();
        String sql;
        if (rank.equals("playnum"))
            sql = "select mid,mname,singername,kid,playnum,commentnum,mphoto,playaddress from music order by playnum desc";
        else
            sql = "select mid,mname,singername,kid,playnum,commentnum,mphoto,playaddress from music order by commentnum desc";
        int id, kid, playnum, commentnum;
        List<Music> musiclist = new ArrayList<Music>();
        Music music = null;
        String mname, mphoto, singername, kname, playaddress;
        Statement s = null;
        int sum = 0;
        try {
            s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                id = rs.getInt("mid");
                singername = rs.getString("singername");
                kid = rs.getInt("kid");
                kname = new Music_KindDao().getName(kid);
                playnum = rs.getInt("playnum");
                commentnum = rs.getInt("commentnum");
                mname = rs.getString("mname");
                mphoto = rs.getString("mphoto");
                playaddress = rs.getString("playaddress");
                music = new Music(id, mname, singername,kid, kname, playaddress, playnum, commentnum, mphoto);
                musiclist.add(music);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, s, conn);
        }
        return musiclist;
    }
    /**
     * 根据id获取音乐
     * @param id
     */
    public Music find(int id){
        Music music=null;
        Connection conn = DBUtil.getConnection();
        String sql="select * from music where mid=?";
        int mid, kid, playnum, commentnum;
        String mname, mphoto, singername, kname, playaddress;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()){
                id = rs.getInt("mid");
                singername = rs.getString("singername");
                kid = rs.getInt("kid");
                kname = new Music_KindDao().getName(kid);
                playnum = rs.getInt("playnum");
                commentnum = rs.getInt("commentnum");
                mname = rs.getString("mname");
                mphoto = rs.getString("mphoto");
                playaddress = rs.getString("playaddress");
                music = new Music(id, mname, singername,kid, kname, playaddress, playnum, commentnum, mphoto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, pstmt, conn);
        }
        return music;
    }
    public void update(int mid,int playnum,int commentnum){
        Connection conn = DBUtil.getConnection();
        String sql="update music set playnum=?,commentnum=? where mid=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, playnum);
            pstmt.setInt(2, commentnum);
            pstmt.setInt(3,mid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, pstmt, conn);
        }
    }
    public boolean isExistMusic(String musicName,String singerName){
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM music WHERE mname=? and sname=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, musicName);
            pstmt.setString(2, singerName);
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
    public void insertMusic(String musicName,String singerName,int kid,String photo,String address){
        Connection conn=DBUtil.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO music(mid,mname,singername,kid,playnum,commentnum,mphoto,playaddress) VALUES(null,?,?,?,?,?,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, musicName);
            pstmt.setString(2, singerName);
            pstmt.setInt(3, kid);
            pstmt.setInt(4, 0);
            pstmt.setInt(5, 0);
            pstmt.setString(6, photo);
            pstmt.setString(7, address);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, pstmt, conn);
        }
    }
}
