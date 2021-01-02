package com.jsu.Servlet;

import com.jsu.Bean.SongList;
import com.jsu.Bean.User;
import com.jsu.Dao.SongListDao;
import com.jsu.Dao.SongList_MusicDao;
import com.jsu.Dao.UserDao;
import org.apache.catalina.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/UserInfoServlet")
@MultipartConfig
public class UserInfoServlet extends HttpServlet {
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
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("SESSION_User");
        SongList_MusicDao slmd = new SongList_MusicDao();
        if ("create".equals(type)) {
            int mid = Integer.parseInt(req.getParameter("Mid"));
            SongListDao sld = new SongListDao();
            sld.insertSl(user.getId());
            List<SongList> songLists = sld.findSongList(user.getId());
            System.out.println("songLists:" + songLists.size());
            slmd.insertSM(songLists.get(0).getId(), mid);
            session.setAttribute("songList", songLists);
            out.print("true");
        } else if ("add".equals(type)) {
            int mid = Integer.parseInt(req.getParameter("Mid"));
            int slid = Integer.parseInt(req.getParameter("Slid"));
            if (slmd.isExist(slid, mid)) {
                out.print("false");
            } else {
                slmd.insertSM(slid, mid);
                out.print("true");
            }
        } else {
            UserDao dao = new UserDao();
            String newName = req.getParameter("U_name");
            // ��ȡ�ϴ��ļ���
            Part part = req.getPart("headShot");
            //�ϴ��ļ�����
            String cd = part.getHeader("Content-Disposition");
            //��ȡ��ͬ���͵��ļ���Ҫ�����ж�
            String fileName = cd.substring(cd.lastIndexOf("=") + 2, cd.length() - 1);

            // Ϊ��ֹ�ϴ��ļ����������ļ�����������
            String newFileName = "u" + user.getId()
                    + fileName.substring(fileName.lastIndexOf("."));
            // ���ϴ����ļ������ڷ�������Ŀ����·����applicant/imagesĿ¼��
            String filepath = getServletContext().getRealPath("/images");
            File f = new File(filepath);
            if (!f.exists())
                f.mkdirs();
            part.write(filepath + "/" + newFileName);
            user.setPhoto("/images/" + newFileName);
            if (!("".equals(newName) || newName == null)) {
                user.setName(newName);
                dao.updateUser(user.getPhoto(), user.getName(), user.getId());
            } else {
                dao.updateUser(user.getPhoto(), user.getId());
            }
            req.getSession().setAttribute("SESSION_User", user);
            resp.sendRedirect("User.jsp");
        }
    }
}
