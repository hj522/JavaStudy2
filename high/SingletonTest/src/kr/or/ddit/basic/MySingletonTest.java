package kr.or.ddit.basic;

public class MySingletonTest {
	public static void main(String[] args) {
		
	//	MySingleton test1 = new MySingleton(); // new 명령 사용 불가. private로 막아놨기 때문에 사용못함!
		
		
		// getInstance()메소드 이용하여 객체 가져오기
		MySingleton test2 = MySingleton.getInstance();
		test2.displayText();
		
		MySingleton test3 = MySingleton.getInstance();
		test3.displayText();
		
		System.out.println("test2 =>" + test2); //동일한 객체
		System.out.println("test3 =>" + test3); //동일한 객체
		
	}
}
