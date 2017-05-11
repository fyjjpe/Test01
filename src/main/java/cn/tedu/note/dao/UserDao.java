package cn.tedu.note.dao;

import java.util.Map;

import cn.tedu.note.entity.User;

public interface UserDao {
	
	int addUser(User user);
	
	User findUserByName(String name);
	
	User findUserById(String userId);
	
	//ʵ�ֶ�̬����
	void updateUser(Map<String,Object> user);
}
