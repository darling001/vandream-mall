package com.vandream.mall.commons.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * @Author Oakley(fangchao)
 * @Date 2018-01-11 15:32
 */
@Component
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type, x-requested-with, X-Custom-Header, Authorization,text/html; charset=utf-8,application/json");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
