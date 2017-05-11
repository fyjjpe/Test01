package cn.tedu.note.util;

import org.apache.commons.codec.digest.DigestUtils;
/**
 * 加盐方法封装类
 * @author Administrator
 *
 */
public class Util {
	
	private static final String salt="tedu.cn是一家培训机构";
	/**
	 * 封装密码加密算法
	 * @param data
	 * @return 返回加盐后的字符串
	 */
	public static String saltMd5(String data){
		return DigestUtils.md5Hex(data+salt);
	}
}
