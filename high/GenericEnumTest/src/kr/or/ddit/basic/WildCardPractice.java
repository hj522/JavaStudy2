package kr.or.ddit.basic;

import java.util.ArrayList;
import java.util.List;

public class WildCardPractice {
	
	public static void main(String[] args) {
		
		FruitsBox<Fruits> fruitsBox = new FruitsBox<>();
		FruitsBox<Peach> peachBox = new FruitsBox<>();
		
		fruitsBox.add(new Peach());
		fruitsBox.add(new Orange());
		
		peachBox.add(new Peach());
		peachBox.add(new Peach());
		
		Mixer.makeJuice(fruitsBox);
		Mixer.makeJuice(peachBox);
		
		
	}
}


class Mixer {
	
	static void makeJuice(FruitsBox<? extends Fruits> box) {
		String fruitsListStr = ""; // 과일 목록
		
		int cnt = 0;
		
		for(Object f : box.getFruitsList()) {
			if(cnt == 0) {
				fruitsListStr += f;
			} else {
				fruitsListStr += ", " + f;
			}
			cnt++;
		}
		
		System.out.println(fruitsListStr + " => 쥬스 완성^ㅁ^~");
	}
}


class Fruits {
	
	private String name; // 과일 이름

	public Fruits(String name) {
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

class Peach extends Fruits {
	
	public Peach() {
		super("복숭아");
	}
}

class Orange extends Fruits {
	
	public Orange() {
		super("오렌지");
	}
}

class FruitsBox<T> {
	
	private List<T> fruitsList = new ArrayList<>(); // 멤버 변수 선언
	
	public List<T> getFruitsList() {
		return fruitsList;
	}

	public void add(T fruits) { // T타입의 과일 담기
		fruitsList.add(fruits);
	}
}