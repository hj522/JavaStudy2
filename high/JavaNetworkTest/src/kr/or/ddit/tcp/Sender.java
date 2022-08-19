package kr.or.ddit.tcp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Sender extends Thread {  // 스레드 클래스 정의
	
	private Scanner scan;
	private String name;
	private DataOutputStream dos;
	
	// 채팅 메시지를 보내는 사람
	public Sender(Socket socket) {
		
		name = "[" + socket.getInetAddress() + " : " + socket.getLocalPort() + "]"; // 채팅 시 상대방이 누군지 알아야함. 소켓 IP주소+포트번호를 꺼내와서 닉네임으로 처리 
		
		scan = new Scanner(System.in); // 채팅 내용 입력 - 스캐너로 처리
		
		try {
			dos = new DataOutputStream(socket.getOutputStream()); // 엔터칠때마다 한 줄씩 읽어오기.. writeUTF메소드
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	// void run부터 실행 시작 
	@Override
		public void run() {
		while(dos != null) {
			
			try {
				dos.writeUTF(name + " >>> " + scan.nextLine()); // 무한루프 -> 타이핑+엔터 -> 블락 풀리고 accept. 내용 전달(메시지 전송)
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
