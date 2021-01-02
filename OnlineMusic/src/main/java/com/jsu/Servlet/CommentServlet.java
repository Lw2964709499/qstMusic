package com.jsu.Servlet;

import com.jsu.Bean.Music;
import com.jsu.Bean.User;
import com.jsu.Dao.CommentDao;
import com.jsu.Dao.MusicDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out= resp.getWriter();
        Music music=(Music) req.getServletContext().getAttribute("NowMusic");
        MusicDao mdao=new MusicDao();
        String type=req.getParameter("type");
        if("addComment".equals(type)){
            music.setCommnentNum(music.getCommnentNum()+1);
            mdao.update(music.getId(), music.getPlaynum(), music.getCommnentNum());
            req.getServletContext().setAttribute("NowMusic", music);
            int mid = music.getId();
            String cDesc = (String) req.getParameter("description");
            User user = (User) req.getSession().getAttribute("SESSION_User");
            CommentDao cdao = new CommentDao();
            cdao.save(cDesc, mid, user.getId());
            out.print(music.getCommnentNum());
        }
    }
}
