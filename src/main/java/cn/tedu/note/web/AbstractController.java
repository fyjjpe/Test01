package cn.tedu.note.web;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.note.util.JsonResult;
/**
 * ��������ĳ����࣬�����������еķ���
 * @author Administrator
 *
 */
public abstract class AbstractController {
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public JsonResult exp(Exception e){
		e.printStackTrace();
		return new JsonResult(e);
	}
	
}
