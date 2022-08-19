package kr.or.ddit.basic;

public class T11DisplayCharacterTest {

	/*
	 	3개(명)의 스레드가 각각 알파벳 대문자를 출력하는데
	 	출력을 끝낸 순서대로 결과를 나타내는 프로그램 작성하기~~
	 */
	
	
	// main메소드 밖에 static으로 공통 변수 선언(순위 정보를 담기 위함) 
	static String strRank = "";
	
	public static void main(String[] args) {
		
		DisplayCharacter[] disChars = new DisplayCharacter[] {
			new DisplayCharacter("홍길동"), // 객체 3개 배열에 담기
			new DisplayCharacter("변학도"),
			new DisplayCharacter("일지매"),
		};
		
		for(DisplayCharacter dc : disChars) {
			dc.start();
		}
		
		for(DisplayCharacter dc : disChars) {
			try {
				dc.join(); // DisplayCharacter가 종료 될 때까지 기둘기 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("경기 끝...");
		System.out.println("-----------------");
		System.out.println();
		System.out.println("경기 결과 ");
		System.out.println("순위: " + strRank); // 클래스 내에서 쓰는 거기 때문에 T11DisplayCharacterTest.strRank 라고 적지 않아도 ㅇㅋ
	}
}


// 알파벳 대문자를 출력하는 스레드 클래스

class DisplayCharacter extends Thread {
	private String name;
	
	public DisplayCharacter(String name) {
		this.name = name; //생성자 파라미터에 이름값 넣어주려고
	}
	
	@Override
	public void run() {
		
		for(char ch='A'; ch<='Z'; ch++) {
			System.out.println(name + "의 출력 문자: " + ch);
			
			try {
				// sleep() 메소드의 값을 200~500 사이의 난수로 한다. 초 랜덤
				Thread.sleep((int)(Math.random() + 301 + 200));
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		
		System.out.println(name + " 출력 끝...");
		
		T11DisplayCharacterTest.strRank += name + " ";  // 끝나는 순서대로 출력
	}
}

	