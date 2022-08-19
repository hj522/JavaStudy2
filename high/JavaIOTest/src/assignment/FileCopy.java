package assignment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {
	public static void main(String[] args) {

		FileOutputStream fos = null;
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream("e:/D_Other/Tulips.jpg");
			fos = new FileOutputStream("e:/D_Other/복사본_Tulips.jpg");
		
			int data = 0;
			
			while((data = fis.read()) != -1) {
				fos.write(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
