	package cn.tedu.note.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.User;
import cn.tedu.note.exception.NameException;
import cn.tedu.note.exception.PasswordException;
import cn.tedu.note.util.Util;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserDao userDao;

	@Transactional
	public User login(String name, String password)
			throws NameException, PasswordException{
		
	 	// System.out.println("Login");//打桩,测试aop技术
		
		//对用户名和密码为空进行判定并处理
		if(name==null || name.trim().isEmpty()){
			throw new NameException("用户名不能为空");
		}
		if(password==null || password.trim().isEmpty()){
			throw new PasswordException("密码不能为空");
		}
		//根据用户名查找用户信息,在检查密码是否正确
		//数据库查询时,对查询自动不需要区分大小写
		User user = userDao.findUserByName(name);
		//为防止查不到用户时，在判断密码时，出现空指针异常,特在此进行判定
		if(user==null){
			throw new NameException("用户名错误");
		}
		
		//password 密码的明文
		String md5 = Util.saltMd5(password);
//		System.out.println(md5);//打桩
		
		//比较摘要，如果摘要一样则明文(密码)一样
		if(user.getPassword().equals(md5)){
			String token = UUID.randomUUID().toString();
			user.setToken(token);
			//动态更新user
			Map<String,Object> userInfo = new HashMap<String,Object>();
			userInfo.put("id", user.getId());
			userInfo.put("token", token);
			userDao.updateUser(userInfo);
			return user;
		}
		throw new PasswordException("密码错误");
	}

	@Transactional
	public User regist(String name, String nick, String password, String confirm)
			throws NameException, PasswordException {
		if(name==null || name.trim().isEmpty()){
			throw new NameException("用户名不能为空");
		}
		if(password==null || password.trim().isEmpty()){
			throw new PasswordException("密码不能为空");
		}
		//确认密码的判断
		if(!password.equals(confirm)){
			throw new PasswordException("确认密码不一致");
		}
		//写业务层逻辑时，凡是需要使用数据时，都调用数据层方法
		User user = userDao.findUserByName(name);
		if(user!=null){
			throw new NameException("该用户已存在");
		}
		if(nick==null || nick.trim().isEmpty()){
			//用户未输入昵称，就将用户名作为昵称
			nick = name;
		}
		//使用UUID方法生成id,该方法产出的id在地球上唯一
		String id = UUID.randomUUID().toString();
		String token = "";
		password = Util.saltMd5(password);
		user = new User(id, name, password, token, nick);
		
		userDao.addUser(user);
		
		return user;
	}
	
	@Transactional(readOnly=true)
	public boolean checkToken(String userId, String token) {
		User user = userDao.findUserById(userId);
//		System.out.println(user);
		return token.equals(user.getToken());
	}

}
