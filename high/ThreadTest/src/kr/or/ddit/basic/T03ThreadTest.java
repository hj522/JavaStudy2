package kr.or.ddit.basic;

public class T03ThreadTest {
	public static void main(String[] args) {
		
		// 스레드의 수행시간 체크 해보기
		Thread th = new Thread(new MyRunner());
		
		// UTC(Universal Time Coodinated 협정 세계 표준시)를 사용하여
		// 1970년 1월 1일 0시 0분 0초를 기준으로 경과한 시간을 밀리세컨드 단위로 나타낸다.
		
		long startTime = System.currentTimeMillis();
		
		th.start(); // 스레드 구동하기
		
		try {
			th.join(); // 현재 실행중인 main스레드에서 작업중인 스레드(지금은 th스레드)가 종료될 때까지 기다린다.
		} catch(InterruptedException ex) {
			ex.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("경과 시간(ms): " + (endTime - startTime)); // 얼마나 걸렸는지
	}
}

class MyRunner implements Runnable {
	// 1~1000000000 까지의 합계를 구하는 스레드 클래스
	
	@Override
	public void run() { // 실제 작업할 내용은 run 안에 넣으면 된다
		long sum = 0;
		for(int i=1; i<=1000000000; i++) {
			sum += i;
		}
		
		System.out.println("합계: " + sum);
	}
}
