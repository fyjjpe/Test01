package cn.tedu.note.web;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.note.entity.User;
import cn.tedu.note.exception.NameException;
import cn.tedu.note.exception.PasswordException;
import cn.tedu.note.service.UserService;
import cn.tedu.note.util.JsonResult;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/login.do")
	@ResponseBody
	public Object login(String name,String password,HttpServletResponse res){
		//�������ҳɹ���¼���û����һ��token����ֱֹ�Ӵӱ༭ҳ�����
		//��Cookie�ӷ������·����ͻ���
		Cookie cookie = new Cookie("token","12345");
		cookie.setPath("/");
		
		User user = userService.login(name, password);
		//���û����е�token�·����������
		cookie.setValue(user.getToken());
		
		res.addCookie(cookie);
		
		//���˴�д:return user;�˴����ص���{id:123,name:tom,password:123....}
		//��������,��������˺Ŵ���ʱ,�޷�����Json����
		//дһ��JsonResult�� ��װ���ص���Ϣ״̬�����ݡ��쳣��Ϣ
		return new JsonResult(user);
	}
	
	@RequestMapping("/regist.do")
	@ResponseBody
	public JsonResult regist(String name,String password,String confirm,String nick){
		User user = userService.regist(name, nick, password, confirm);
		return new JsonResult(user);
	}
	
	//������:�쳣������Ż����ɴ��򻯿������Ĵ���
	//ע�⣺@ExceptionHandler����ָ�����������쳣�������
	@ExceptionHandler(NameException.class)
	@ResponseBody
	public Object nameExp(NameException e){
		//��ؼ���e.printStackTrace()
		e.printStackTrace();
		return new JsonResult(2,e);
	}

	@ExceptionHandler(PasswordException.class)
	@ResponseBody
	public Object pwdExp(PasswordException e){
		e.printStackTrace();
		return new JsonResult(3,e);
	}
	

	
	
	
	
}
