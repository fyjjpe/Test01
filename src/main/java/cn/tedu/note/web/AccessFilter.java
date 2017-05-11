package cn.tedu.note.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.tedu.note.service.UserService;


public class AccessFilter implements Filter {

	WebApplicationContext ctx;
	
	public void init(FilterConfig cfg) throws ServletException {
		ServletContext sc = cfg.getServletContext();
		//在web应用层获取当前的Spring容器
		ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		//getCookies 方法可以获取客户端传送的所有Cookie对象
		Cookie[] cookies = request.getCookies();
		
		//获取cookies中的token
		String token = null;
		String userId = null;
		if(cookies!=null){
			for(Cookie c : cookies){
//				System.out.println(c.getName()+":"+c.getValue());
				if("token".equals(c.getName())){
					token = c.getValue();
				}
				if("userId".equals(c.getName())){
					userId = c.getValue();
				}
			}
		}
		if(token==null){
			//重定向到登录页面
			response.sendRedirect("log_in.html");
			return;
		}
		
		UserService userService = ctx.getBean("userService",UserService.class);
		//如果Cookie中的Token与数据库的Token不一致，则重定向到登录页面
		if(userService.checkToken(userId,token)){
			//执行后续的请求,继续执行edit.html了
			chain.doFilter(req, res);
		} else {
			//检查结果不通过
			response.sendRedirect("log_in.html");
		}
	}

	public void destroy() {
		
	}

}
