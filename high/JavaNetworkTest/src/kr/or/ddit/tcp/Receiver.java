package kr.or.ddit.tcp;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receiver extends Thread {
	
	private DataInputStream dis; // readUTF 사용을 위해
	
	
	public Receiver(Socket socket) {
		
		try {
			
			dis = new DataInputStream(socket.getInputStream());		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(dis != null) {
			
			try {
				
				System.out.println(dis.readUTF());	// 무한루프. 상대방이 writeUTF 쓸 때까지 블락 상태 -> 보내오는 메시지 읽어오기
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
