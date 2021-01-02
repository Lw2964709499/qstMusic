package com.jsu.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebFilter(
        urlPatterns = {"/User.jsp","/Admin.jsp"},
        initParams = {@WebInitParam(name="loginPage",value = "login.jsp")},
        dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD ,DispatcherType.ASYNC}
)
public class UserAuthorityFilter implements Filter {
    private String loginPage = "login.jsp";
    public UserAuthorityFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        loginPage = filterConfig.getInitParameter("loginPage");
        if (null == loginPage) {
            loginPage = "login.jsp";
        }

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpSession session=(HttpSession) ((HttpServletRequest)servletRequest).getSession();
        String requsetPath=((HttpServletRequest)servletRequest).getServletPath();

        if(session.getAttribute("SESSION_User")!=null){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            session.setAttribute("Tip","ÇëÏÈµÇÂ¼");
            ((HttpServletResponse)servletResponse).sendRedirect(loginPage);
        }
    }

    @Override
    public void destroy() {
        this.loginPage = null;
    }
}
