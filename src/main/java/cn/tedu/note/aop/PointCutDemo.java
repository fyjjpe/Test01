package cn.tedu.note.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PointCutDemo {
	/**
	 * ֪ͨ:
	 * 
	 * @Before
	 * 
	 * @After�ȵ�
	 */
	/**
	 * �����: "execution(* cn.tedu.note.service.UserService.login(..))"
	 * ��"within(cn.tedu.note.service.UserServiceImpl)"�ȵ�
	 */

	// 1.execution(�������η�   ����.����.������(����))
	// @Before("execution(* cn.tedu.note.service.UserService.login(..))")
	// @Before("execution(* cn.tedu.note.*.*Service.*(..))")

	// @Before("bean(*Service)")

	// 2.within(ȫ·������)
	// @Before("within(cn.tedu.note.service.UserServiceImpl)")
//	@Before("within(cn.tedu.note.service.*Impl)")
	public void test() {
		System.out.println("PointCut");
	}

}
