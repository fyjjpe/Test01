package cn.tedu.note.util;

import org.apache.commons.codec.digest.DigestUtils;
/**
 * ���η�����װ��
 * @author Administrator
 *
 */
public class Util {
	
	private static final String salt="tedu.cn��һ����ѵ����";
	/**
	 * ��װ��������㷨
	 * @param data
	 * @return ���ؼ��κ���ַ���
	 */
	public static String saltMd5(String data){
		return DigestUtils.md5Hex(data+salt);
	}
}
