package kr.or.ddit.basic;

import java.util.ArrayList;
import java.util.List;

public class T06WildCardTest {
	
	/*
	  	[와일드 카드]
	 	 
	 	 와일드카드(?)는 제너릭 타입을 이용한 타입 안전한 코드를 위해 사용되는 특별한 종류의 인수(Argument)로서,
	 	 함수 선언, 객체 생성 및 메서드를 정의할 때 사용된다.
	 	 
	 	 <? extends T>
	 	  : 와일드카드의 상한 제한. T와 그 자손들만 가능
	 	 
	 	 <? super T>
	 	  : 와일드카드의 하한 제한. T와 그 조상들만 가능
	 	 
	 	 <?>
	 	  : 모든 타입이 가능 <? extends Object>와 동일
	 
	 */
	
	public static void main(String[] args) {
		
		FruitBox<Fruit> fruitBox = new FruitBox<>();
		FruitBox<Apple> appleBox = new FruitBox<>();
		FruitBox<Garbage> garbageBox = new FruitBox<>();
		
		fruitBox.add(new Apple());
		fruitBox.add(new Grape());
		
		appleBox.add(new Apple());
		appleBox.add(new Apple());
	//	appleBox.add(new Grape()); (X) 
		
		garbageBox.add(new Garbage());
		garbageBox.add(new Garbage());
		garbageBox.add(new Garbage());
		
		
		Juicer.makeJuice(fruitBox);
		Juicer.makeJuice(appleBox); 
	//	Juicer.makeJuice(garbageBox); //(X)
				
	}
}


	class Juicer { // 제한된 파라미터 문법 사용
		//static <T extends Fruit> void makeJuice(FruitBox<T> box) {
		
		static void makeJuice(FruitBox<? extends Fruit> box) {
							 // 일반 메소드. 와일드카드 + 타입제한 걸기
							 // 메서드의 매개변수에 와일드 카드를 사용!
			
			
			String fruitListStr = ""; // 과일 목록
			
			int cnt = 0;
			
			for(Object f : box.getFruitList()) {
				if(cnt == 0) {
					fruitListStr += f;
				} else {
					fruitListStr += ", " + f;
				}
				cnt++;
			}
			
			System.out.println(fruitListStr + " => 쥬스 완성!");
		}
	}

	
	class Fruit {
		
		private String name; // 과일이름

		public Fruit(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "과일(" + name + ")";
		}
	}
	
	
	class Apple extends Fruit {

		public Apple() {
			super("사과");
		}
	}
	
	
	class Grape extends Fruit {

		public Grape() {
			super("포도");
		}
	}
	
	class Garbage {

		@Override
		public String toString() {
			return "쓰레기";
		}
		
	}
	
	class FruitBox<T> {
		
		private List<T> fruitList = new ArrayList<>(); // 멤버 변수 선언

		public List<T> getFruitList() {
			return fruitList;
		}
		
		public void add(T fruit) { // T타입의 과일 담기
			fruitList.add(fruit);
		}
	}