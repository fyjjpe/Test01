package cn.tedu.note.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//Aspect 切面,横截面
/*
 * 该类只是创建了切面组件，如果需要能够使用，
 * 就必须在Spring配置文件中配置
 */

@Component
@Aspect
public class AspectDemo {
	
	/*
	 * 以下五个注解声明叫做通知，声明切面方法的切入位置，相当于被拦截方法的前后位置
	 */
	
	//切面切入位置为userService的所有方法之前
//	@Before("bean(userService)")//1.在被拦截的方法之前执行
	public void test1(){
		System.out.println("调用方法之前，时执行test1");
	}
	
//	@AfterReturning("bean(userService)")//2.在被拦截的方法正常结束之后执行
	public void test2(){
		System.out.println("方法正常结束时，执行test2");
	}
	
//	@AfterThrowing("bean(userService)")//3.在被拦截的方法异常结束之后执行
	public void test3(){
		System.out.println("方法抛出异常时，执行test3");
	}
	
//	@After("bean(userService)")//4.在被拦截的方法最终(无论是否有异常)结束之后执行
	public void test4(){
		System.out.println("调用方法之后，才执行test4");
	}
	
	//5.@Around 环绕通知，可以在方法之前和之后执行,
	//  它对应的方法必须有返回值,可以抛出异常，而且还有特别重要的参数ProceedingJoinPoint
//	@Around("bean(userService)")
	public Object test5(ProceedingJoinPoint joinPoint) throws Throwable{
		//业务方法之前
		System.out.println("业务方法之前");
		long t1 = System.currentTimeMillis();
		
		//调用底层的业务方法:login/regist等
		Object obj = joinPoint.proceed();
		
		System.out.println("业务方法之后"+obj);
		//业务方法之后
		long t2 = System.currentTimeMillis();
		
		//Signature:签名，这里返回的是业务方法签名
		Signature s = joinPoint.getSignature();
		//s是方法签名信息：包含方法名和参数类型
		System.out.println(s+":"+(t2-t1));//User cn.tedu.note.service.UserService.login(String,String):365

		return obj;
		
		/*
		 * 正常登录输出:
		 *	  业务方法之前
         * 	 Login
         *   业务方法之后
         *   
         * 异常登录输出：
         * 	 业务方法之前
         * 	 Login
		 */
		
	}
	
	
}
