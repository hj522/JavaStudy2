package kr.or.ddit.basic;

public class Service {
	
	@PrintAnnotation // 마치 주석처럼 ,,, 에러가 안ㄴ ㅏ , ,, ,,,,,,,,,
	public void method1() {
		System.out.println("메서드1에서 출력되었습니다."); // 디폴트로 설정한 값 출력
	}
	
	// @PrintAnnotation("%")
	@PrintAnnotation(value = "%")
	public void method2() {
		System.out.println("메서드2에서 출력되었습니다.");
	}
	
	@PrintAnnotation(value = "#", count = 25)
	public void method3() {
		System.out.println("메서드3에서 출력되었습니다.");
	}
	
	
	
}
