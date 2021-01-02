package com.jsu.Dao;

import com.jsu.Bean.Music;
import com.jsu.Bean.MusicKind;
import com.jsu.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Music_KindDao {
    /**
     * 根据id查找歌曲类型
     *
     */
    public String getName(int id){
        String kindname=null;
        Connection conn = DBUtil.getConnection();
        String sql = "select kname from music_kind where kid=?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                kindname = rs.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.closeJDBC(null, pstmt, conn);
        }
        return kindname;
    }
    public List<MusicKind> getAllKind(){
        List<MusicKind> mkl=new ArrayList<MusicKind>();
        Connection conn=DBUtil.getConnection();
        String sql = "select * from music_kind";
        int id;
        MusicKind mk = null;
        String name;
        Statement s = null;
        try {
            s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                id = rs.getInt(1);
                name=rs.getString(2);
                mk=new MusicKind(id,name);
                mkl.add(mk);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, s, conn);
        }
        return mkl;
    }
    public boolean insert(String name){
        List<MusicKind> musicKindList=getAllKind();
        for(int i=0;i<musicKindList.size();i++){
            if(name.equals(musicKindList.get(i).getName()))
                return false;
        }
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO music_kind(kid,kname) VALUES(null,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, pstmt, conn);
        }
        return true;
    }
}
