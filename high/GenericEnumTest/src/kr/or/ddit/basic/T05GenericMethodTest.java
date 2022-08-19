package kr.or.ddit.basic;

class Util2 {
	            // 제한된 파라미터 문법.. extends
				// Number 또는 Number라는 클래스를 extends한 타입만 모두 허용하겠다! 
	public static <T extends Number> int compare(T t1, T t2) {
		
		double v1 = t1.doubleValue();
		double v2 = t2.doubleValue();
		
		return Double.compare(v1, v2);
		
	}
}

public class T05GenericMethodTest {
	public static void main(String[] args) {
		
		int result1 = Util2.<Integer>compare(10, 20); // 오른쪽이 더 크기 때문에 음수값 출력
		System.out.println(result1);
		
		int result2 = Util2.<Number>compare(3.14, 3); // 왼쪽이 더 크기 때문에 양수값 출력
		System.out.println(result2);
		
//		Util2.compare("C", "JAVA"); // 타입 제한이 걸려서 출력 불가능.. type safety
		
	}
}
