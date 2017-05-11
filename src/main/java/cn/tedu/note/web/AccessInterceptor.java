package cn.tedu.note.web;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.tedu.note.service.UserService;
@Component
/**
 * 拦截所有的.do请求
 * @author Administrator
 *
 */
public class AccessInterceptor implements HandlerInterceptor {
	@Resource
	private UserService userService;
	
	public void afterCompletion(HttpServletRequest req, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) throws Exception {
		//1.获取cookie中userId，token
		Cookie[] cookies = request.getCookies();
		String userId = null;
		String token = null;
		if(cookies != null){
			for (Cookie c : cookies) {
				if("userId".equals(c.getName())){
					userId = c.getValue();
				}
				if("token".equals(c.getName())){
					token = c.getValue();
				}
			}
		}
		//2.创建一个包含错误消息的Json字符串
		String json = "{\"state\":1,\"data\":null,\"message\":\"请重新登录！\"}";
		//将错误消息的字符串发送给客户端
		if(token==null || userId==null){
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println(json);
			return false;
		}
		//3.将userId和token交给业务层比较
		if(userService.checkToken(userId, token)){
			//4.如果通过，则return true,继续访问
			return true;
		}else{
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println(json);
			//5.如果不通过，则return false,利用response返回一个包含错误消息的JSON对象
			return false;
		}

	}
}
