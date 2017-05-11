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
		//��webӦ�ò��ȡ��ǰ��Spring����
		ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		//getCookies �������Ի�ȡ�ͻ��˴��͵�����Cookie����
		Cookie[] cookies = request.getCookies();
		
		//��ȡcookies�е�token
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
			//�ض��򵽵�¼ҳ��
			response.sendRedirect("log_in.html");
			return;
		}
		
		UserService userService = ctx.getBean("userService",UserService.class);
		//���Cookie�е�Token�����ݿ��Token��һ�£����ض��򵽵�¼ҳ��
		if(userService.checkToken(userId,token)){
			//ִ�к���������,����ִ��edit.html��
			chain.doFilter(req, res);
		} else {
			//�������ͨ��
			response.sendRedirect("log_in.html");
		}
	}

	public void destroy() {
		
	}

}
