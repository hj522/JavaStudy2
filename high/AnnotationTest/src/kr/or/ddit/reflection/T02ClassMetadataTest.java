package kr.or.ddit.reflection;import java.lang.reflect.Modifier;

public class T02ClassMetadataTest {
	public static void main(String[] args) {
		
		// 자바 리플렉션
		// 클래스 오브젝트 생성하기
		Class<?> clazz = SampleVO.class;  // .class 방식 사용
		
		System.out.println("심플클래스명: " + clazz.getSimpleName()); 
		System.out.println("클래스명: " + clazz.getName()); // 패키지명까지 출력
		System.out.println("상위클래스명: " + clazz.getSuperclass().getName());
		
		// 패키지 정보 가져오기
		Package pkg = clazz.getPackage();
		System.out.println("패키지 정보: " + pkg.getName());
		
		// 해당 클래스에서 구현하고 있는 인터페이스 목록 가져오기
		Class<?>[] interfaces = clazz.getInterfaces();
		
		System.out.println("인터페이스 목록: ");
		for(Class<?> inf : interfaces) {
			System.out.print(inf.getName() + " | ");
			
		}
		
		System.out.println();
		
		// 클래스의 접근제어자가 가져오기(flag bit값 반환됨 => 접근제어자 설정 유무 정보)
		int modFlag = clazz.getModifiers(); //여러개일 수 있기 때문에 s 붙임
		
		System.out.println("접근제어자: " + Modifier.toString(modFlag));
	}
}