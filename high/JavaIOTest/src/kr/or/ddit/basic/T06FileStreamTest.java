package kr.or.ddit.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 파일 출력 예제
 * @author PC-07
 *
 */

public class T06FileStreamTest {
	public static void main(String[] args) {

		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream("e:/D_Other/out.txt");
			
			for(char ch='a'; ch<='z'; ch++) {  // a~z 1바이트씩 write
				fos.write(ch);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close(); // 안전하게 .. 닫아주기!! 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		// 파일 읽어오기(FileInputStream 사용)
		
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(new File("e:/D_Other/out.txt"));
			
			int data = 0;
			while((data = fis.read()) != -1) {
				System.out.print((char)data); // 1바이트씩 읽고 문자로 다시 형변환
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
