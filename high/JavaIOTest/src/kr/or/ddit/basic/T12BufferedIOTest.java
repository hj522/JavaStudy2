package kr.or.ddit.basic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 문자기반 스트림을 위한 Buffered스트림 사용 예제
 * @author PC-07
 *
 */

public class T12BufferedIOTest {
	public static void main(String[] args) {
		
		FileReader fr = null;
		BufferedReader br = null; // 보조 스트림 이용
		
		try {
		// 이클립스에서 만든 자바프로그램이 실행되는 기본 위치는 해당 '프로젝트 폴더'가 기본 위치가 된다.
			fr = new FileReader("./src/kr/or/ddit/basic/T11BufferedIOTest.java");
			
			br = new BufferedReader(fr);
			
			// 한줄씩 읽을 수 있도록 해주는 readLine()을 이용한다.
			String temp = ""; // 리턴받기위해
			int cnt = 1; // 줄번호 확인용
			
			// string값.. 더이상 읽을 수 없으면 null리턴
			while((temp = br.readLine()) != null) {
				System.out.printf("%4d : %s\n", cnt++, temp);
			}

			// br 없이 쓸때..
// 			int data = 0;
//			while((data = fr.read()) != -1) {
//				System.out.print((char) data);
//			}
			
		} catch(IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				br.close();
			//	fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
