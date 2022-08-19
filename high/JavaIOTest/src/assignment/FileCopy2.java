package assignment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy2 {
	public static void main(String[] args) {

		FileOutputStream fos = null;
		FileInputStream fis = null;
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		long startTime = System.currentTimeMillis();
		
		try {
			fis = new FileInputStream("c:/xampp/htdocs/jsstudy/images/c6.jpg");
			fos = new FileOutputStream("c:/xampp/htdocs/jsstudy/images/복사본_c6.jpg");
		
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(fos);
		
			int data = 0;
			
			while((data = bis.read()) != -1) {
				bos.write(data);
			//	fos.write(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("복사 시간:" +(endTime-startTime));
	}
}
