package cn.tedu.note.service;

import cn.tedu.note.entity.User;
import cn.tedu.note.exception.NameException;
import cn.tedu.note.exception.PasswordException;

/**
 * ҵ���ӿ�
 * �������ҵ��㹦�ܷ���
 *	ע��:�׳��쳣�Ǹ���ҵ�����󣬶����﷨����
 */
public interface UserService {
	
	/**
	 * ��¼���ܷ���
	 * @param name ��¼�û���
	 * @param password ��¼ ����
	 * @return ��¼�ɹ�ʱ�򷵻ص�¼���û���
	 * @throws NameException �û�������
	 * @throws PasswordException �������
	 */
	User login(String name,String password) throws NameException,PasswordException;	
	
	/**
	 * ע���û��Ĺ���
	 * @param name ����
	 * @param nick �ǳ�
	 * @param password ����
	 * @param confirm ȷ������
	 * @return ע��ɹ��󷵻�һ��ע���û�
	 * @throws �û�������
	 * @throws �������
	 */
	User regist(String name,String nick,String password,String confirm) 
			throws NameException,PasswordException;

	//��֤�û���token�Ƿ���ȷ
	boolean checkToken(String userId, String token);
	
	
	
}
