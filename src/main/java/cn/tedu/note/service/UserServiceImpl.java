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
		
	 	// System.out.println("Login");//��׮,����aop����
		
		//���û���������Ϊ�ս����ж�������
		if(name==null || name.trim().isEmpty()){
			throw new NameException("�û�������Ϊ��");
		}
		if(password==null || password.trim().isEmpty()){
			throw new PasswordException("���벻��Ϊ��");
		}
		//�����û��������û���Ϣ,�ڼ�������Ƿ���ȷ
		//���ݿ��ѯʱ,�Բ�ѯ�Զ�����Ҫ���ִ�Сд
		User user = userDao.findUserByName(name);
		//Ϊ��ֹ�鲻���û�ʱ�����ж�����ʱ�����ֿ�ָ���쳣,���ڴ˽����ж�
		if(user==null){
			throw new NameException("�û�������");
		}
		
		//password ���������
		String md5 = Util.saltMd5(password);
//		System.out.println(md5);//��׮
		
		//�Ƚ�ժҪ�����ժҪһ��������(����)һ��
		if(user.getPassword().equals(md5)){
			String token = UUID.randomUUID().toString();
			user.setToken(token);
			//��̬����user
			Map<String,Object> userInfo = new HashMap<String,Object>();
			userInfo.put("id", user.getId());
			userInfo.put("token", token);
			userDao.updateUser(userInfo);
			return user;
		}
		throw new PasswordException("�������");
	}

	@Transactional
	public User regist(String name, String nick, String password, String confirm)
			throws NameException, PasswordException {
		if(name==null || name.trim().isEmpty()){
			throw new NameException("�û�������Ϊ��");
		}
		if(password==null || password.trim().isEmpty()){
			throw new PasswordException("���벻��Ϊ��");
		}
		//ȷ��������ж�
		if(!password.equals(confirm)){
			throw new PasswordException("ȷ�����벻һ��");
		}
		//дҵ����߼�ʱ��������Ҫʹ������ʱ�����������ݲ㷽��
		User user = userDao.findUserByName(name);
		if(user!=null){
			throw new NameException("���û��Ѵ���");
		}
		if(nick==null || nick.trim().isEmpty()){
			//�û�δ�����ǳƣ��ͽ��û�����Ϊ�ǳ�
			nick = name;
		}
		//ʹ��UUID��������id,�÷���������id�ڵ�����Ψһ
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
