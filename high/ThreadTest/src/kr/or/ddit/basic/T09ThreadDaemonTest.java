package kr.or.ddit.basic;

public class T09ThreadDaemonTest {
	public static void main(String[] args) {
		
		Thread th = new AutoSaveThread(); 
		
		// 데몬 스레드로 설정하기 -> start() 호출하기 '전'에 설정한다! **
		th.setDaemon(true); // setDaemon이 없으면 종료되지 않는다. 일반스레드가 전부 사라지면 종료
		
		th.start();
		
		try {
			for(int i=1; i<=20; i++) {
				System.out.println("작업 " + i);
				
				Thread.sleep(1000);
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		
		System.out.println("메인 스레드 종료...");
	}
}

/**
 * 자동저장 기능을 제공하는 스레드 클래스
 * (3초에 한 번씩 저장하기)
 * @author PC-07
 *
 */
class AutoSaveThread extends Thread {
	
	public void save() { // 작업내용을 저장하는 기능
		System.out.println("작업 내용을 저장합니다...");
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(3000);
		} catch(InterruptedException ex) {
			ex.printStackTrace();
		}
			
		save(); 
		
	}
}
}