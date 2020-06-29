package com.applepieme.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
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
        // 管理员或用户登录 则不拦截
        if (req.getSession().getAttribute("userCart") != null ||
                req.getSession().getAttribute("manage") != null) {
            chain.doFilter(request, response);
            // 部分页面不拦截
        } else if ("/login.go".equals(path) || "/index.go".equals(path) || "/signup.go".equals(path)) {
            chain.doFilter(request, response);
            // 不符合条件 跳转到登录页面
        } else {
            req.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
