package Assignment;

import java.util.Comparator;

public class HorseRacing {
	
	static String horseRank = "";
	
	public static void main(String[] args) {
		
		Horse[] mal = new Horse[] {
			new Horse("1번말"),
			new Horse("2번말"),
			new Horse("3번말"),
			new Horse("4번말"),
			new Horse("5번말"),
			new Horse("6번말"),
			new Horse("7번말"),
			new Horse("8번말"),
			new Horse("9번말"),
			new Horse("10번말"),
		};
		
		for(Horse h : mal) {
			h.start();
		}
		
		for(Horse h : mal) {
			try {
				h.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("레이스 종료");
		System.out.println("--------");
		System.out.println();
		System.out.println("★☆결과 발표☆★");
		System.out.println("순위: " + horseRank);
	}
	
}


class Horse extends Thread {

	private String name;
	private int rank;
	
	
	public Horse(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		
		for(char ch='A'; ch<='Z'; ch++) {
			System.out.println(name + ">");
		}
		
		try {
			Thread.sleep((int)(Math.random() + 11 + 1));
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		
	}
}

	class Asc implements Comparator<String> {
		
		@Override
		public int compare(String str1, String str2) {
			return str1.compareTo(str2);
		}
	}
	
	
	/*
	  	
	 */
