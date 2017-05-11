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
 * �������е�.do����
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
		//1.��ȡcookie��userId��token
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
		//2.����һ������������Ϣ��Json�ַ���
		String json = "{\"state\":1,\"data\":null,\"message\":\"�����µ�¼��\"}";
		//��������Ϣ���ַ������͸��ͻ���
		if(token==null || userId==null){
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println(json);
			return false;
		}
		//3.��userId��token����ҵ���Ƚ�
		if(userService.checkToken(userId, token)){
			//4.���ͨ������return true,��������
			return true;
		}else{
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println(json);
			//5.�����ͨ������return false,����response����һ������������Ϣ��JSON����
			return false;
		}

	}
}
