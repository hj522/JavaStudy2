package Assignment;

import kr.or.ddit.basic.T02EnumTest.Season;

public class Enum {

	public enum Planet {
		수성(2439), 금성(6052), 지구(6371), 화성(3390),
		목성(69911), 토성(58232), 천왕성(25362), 해왕성(24622);
		
		private double area;
		
		Planet(double data) {
			area = data;
		}

		public double getArea() {
			return area * area * Math.PI * 4;
		}
		
	}
	
	public static void main(String[] args) {
		
		Planet[] enumArr = Planet.values();
		for(int i=0; i<enumArr.length; i++) {
		System.out.println(enumArr[i].name() + " (" + enumArr[i].getArea() + ") ");
		
		}
				
	}
	
}
