package kr.or.ddit.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpFileSender {
	
	// TCP방식! 연결 기반
	// 서버소켓과 소켓  필요
	
	/*
		서버는 클라이언트가 접속했을 때
		서버 컴퓨터의 특정폴더에 있는 파일을 클라이언트로 전송한다.
	 */
	
	private ServerSocket server;
	private Socket socket;
	private FileInputStream fis;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	
	public void serverStart() {
		
		try {
			server = new ServerSocket(7777);
			System.out.println("서버 준비 완료...");
			
			String downDir = "e:/D_Other/";
			
			File file = null;
			
			while(true) {
				
				System.out.println("파일 전송 대기 중...");
				socket = server.accept(); 
				
				System.out.println("요청파일 존재여부 체크 중...");
				
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				
				
				file = new File(downDir + dis.readUTF());
				
				if(!file.exists()) {
					
					System.out.println("요청파일(" + file.getName() + ") 존재하지 않음");
					dos.writeUTF("요청파일(" + file.getName() + ") 존재하지 않습니다");
					
					dos.close();
					socket.close();
					
					continue;
					
				} else {
					
					dos.writeUTF("OK"); // 요청파일 확인했음을 알림
					System.out.println("요청파일(" + file.getName() + ") 전송 시작!");
				}
				
				fis = new FileInputStream(file);
				
				//입출력 향상을 위해 버퍼 기능 사용
				BufferedInputStream bis = new BufferedInputStream(fis);
				BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
				
				int data = 0;
				while((data = bis.read()) != -1) {
					bos.write(data);
				}
				
				bis.close();
				bos.close();
				
				dis.close();
				dos.close();
				
				System.out.println("파일 전송 완료~");
			}
					
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new TcpFileSender().serverStart();
	}
}
