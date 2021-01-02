package com.jsu.Servlet;

import com.jsu.Dao.Music_KindDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/MusicKindServlet")
public class MusicKindServlet extends HttpServlet {
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
        Music_KindDao mkd=new Music_KindDao();
        if("add".equals(type)){
            String musicKind=req.getParameter("musicKind");
            boolean flag=mkd.insert(musicKind);
            if(flag)
                out.print("true");
            else
                out.print(false);
        }
    }
}
