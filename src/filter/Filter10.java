package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "Filter 1",urlPatterns = {"/*"})//所有资源进行过滤
public class Filter10 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //HttpServletRequest request = (HttpServletRequest)servletRequest;
        //HttpServletResponse response = (HttpServletResponse)servletResponse;
        System.out.println("Filter 1 - encoding begins");
        //System.out.println("path：" + path + "@"+new Date());
        filterChain.doFilter(servletRequest,servletResponse);//执行其他过滤器，如过滤器已执行完毕，则执行原请求
        System.out.println("Filter 1 - encoding ends");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
