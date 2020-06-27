package com.applepieme.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 字符编码拦截器
 * 给每个页面设置字符编码
 *
 * @author applepieme@yeah.net
 * @date 2020/6/27 20:39
 */
public class EncodingFilter implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (encoding != null) {
            request.setCharacterEncoding(encoding);
            response.setContentType("text/html; charset = " + encoding);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        this.encoding = null;
    }
}
