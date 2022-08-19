package kr.or.ddit.basic;

import java.io.FileInputStream;
import java.io.IOException;

public class T05FileStreamTest {
	public static void main(String[] args) {
		
		// 파일을 읽기 위한 스트림 객체 생성
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream("e:/D_Other/test.txt");
			
			int data; // 읽은 데이터 저장
			
			// 읽어온 값이 -1이면 파일의 끝까지 읽었다는 의미!
			while((data = fis.read()) != -1) {
				System.out.print((char) data); // 1바이트씩,, 읽어온 자료를 다시 문자로 변환해서 출력하기
			}
			
			fis.close(); // 작업 완료 후 스트림 닫기
			
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}









