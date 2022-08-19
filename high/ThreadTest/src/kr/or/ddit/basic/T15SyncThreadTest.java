package kr.or.ddit.basic;

	// 동기화(Synchronization): 공유자원(객체)을 상대로 순서대로 작업이 이루어지도록 처리하는 방법

public class T15SyncThreadTest {
	public static void main(String[] args) {
		ShareObject sObj = new ShareObject();
		
		WorkThread th1 = new WorkThread("1번스레드", sObj);
		WorkThread th2 = new WorkThread("2번스레드", sObj);
		
		th1.start();
		th2.start();
		
		// 2개의 스레드가 동시에 각자 자기 할 일을 처리 -> 임계영역 발생 가능
		// 임계영역(Critical Section)에 동기화 처리하여 문제 발생 방지
	}
}

// 공통으로 사용할 객체
class ShareObject {
	private int sum = 0;
	
	// 동기화 하는 방법1 : 메소드 자체에 동기화 설정하기
	// 임계영역 add에  동기화(synchronized)처리
//	synchronized public void add() { 
	public void add() { 
		
	// 동기화 하는 방법 2: 동기화 코드블럭으로 설정하기
//		synchronized (this) {
		for(int i=0; i<1000000000; i++) {} // 동기화 전까지 시간벌기용
		
			int n = sum;
			
			n += 10; 
			
			sum = n;
			System.out.println(Thread.currentThread().getName()
								+ " 합계: " + sum);
//		}
	}	
}


// 작업을 수행하는 스레드 클래스
class WorkThread extends Thread {
	private ShareObject sObj;
	
	public WorkThread(String name, ShareObject sObj) {
		super(name);
		this.sObj = sObj;
	}
	
	@Override
	public void run() {
		for(int i=1; i<=10; i++) {
			synchronized (sObj) { // mutex: 공유객체
				sObj.add(); // add메소드 10번 호출
			}
		}
	}
}