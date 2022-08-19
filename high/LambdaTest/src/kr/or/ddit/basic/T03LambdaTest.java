package kr.or.ddit.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class T03LambdaTest {
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<String>();
		list.add("홍길동");
		list.add("이몽룡");
		list.add("강감찬");
		
						//Consumer: functional interface
		list.forEach(new Consumer<String>() {
			
			@Override
			public void accept(String name) {
				System.out.println(name);
			}
		});
		
		System.out.println("-----------------------------------");

		
		// 람다식 사용
		list.forEach(name -> System.out.println(name));
		
	}
}
