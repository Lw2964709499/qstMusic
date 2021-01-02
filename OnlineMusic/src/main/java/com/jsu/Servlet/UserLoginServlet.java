package com.jsu.Servlet;

import com.jsu.Bean.User;
import com.jsu.Dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
    public UserLoginServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String username = req.getParameter("username");
        UserDao dao = new UserDao();
        // ��¼��֤
        User user = (User) dao.login(email, password);
        if (user != null) {
            if(user.getAuthority()==1)
                resp.sendRedirect("Admin.jsp");
            else {
                req.getSession().setAttribute("SESSION_User", user);
                resp.sendRedirect("index.jsp");
            }
        } else {
            // �û���¼��Ϣ���󣬽��д�����ʾ
            out.print("<script type='text/javascript'>");
            out.print("alert('�û���������������������룡');");
            out.print("window.location='login.jsp';");
            out.print("</script>");
        }
    }
}
