package com.applepieme.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限拦截器
 * 未登录的部分操作无法进行
 *
 * @author applepieme@yeah.net
 * @date 2020/6/29 11:13
 */
@WebFilter("*.go")
public class PowerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        String path = req.getServletPath();
        if (req.getSession().getAttribute("userCart") != null ||
                req.getSession().getAttribute("manage") != null) {
            chain.doFilter(request, response);
        } else if ("/login.go".equals(path)) {
            chain.doFilter(request, response);
        } else {
            req.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
