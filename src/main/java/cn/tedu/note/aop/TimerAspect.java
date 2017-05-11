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
 * 性能审计 切面组件
 * @author Administrator
 *
 */
@Component
@Aspect
public class TimerAspect {
	//管理一个文件和一个阻塞队列
	private File file;
	
	//从配置文件config中获取logfile属性
	//添加一个配置文件config.properties
	//配置文件中包含属性 logfile
	//在Spring中读取配置文件为 Bean config
	
	//将配置文件的保存名字和格式写活了，将来要改，只需要该config.properties配置文件中属性值即可
	@Value("#{config.logfile}")	
	public void setFilename(String filename){
		System.out.println(filename);//打桩
		file = new File(filename);
		
	}
	
	//阻塞队列
	//生产者和消费者模式:使用BlockingQueue
	private BlockingQueue<String> queue = new LinkedBlockingQueue<String>(500);
	

	//切入到全部业务方法的前后，进而计算时间的消耗
	@Around("bean(*Service)")
	public Object test(ProceedingJoinPoint joinPoint) throws Throwable{
		long t1 = System.currentTimeMillis();
		//调用业务方法
		Object obj = joinPoint.proceed();
		long t2 = System.currentTimeMillis();
		//计算业务耗时
		long t = t2-t1;
		//获取当前线程(这个线程是Tomcat创建的)
		Thread tx = Thread.currentThread();
//		System.out.println(tx+":"+t);
//		Thread.sleep(5000);
		//得到了时间，再得到方法的签名
		Signature s = joinPoint.getSignature();
		//将需要保存的数据加入到队列中去
		String str = tx+","+s+","+t;
		//将需要保存的数据添加到队列中
		queue.offer(str);
		return obj;
	}
	
	@PostConstruct //创建Bean组件后立即执行的方法
	public void start(){
		//Bean组件TimerAspect创建以后，立即启动线程
		t.start();
	}
	
	private Thread t = new Thread(){
		public void run() {
			System.out.println("OK");
			PrintWriter pw = null;
			try {
				while(true){
					//take从队列中取出一个数据
					//如果没有数据，则等到有数据为止
					String s = queue.take();
					//打开文件(追加方法)
					pw = new PrintWriter(new FileOutputStream(file, true));
					//写入数据
					pw.println(s);
//					System.out.println("T:" + s);//打桩
					while(!queue.isEmpty()){
						s = queue.take();
						// System.out.println("T:" + s);//打桩
						pw.println(s);
					}
					//关闭文件
					pw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	};
	
	
	
}
