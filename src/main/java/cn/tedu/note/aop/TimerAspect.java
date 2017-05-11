package cn.tedu.note.aop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ������� �������
 * @author Administrator
 *
 */
@Component
@Aspect
public class TimerAspect {
	//����һ���ļ���һ����������
	private File file;
	
	//�������ļ�config�л�ȡlogfile����
	//���һ�������ļ�config.properties
	//�����ļ��а������� logfile
	//��Spring�ж�ȡ�����ļ�Ϊ Bean config
	
	//�������ļ��ı������ֺ͸�ʽд���ˣ�����Ҫ�ģ�ֻ��Ҫ��config.properties�����ļ�������ֵ����
	@Value("#{config.logfile}")	
	public void setFilename(String filename){
		System.out.println(filename);//��׮
		file = new File(filename);
		
	}
	
	//��������
	//�����ߺ�������ģʽ:ʹ��BlockingQueue
	private BlockingQueue<String> queue = new LinkedBlockingQueue<String>(500);
	

	//���뵽ȫ��ҵ�񷽷���ǰ�󣬽�������ʱ�������
	@Around("bean(*Service)")
	public Object test(ProceedingJoinPoint joinPoint) throws Throwable{
		long t1 = System.currentTimeMillis();
		//����ҵ�񷽷�
		Object obj = joinPoint.proceed();
		long t2 = System.currentTimeMillis();
		//����ҵ���ʱ
		long t = t2-t1;
		//��ȡ��ǰ�߳�(����߳���Tomcat������)
		Thread tx = Thread.currentThread();
//		System.out.println(tx+":"+t);
//		Thread.sleep(5000);
		//�õ���ʱ�䣬�ٵõ�������ǩ��
		Signature s = joinPoint.getSignature();
		//����Ҫ��������ݼ��뵽������ȥ
		String str = tx+","+s+","+t;
		//����Ҫ�����������ӵ�������
		queue.offer(str);
		return obj;
	}
	
	@PostConstruct //����Bean���������ִ�еķ���
	public void start(){
		//Bean���TimerAspect�����Ժ����������߳�
		t.start();
	}
	
	private Thread t = new Thread(){
		public void run() {
			System.out.println("OK");
			PrintWriter pw = null;
			try {
				while(true){
					//take�Ӷ�����ȡ��һ������
					//���û�����ݣ���ȵ�������Ϊֹ
					String s = queue.take();
					//���ļ�(׷�ӷ���)
					pw = new PrintWriter(new FileOutputStream(file, true));
					//д������
					pw.println(s);
//					System.out.println("T:" + s);//��׮
					while(!queue.isEmpty()){
						s = queue.take();
						// System.out.println("T:" + s);//��׮
						pw.println(s);
					}
					//�ر��ļ�
					pw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	};
	
	
	
}
