package kr.or.ddit.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

// UDP - 연결이 필요 없다! 상대적으로 속도가 빠르다. but 신뢰성x
// receiver 먼저 실행
// DatagramSocket, DatagramPacket 클래스 이용. 바이트 배열을 담는 그릇 역할


// 서버 시간을 상대방에게 보내는 예제

public class UdpServer {
	
	private DatagramSocket ds;
	private DatagramPacket dp;
	private byte[] msg; // 패킷 수신을 위한 바이트 배열 선언
	
	public UdpServer() {
		try {
			ds = new DatagramSocket(8888); // 메시지 수신을 위한 포트번호 설정
		} catch(SocketException e) {
			e.printStackTrace();
		}
	}
	
	// 진입 메소드
	public void start() throws IOException {
		
		while(true) {
			// 데이터를 수신하기 위한 패킷을 생성한다.
			msg = new byte[1]; // 1바이트짜리 배열 초기화 
			dp = new DatagramPacket(msg, msg.length); // 패킷에 담기
			
			System.out.println("패킷 수신 대기중...");
			
			// 패킷을 통해 데이터를 수신(receive)한다.
			ds.receive(dp);
			/* 블락 상태.. 상대방이 소켓으로 나에게 데이터를 보내올 때까지 멈춤 */
			
			System.out.println("패킷 수신 완료");
			
			// 수신한 패킷으로부터 client의 IP주소와 Port정보를 얻는다.
			InetAddress address = dp.getAddress();
			int port = dp.getPort();
			
			// 서버의 현재 시간을 시/분/초 형태([hh:mm:ss])로 반환한다.
			SimpleDateFormat sdf = new SimpleDateFormat("[hh:mm:ss]");
			String time = sdf.format(new Date()); // time에 서버시간이 문자열로 저장된다.
			msg = time.getBytes(); /* getBytes(): 시간 문자열을 byte배열로 변환!! */
			
			// 패킷을 생성해서 client에게 전송(send)한다.
			dp = new DatagramPacket(msg, msg.length, address, port); // time을 바이트 배열로 담기. 몇 바이트까지 유효한지 length를 통해 알 수 있음 
			ds.send(dp); // 소켓 사용해서 보내기
		}
	}
	
	public static void main(String[] args) throws IOException {
		new UdpServer().start();
	}
}
