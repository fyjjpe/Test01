package cn.tedu.note.util;

import java.io.Serializable;

import cn.tedu.note.exception.NameException;

/**
 * 同于封装AJAX调用的JSON返回值
 * 其中正确返回值:
 * {state:0, data:返回数据, message:错误消息}
 * 如果是错误返回值:
 * {state:1, data:null, message:错误消息}
 *
 */
public class JsonResult implements Serializable{
	private static final long serialVersionUID = -3644950655568598241L;
	/**
	 * 定义两个常量，作为状态的参数
	 */
	public static final int SUCCESS = 0;
	public static final int ERROR = 1;
	/**
	 * state:返回是否成功的状态,0表示成功,1或其他值表示失败
	 */
	private int state;
	/**
	 * data:成功时候返回的JSON数据
	 */
	private Object data;
	/**
	 * message:错误时候的错误消息
	 */
	private String message;
	
	public JsonResult(){
		
	}

	//重载构造器,方便代码的书写简化
	//账号或密码错误
	public JsonResult(Throwable e){
		state = ERROR;
		data = null;
		message = e.getMessage();
	}
	//账号和密码正确
	public JsonResult(Object data){
		state = SUCCESS;
		this.data = data;
		message = "";
	}
	
	//第二天：再增加一个重载的构造器
	public JsonResult(int state,Throwable e){
		this.state = state;
		data = null;
		message = e.getMessage();
	}
	
	public JsonResult(int state, Object data, String message) {
		super();
		this.state = state;
		this.data = data;
		this.message = message;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "JsonResult [state=" + state + ", data=" + data + ", message=" + message + "]";
	}

}
