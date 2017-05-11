package cn.tedu.note.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//Aspect ����,�����
/*
 * ����ֻ�Ǵ�������������������Ҫ�ܹ�ʹ�ã�
 * �ͱ�����Spring�����ļ�������
 */

@Component
@Aspect
public class AspectDemo {
	
	/*
	 * �������ע����������֪ͨ���������淽��������λ�ã��൱�ڱ����ط�����ǰ��λ��
	 */
	
	//��������λ��ΪuserService�����з���֮ǰ
//	@Before("bean(userService)")//1.�ڱ����صķ���֮ǰִ��
	public void test1(){
		System.out.println("���÷���֮ǰ��ʱִ��test1");
	}
	
//	@AfterReturning("bean(userService)")//2.�ڱ����صķ�����������֮��ִ��
	public void test2(){
		System.out.println("������������ʱ��ִ��test2");
	}
	
//	@AfterThrowing("bean(userService)")//3.�ڱ����صķ����쳣����֮��ִ��
	public void test3(){
		System.out.println("�����׳��쳣ʱ��ִ��test3");
	}
	
//	@After("bean(userService)")//4.�ڱ����صķ�������(�����Ƿ����쳣)����֮��ִ��
	public void test4(){
		System.out.println("���÷���֮�󣬲�ִ��test4");
	}
	
	//5.@Around ����֪ͨ�������ڷ���֮ǰ��֮��ִ��,
	//  ����Ӧ�ķ��������з���ֵ,�����׳��쳣�����һ����ر���Ҫ�Ĳ���ProceedingJoinPoint
//	@Around("bean(userService)")
	public Object test5(ProceedingJoinPoint joinPoint) throws Throwable{
		//ҵ�񷽷�֮ǰ
		System.out.println("ҵ�񷽷�֮ǰ");
		long t1 = System.currentTimeMillis();
		
		//���õײ��ҵ�񷽷�:login/regist��
		Object obj = joinPoint.proceed();
		
		System.out.println("ҵ�񷽷�֮��"+obj);
		//ҵ�񷽷�֮��
		long t2 = System.currentTimeMillis();
		
		//Signature:ǩ�������ﷵ�ص���ҵ�񷽷�ǩ��
		Signature s = joinPoint.getSignature();
		//s�Ƿ���ǩ����Ϣ�������������Ͳ�������
		System.out.println(s+":"+(t2-t1));//User cn.tedu.note.service.UserService.login(String,String):365

		return obj;
		
		/*
		 * ������¼���:
		 *	  ҵ�񷽷�֮ǰ
         * 	 Login
         *   ҵ�񷽷�֮��
         *   
         * �쳣��¼�����
         * 	 ҵ�񷽷�֮ǰ
         * 	 Login
		 */
		
	}
	
	
}
