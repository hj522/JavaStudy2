package kr.or.ddit.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MultiChatServer {
	
	// 대화명, 클라이언트의 Socket을 저장하기 위한 Map변수 선언
	Map<String, Socket> clients;
	
	public MultiChatServer() {
		// 동기화 처리 가능하도록 Map객체 생성
		clients = Collections.synchronizedMap(new HashMap<String, Socket>());
	}
	
	// 비지니스 로직을 처리하는 메소드
	// 시작 메소드
	public void serverStart() {
		
		Socket socket = null;
		
		try(ServerSocket serverSocket = new ServerSocket(7777)) {
			System.out.println("서버가 시작되었습니다.");
			
			while(true) {
				// 클라이언트의 접속을 대기한다.
				socket = serverSocket.accept();
				
				System.out.println("[" + socket.getInetAddress() + " : " + socket.getPort() + "] 에서 접속하였습니다.");
				
				// 메시지 전송 처리를 하는 스레드 생성 및 실행
				ServerReceiver receiver = new ServerReceiver(socket);
				receiver.start();
				
			}
			
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * 대화방 즉, Map에 저장된 전체 유저에게 안내 메시지를 전송하는 메소드
	 * @param msg 전송할 메시지
	 */
	public void sendMessage(String msg) {
		Iterator<String> it = clients.keySet().iterator();
		while(it.hasNext()) {
			try {
				String name = it.next(); // iterator를 이용해서 대화명(key값) 가져오기
				
				// 대화명에 해당하는 소켓 객체를 가져와서 DataOutputStream객체 생성하기
				DataOutputStream dos = new DataOutputStream(clients.get(name).getOutputStream());
				
				dos.writeUTF(msg); // 메시지 보내기
				
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 대화방 즉, Map에 저장된 전체 유저에게 대화 메시지를 전송하는 메소드
	 * @param msg 보낼 대화 메시지
	 * @param from 보내는 사람 대화명
	 */
	public void sendMessage(String msg, String from) {
		sendMessage("[" + from + "]" + msg); 
	}
	
	
	/**
	 * 특정 유저에게만 귓속말을 전송하는 메소드
	 * @param msg 메시지
	 * @param from 나
	 * @param to 상대방
	 */
	public void sendMessage(String msg, String from, String to) {
		
		// Map에 저장된 유저의 대화명 리스트 추출(key값 구하기)
		Iterator<String> it = clients.keySet().iterator();
		
		while(it.hasNext()) {
			
			try {
				String name = it.next(); //대화명 (key값) 가져오기
				
				boolean chk = false;
				
				// 대화명에 해당하는 Socket의 OutputStream객체 구하기
				if(name.equals(to)) {
					chk = true;
					DataOutputStream dos = new DataOutputStream(clients.get(to).getOutputStream());
					dos.writeUTF("["+from+">>"+to+"] "+ msg);//메시지 보내기
				} else {
					System.out.println("귓속말 전달에 실패하였습니다.");
				}
								
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// 별도의 내부 클래스 선언
	/**
	 * 서버에서 클라이언트로 메시지를 전송할 Thread를 Inner클래스로 정의
	 * Inner클래스에서는 부모 클래스의 멤버들을 직접 사용할 수 있다.
	 */
	class ServerReceiver extends Thread {
		private Socket socket;
		private DataInputStream dis;
		private String name;
		
		public ServerReceiver(Socket socket) {
			this.socket = socket;
			
			try {
				dis = new DataInputStream(socket.getInputStream()); // 수신용
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			
			try {
				// 서버에서는 클라이언트가 보내준 최초의 메시지. 즉, 대화명을 수신한다.
				name = dis.readUTF(); 
				
				// 대화명을 받아서 다른 모든 클라이언트에게 대화방 참여 메시지를 보낸다.
				sendMessage("#" + name + "님이 입장했습니다.");
				
				// 대화명과 소켓정보를 Map에 저장한다.
				clients.put(name, socket);
				
				System.out.println("현재 서버 접속자 수는 " + clients.size() + "명 입니다.");
				
				// 이후의 메시지 처리는 반복문으로 처리한다.
				// 한 클라이언트가 보낸 메시지를 다른 모든 클라이언트에게 보내준다.
				while(dis != null) {
//					sendMessage(dis.readUTF(), name);
					String msg = dis.readUTF();
					String msg2 ="";
					String[] line = msg.split(" ");

					if (line.length > 2) {
						if (line[0].equals("/w")) {

							for (int i=2; i<line.length; i++) {
								msg2 += (line[i]+" ");
							}
							sendMessage(msg2, name, line[1]); // 오버로딩
						}
					} else {
						sendMessage(msg, name); // 오버로딩
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 이 finally영역이 실행된다는 것은 클라이언트의 접속이 종료되었다는 의미이다.
				sendMessage(name + "님이 나가셨습니다.");
				
				// Map에서 해당 대화명을 삭제한다.
				clients.remove(name);
				
				System.out.println("[" + socket.getInetAddress() + " : " + socket.getPort() + "]에서 접속을 종료했습니다.");
				System.out.println("현재 접속자 수는 " + clients.size() + "명 입니다.");
			
			}
		}
	}
	
	public static void main(String[] args) {
		new MultiChatServer().serverStart();
	}
}
