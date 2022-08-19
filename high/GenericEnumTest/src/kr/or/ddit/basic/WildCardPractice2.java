package kr.or.ddit.basic;

import java.util.ArrayList;
import java.util.List;

public class WildCardPractice2 {
	
	public static void displayCartItemInfo(Carts<? extends Foods> cart) {
		System.out.println("= 음식 장바구니 리스트 =");
		for(Object obj : cart.getList()) {
			System.out.println(obj.toString());
		}
		
		System.out.println("-----------------------------------");
	}
	
	public static void displayCartItemInfo2(Carts<? extends Drinks> cart) {
		System.out.println("= 음료 장바구니 리스트 =");
		for(Object obj : cart.getList()) {
			System.out.println(obj.toString());
		}
		
		System.out.println("===================================");
	}
	
	public static void displayCartItemInfo3(Carts<? super Meats> cart) {
		System.out.println("= 고기류 및 상위음식 장바구니 리스트 =");
		for(Object obj : cart.getList()) {
			System.out.println(obj.toString());
		}
		
		System.out.println("===================================");
	}
	
	
	

	public static void main(String[] args) {
		
		// food 타입의 cart
		Carts<Foods> foodCart = new Carts<>();
		foodCart.add(new Meats("돼지고기", 5000));
		foodCart.add(new Meats("소고기", 15000));
		foodCart.add(new Juices("토마토주스", 2000));
		foodCart.add(new Coffees("카푸치노", 3500));
		
		Carts<Meats> meatCart = new Carts<>();
		meatCart.add(new Meats("돼지고기", 5000));
		meatCart.add(new Meats("소고기", 15000));
		
		Carts<Drinks> drinkCart = new Carts<>();
		drinkCart.add(new Juices("토마토주스", 2000));
		
		displayCartItemInfo(foodCart);
		displayCartItemInfo(meatCart);
		displayCartItemInfo(drinkCart);
	}
}

	// 음식 클래스
	class Foods {
		
		private String name;
		private int price;
		
		public Foods(String name, int price) {
			super();
			this.name = name;
			this.price = price;
		}

		@Override
		public String toString() {
			return this.name + "(" + this.price + "원)";
		}
	}

	class Meats extends Foods {

		public Meats(String name, int price) {
			super(name, price);
		}
	}
	
	class Drinks extends Foods {

		public Drinks(String name, int price) {
			super(name, price);
		}
	}
	
	class Juices extends Drinks {

		public Juices(String name, int price) {
			super(name, price);
		}
	}
	
	class Coffees extends Drinks {

		public Coffees(String name, int price) {
			super(name, price);
		}
	}

class Carts<T> {
	
	private List<T> list = new ArrayList<>();
	
	public List<T> getList() {
		return list;
	}
	
	public void add(T item) {
		list.add(item);
	}
}