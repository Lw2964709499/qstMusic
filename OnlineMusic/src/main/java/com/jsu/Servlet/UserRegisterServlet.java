package com.jsu.Servlet;

import com.jsu.Dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out=resp.getWriter();
        String email=req.getParameter("email");
        String type = req.getParameter("type");
        UserDao dao = new UserDao();
        if("emailAjaxValidate".equals(type)){

            boolean flag = dao.isExistEmail(email);
            if(flag)
                out.print("邮箱已被注册！");
            else
                out.print("邮箱可以使用！");
        }else {
            String password = req.getParameter("password");
            String username = req.getParameter("username");
            if (dao.isExistEmail(email)) {
                out.print("<script type='text/javascript'>");
                out.print("alert('邮箱已被注册，请重新输入！');");
                out.print("window.location='register.jsp';");
                out.print("</script>");
            } else {
                dao.save(email, password, username);
                resp.sendRedirect("login.jsp");
            }
        }
    }
}
