package kr.or.ddit.basic;


@FunctionalInterface
public interface LambdaInterface1 { // public은 하나만 있어도 됨
	
	// 반환값이 없고 매개변수도 없는 추상메서드 선언
	public void test();
}


@FunctionalInterface
interface LambdaInterface2 {
	
	// 반환값은 없고 매개변수가 있는 추상메서드 선언
	public void test(int a);
}


@FunctionalInterface
interface LambdaInterface3 {
	
	// 반환값이 있고 매개변수도 있는 추상메서드 선언
	public int test(int a, int b);
}
