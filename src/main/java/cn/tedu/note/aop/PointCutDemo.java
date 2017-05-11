package cn.tedu.note.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PointCutDemo {
	/**
	 * 通知:
	 * 
	 * @Before
	 * 
	 * @After等等
	 */
	/**
	 * 切入点: "execution(* cn.tedu.note.service.UserService.login(..))"
	 * 和"within(cn.tedu.note.service.UserServiceImpl)"等等
	 */

	// 1.execution(访问修饰符   包名.类名.方法名(参数))
	// @Before("execution(* cn.tedu.note.service.UserService.login(..))")
	// @Before("execution(* cn.tedu.note.*.*Service.*(..))")

	// @Before("bean(*Service)")

	// 2.within(全路径类名)
	// @Before("within(cn.tedu.note.service.UserServiceImpl)")
//	@Before("within(cn.tedu.note.service.*Impl)")
	public void test() {
		System.out.println("PointCut");
	}

}
