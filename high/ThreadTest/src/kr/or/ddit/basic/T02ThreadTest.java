package kr.or.ddit.basic;

public class T02ThreadTest {
	public static void main(String[] args) {
	
	//	[멀티 스레드 프로그램 방식]
		
	// 방법1 : Thread 클래스를 상속한 class의 인스턴스를 생성한 후
	//	             이 인스턴스의 *start() 메서드를 실행한다.
	
	MyThread1 th1 = new MyThread1(); // MyThread1 객체 생성
	th1.start(); 
	
	
	// 방법2 : Runnable 인터페이스를 구현한 class의 인스턴스를 생성한 후
	//		   이 인스턴스를 Thread객체의 인스턴스를 생성할 때 생성자의 파라미터로 넘겨준다.
	//		   이때 생성된 Thread 객체의 인스턴스의 start()을 실행한다.
	
	Runnable r = new MyThread2();
	Thread th2 = new Thread(r); //runnable을 이용해서 thread 객체 생성
	th2.start();
	
	// 방법3 : 익명 클래스를 이용
	// 		  Runnable 인터페이스를 구현한 익명클래스를 Thread 인스턴스를 생성할 때 매개변수로 넘겨준다.
	// 방법2와 다른 점은 .. 별도의 클래스를 도출하지 않음(?)
	Thread th3 = new Thread(new Runnable() { // 익명 구현 객체
		
		@Override
		public void run() {
			
			for(int i=1; i<=200; i++) {
				System.out.print("@");
				
				try {
				 // Thread.sleep(시간) => 주어진 시간동안 작업을 잠시 멈춘다.
				 // 					  시간은 밀리세컨드단위를 사용함.
				 //						  즉, 1000은 1초를 의미한다.
					Thread.sleep(100); // 0.1초
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	});
	
	th3.start();

	}
}

class MyThread1 extends Thread { // thread 클래스를 상속해서 쓰레드 구현
	
	@Override
	public void run() { //thread에 있는 메소드.. 스레드가 수행할 작업을 void run() 안에 작성
		for(int i=1; i<=200; i++) {
			System.out.print("*");
			
			try {
			 // Thread.sleep(시간) => 주어진 시간동안 작업을 잠시 멈춘다.
			 // 					  시간은 밀리세컨드단위를 사용함.
			 //						  즉, 1000은 1초를 의미한다.
				Thread.sleep(100); // 0.1초
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}


class MyThread2 implements Runnable { // Runnable 인터페이스를 구현

	@Override
	public void run() {
		
		for(int i=1; i<=200; i++) {
			System.out.print("$");
			
			try {
			 // Thread.sleep(시간) => 주어진 시간동안 작업을 잠시 멈춘다.
			 // 					  시간은 밀리세컨드단위를 사용함.
			 //						  즉, 1000은 1초를 의미한다.
				Thread.sleep(100); // 0.1초
			} catch (InterruptedException e) {
				e.printStackTrace();
		
			}
		}
	}
}