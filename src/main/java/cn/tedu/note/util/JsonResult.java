package cn.tedu.note.util;

import java.io.Serializable;

import cn.tedu.note.exception.NameException;

/**
 * ͬ�ڷ�װAJAX���õ�JSON����ֵ
 * ������ȷ����ֵ:
 * {state:0, data:��������, message:������Ϣ}
 * ����Ǵ��󷵻�ֵ:
 * {state:1, data:null, message:������Ϣ}
 *
 */
public class JsonResult implements Serializable{
	private static final long serialVersionUID = -3644950655568598241L;
	/**
	 * ����������������Ϊ״̬�Ĳ���
	 */
	public static final int SUCCESS = 0;
	public static final int ERROR = 1;
	/**
	 * state:�����Ƿ�ɹ���״̬,0��ʾ�ɹ�,1������ֵ��ʾʧ��
	 */
	private int state;
	/**
	 * data:�ɹ�ʱ�򷵻ص�JSON����
	 */
	private Object data;
	/**
	 * message:����ʱ��Ĵ�����Ϣ
	 */
	private String message;
	
	public JsonResult(){
		
	}

	//���ع�����,����������д��
	//�˺Ż��������
	public JsonResult(Throwable e){
		state = ERROR;
		data = null;
		message = e.getMessage();
	}
	//�˺ź�������ȷ
	public JsonResult(Object data){
		state = SUCCESS;
		this.data = data;
		message = "";
	}
	
	//�ڶ��죺������һ�����صĹ�����
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
