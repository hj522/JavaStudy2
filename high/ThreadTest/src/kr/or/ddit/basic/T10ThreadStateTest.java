package kr.or.ddit.basic;

/*
	 < 스레드의 상태 >
	 
	 - NEW : 스레드가 생성되고 아직 start()가 호출되지 않은 상태
	 - RUNNABLE : 실행 중 또는 실행 가능한 상태 
	 - BLOCKED : 동기화 블럭에 의해서 일시정지 된 상태(Lock이 풀릴 때까지 기다리는 상태)
	 - WAITING, TIMED_WAITING : 스레드의 작업이 종료되지는 않았지만 실행 가능하지 않은 일시정지 상태
	 						    (TIMED_WAITING은 일시정지 시간이 지정된 경우임)
	 						          일시정지가 풀리면 다시 RUNNABLE로 돌아감
	 - TERMINATED : 스레드의 작업이 종료된 상태
 */

public class T10ThreadStateTest {

	public static void main(String[] args) {
		
		TargetThread targetTh = new TargetThread();
		
		StatePrintThread spTh = new StatePrintThread(targetTh);
		
		spTh.start();
		
	}
}


// Target 스레드.. 어떻게바뀌어가는지 상태 확인용
class TargetThread extends Thread {
	
	@Override
	public void run() {
		for(long i=1; i<=1000000000L; i++) {} // 시간 지연용
		
		try {
			Thread.sleep(1500); 
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		
		for(long i=1; i<=1000000000L; i++) {} // 시간 지연용
	}
}


// 스레드의 상태를 출력하기 위한 스레드 클래스
class StatePrintThread extends Thread {
	private Thread targetThread; // 상태변화를 관찰할 스레드 저장 변수

	public StatePrintThread(Thread targetThread) {
		this.targetThread = targetThread;
	}
	
	@Override
	public void run() {
		while(true) {
			
			// 스레드의 상태 구하기 (getState() 이용)
			Thread.State state = targetThread.getState(); //열거형 상수,, 스레드 클래스 안에서 선언
			System.out.println("타겟 스레드의 상태값: " + state);
			
			// NEW 상태인지 검사하여 스레드 구동시키기
			if(state == Thread.State.NEW) {
				targetThread.start(); //StatePrintThread가 start호출
			}
			
			// 종료 상태인지 검사하여 무한루프 빠져나가기
			if(state == Thread.State.TERMINATED) {
			//	targetThread.start();
				break;
			}
			
			try {
				Thread.sleep(500); // 0.5초 단위로 모니터링
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
}
