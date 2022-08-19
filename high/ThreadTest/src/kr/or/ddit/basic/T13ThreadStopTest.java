package kr.or.ddit.basic;

public class T13ThreadStopTest {
	
	// 스레드 중지 시키기
	
	public static void main(String[] args) {
//		ThreadStopEx1 th = new ThreadStopEx1(); // 객체 생성해서 start 시킴
//		th.start();
//		
//		try {
//			Thread.sleep(1000);
//		} catch(InterruptedException ex) {
//			ex.printStackTrace();
//		}
//		
////		th.stop(); // 컴퓨터 강제종료 느낌.. 좋은 방법은 아님
//		
//		th.setStop(true); // 스레드를 종료하기 위해 stop 필드를 true로 변경
//						  // 무한루프를 빠져나오게 함
		
		ThreadStopEx2 th2 = new ThreadStopEx2();
		th2.start(); // 무한루프상태
		
		try {
			Thread.sleep(1000); //메인스레드 1초 잠듦
		} catch(InterruptedException ex) {
			ex.printStackTrace();
		}
		
		th2.interrupt();
	}
}


class ThreadStopEx1 extends Thread {
	
	private boolean stop; //상태값. stop 플래그 필드
	
	
	public void setStop(boolean stop) {
		this.stop = stop;
	}


	@Override
	public void run() {
		while(!stop) { // stop이 true가 되면 run() 종료
			System.out.println("스레드 처리 중..^^");
		}
		
		// 스레드가 사용한 자원 정리
		System.out.println("자원 정리 중..");
		System.out.println("실행 종료");
	}
}


// interrupt()메서드를 이용하여 스레드를 멈추게 하는 방법

class ThreadStopEx2 extends Thread {
	 	
	@Override
	public void run() {
	/*
	 	*방법1 => sleep()메소드나 join()메서드 등을 사용했을 때
	 	interrupt()메서드를 호출하면 InterruptedException이 발생한다.
	 	
		try {
		while(true) {
			System.out.println("스레드 처리중...");
			Thread.sleep(1); //외부에서 interrupt를 걸었을 때 interruptedException발생할 것임..
			}
		} catch(InterruptedException ex) {} //무한루프를 빠져나오고 println 실행
		
		System.out.println("자원 정리중...");
		System.out.println("실행 종료");
	
	}
*/
	
		// *방법2 => interrupt() 메소드가 호출되었는지 검사하기
	while(true) {
		System.out.println("스레드 처리중...");
		
		/* 검사방법1: 스레드 객체의 인스턴스 메소드를 이용하는 방법
		if(this.isInterrupted()) { // interrupt() 호출되면 true 리턴
			System.out.println("인스턴스 메소드 isInterrupted 호출됨");
			break;
		} */
		
		// 검사방법2: 스레드의 정적 메소드를 이용하는 방법
		if(Thread.interrupted()) {
			System.out.println("정적 메서드 interrupted 호출됨");
			break;
		}
	}
	System.out.println("자원 정리중...");
	System.out.println("실행 종료");

	}
}