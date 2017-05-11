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
		//给正常且成功登录的用户添加一个token，防止直接从编辑页面进入
		//将Cookie从服务器下发到客户端
		Cookie cookie = new Cookie("token","12345");
		cookie.setPath("/");
		
		User user = userService.login(name, password);
		//将用户表中的token下发到浏览器中
		cookie.setValue(user.getToken());
		
		res.addCookie(cookie);
		
		//若此处写:return user;此处返回的是{id:123,name:tom,password:123....}
		//这样不好,当密码或账号错误时,无法返回Json对象
		//写一个JsonResult类 封装返回的消息状态、内容、异常消息
		return new JsonResult(user);
	}
	
	@RequestMapping("/regist.do")
	@ResponseBody
	public JsonResult regist(String name,String password,String confirm,String nick){
		User user = userService.regist(name, nick, password, confirm);
		return new JsonResult(user);
	}
	
	//第三天:异常处理的优化，可大大简化控制器的代码
	//注意：@ExceptionHandler可以指定传入类型异常这个参数
	@ExceptionHandler(NameException.class)
	@ResponseBody
	public Object nameExp(NameException e){
		//务必加上e.printStackTrace()
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
