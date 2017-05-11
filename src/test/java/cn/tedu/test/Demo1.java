package cn.tedu.test;

public class Demo1 {
	public static void main(String[] args) {
		// Foo f1 = Foo.getFoo();
		// Foo f2 = Foo.getFoo();
		// System.out.println(f1==f2);//true
		
		GirlFriend g1 = GirlFriend.getG1();
		GirlFriend g2 = GirlFriend.getG1();
		System.out.println(g1==g2);//true
	}
}



//懒汉式加载,饱和式，在需要时候创建对对象
class Foo{
	private static Foo foo;
	private Foo(){
	}
	public synchronized static Foo getFoo(){
		if(foo==null){
			foo = new Foo();
		}
		return foo;
	}
}

//非懒惰模式，饥饿式，及早创建对象
class GirlFriend{
	private static GirlFriend g1 = new GirlFriend();
	private GirlFriend(){
		
	}
	public static GirlFriend getG1(){
		return g1;
	}
	
}






