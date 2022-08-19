package kr.or.ddit.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MultiChatClient {

	public void clientStart() {
		
		
		Socket socket = null;
		
		try {
			socket = new Socket("192.168.142.6", 7777);
			
			System.out.println("서버에 연결되었습니다.");
			
			// 송신용 스레드
			ClientSender sender = new ClientSender(socket);
			
			// 수신용 스레드
			ClientReceiver receiver = new ClientReceiver(socket);
			
			sender.start();
			receiver.start();
			
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	class ClientSender extends Thread {
		private DataOutputStream dos;
		private Scanner scan = new Scanner(System.in);
		
		public ClientSender(Socket socket) {
			try {
				dos = new DataOutputStream(socket.getOutputStream());
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				if(dos != null) {
					// 시작하자마자 자신의 대화명을 서버로 전송
					System.out.println("대화명 >> ");
					dos.writeUTF(scan.nextLine());
				}
				
				while(dos != null) {
					// 키보드로 입력받은 메시지를 서버로 전송
					dos.writeUTF(scan.nextLine());
				}
				
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	class ClientReceiver extends Thread {
		private DataInputStream dis;
		
		public ClientReceiver(Socket socket) {
			try {
				dis = new DataInputStream(socket.getInputStream());
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			
			while(dis != null) {
				try {
					// 서버로부터 수신한 메시지 출력하기
					System.out.println(dis.readUTF());
					
				} catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new MultiChatClient().clientStart();
	}
}

// 수업시간에 작성한 멀티채팅 프로그램에 특정 사용자에게 귀속말 보내기 기능을 구현하시오.
// 사용 예) /w 사용자 메시지