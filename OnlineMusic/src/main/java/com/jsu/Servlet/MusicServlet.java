package com.jsu.Servlet;

import com.jsu.Bean.Music;
import com.jsu.Dao.MusicDao;
import com.jsu.Dao.Music_KindDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/MusicServlet")
@MultipartConfig
public class MusicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String type = req.getParameter("type");
        MusicDao md=new MusicDao();
        if("add".equals(type)){
            String musicName = req.getParameter("musicName");
            String singerName =req.getParameter("singerName");
            String kindType = req.getParameter("kindType");
            Part musicAddress = req.getPart("musicFile");
            if(md.isExistMusic(musicName,singerName)||"AL".equals(kindType)||musicAddress==null){
                resp.sendRedirect("Admin.jsp");
            }else {
                int kind=Integer.parseInt(kindType);
                // ��ȡ�ϴ��ļ���
                Part partPhoto = req.getPart("musicPhoto");
                //�ϴ��ļ�����
                String cd = partPhoto.getHeader("Content-Disposition");
                String cd1 = musicAddress.getHeader("Content-Disposition");
                //��ȡ��ͬ���͵��ļ���Ҫ�����ж�
                String photoName = cd.substring(cd.lastIndexOf("=") + 2, cd.length() - 1);
                String musicFileName = cd1.substring(cd.lastIndexOf("=") + 1, cd1.length() - 1);
                // Ϊ��ֹ�ϴ��ļ����������ļ�����������
                String newPhotoName = System.currentTimeMillis()
                        + photoName.substring(photoName.lastIndexOf("."));
                String newMusicFile = System.currentTimeMillis()
                        + musicFileName.substring(musicFileName.lastIndexOf("."));
                // ���ϴ����ļ������ڷ�������Ŀ����·����/imagesĿ¼��
                String photopath = getServletContext().getRealPath("/images");
                String musicpath = getServletContext().getRealPath("/music");
                md.insertMusic(musicName,singerName,kind,"/images/"+newPhotoName,"/music/"+newMusicFile);
                File f = new File(photopath);
                if (!f.exists())
                    f.mkdirs();
                File f1 = new File(musicpath);
                if (!f1.exists())
                    f1.mkdirs();
                partPhoto.write(photopath + "/" + newPhotoName);
                musicAddress.write(musicpath + "/" + newMusicFile);
                resp.sendRedirect("Admin.jsp");
            }
        }else if("playAjaxValidate".equals(type)){
            Music music=(Music) req.getServletContext().getAttribute("NowMusic");
            MusicDao mdao=new MusicDao();
            music.setPlaynum(music.getPlaynum()+1);
            int s= music.getPlaynum();
            mdao.update(music.getId(), music.getPlaynum(), music.getCommnentNum());
            req.getServletContext().setAttribute("NowMusic", music);
            out.print(music.getPlaynum());
        }
    }

}
