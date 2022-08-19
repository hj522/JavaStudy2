package kr.or.ddit.basic;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 성능 향상을 위한 보조스트림 예제 (바이트 기반의 Buffered스트림 사용 예제)
 * @author PC-07
 *
 */

// 버퍼: 임시 저장 공간 역할

public class T11BufferedIOTest {
	public static void main(String[] args) {
		
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		
		try {
			fos = new FileOutputStream("e:/D_Other/bufferTest.txt");
			
		// 버퍼의 크기를 지정하지 않으면 기본적으로 버퍼의 크기가 8192byte(8kb)로 설정된다.
		// 버퍼 크기가 5인 스트림 생성
			bos = new BufferedOutputStream(fos, 5); 
			
			for(char ch='1'; ch<='9'; ch++) {
				bos.write(ch);
			}
			// 버퍼 공간이 다 차면 내보냄
			bos.flush(); // 작업을 종료하기 전에 버퍼에 남아있는 데이터를 모두 출력시킨다.(close시 자동 호출)
			System.out.println("작업 끝...");
			
		} catch(IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
