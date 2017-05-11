package cn.tedu.note.service;

import cn.tedu.note.entity.User;
import cn.tedu.note.exception.NameException;
import cn.tedu.note.exception.PasswordException;

/**
 * 业务层接口
 * 声明软件业务层功能方法
 *	注意:抛出异常是根据业务需求，而非语法需求
 */
public interface UserService {
	
	/**
	 * 登录功能方法
	 * @param name 登录用户名
	 * @param password 登录 密码
	 * @return 登录成功时候返回登录的用户名
	 * @throws NameException 用户名错误
	 * @throws PasswordException 密码错误
	 */
	User login(String name,String password) throws NameException,PasswordException;	
	
	/**
	 * 注册用户的功能
	 * @param name 名称
	 * @param nick 昵称
	 * @param password 密码
	 * @param confirm 确认密码
	 * @return 注册成功后返回一个注册用户
	 * @throws 用户名错误
	 * @throws 密码错误
	 */
	User regist(String name,String nick,String password,String confirm) 
			throws NameException,PasswordException;

	//验证用户的token是否正确
	boolean checkToken(String userId, String token);
	
	
	
}
