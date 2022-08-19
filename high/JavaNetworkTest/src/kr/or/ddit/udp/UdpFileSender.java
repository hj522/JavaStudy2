package kr.or.ddit.udp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class UdpFileSender {
	
	private DatagramSocket ds;
	private DatagramPacket dp;
	
	private InetAddress receiveAddr;
	private int port; // 데이터 보낼 때 사용할 포트번호
	private File file;
	
	
	public UdpFileSender(String receiveIp, int port) {
		
		try {
			ds = new DatagramSocket();
			
			this.port = port;
			receiveAddr = InetAddress.getByName(receiveIp);
			file = new File("e:/D_Other/c6.jpg");
			
			if(!file.exists()) {
				System.out.println("파일이 존재하지 않습니다.");
				System.exit(0);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/*	스타트 - 파일이름보내기 - 파일사이즈(크기)보내기 - 하나씩 읽어와서 보내기	*/
	
	public void start() throws Exception {
		
		long fileSize = file.length(); // 바이트 크기로 사이즈 저장
		long totalReadBytes = 0;
		
		long startTime = System.currentTimeMillis();
		
		try {
			// 전송시작을 알려주기 위한 문자열 전송
			sendData("start".getBytes());
			
			// 파일명 전송
			sendData(file.getName().getBytes());
			
			// 총 파일 사이즈 정보 전송하기
			sendData(String.valueOf(fileSize).getBytes()); // filesize가 long타입이라 문자열로 변환하는 작업 거치기
			
			FileInputStream fis = new FileInputStream(file);
			
			byte[] buffer = new byte[1000];
			while(true) {
				Thread.sleep(10); // 패킷 전송 간의 간격을 주기 위해서..
				
				int readBytes = fis.read(buffer, 0, buffer.length); // 1000바이트씩 파일 읽기
				if(readBytes == -1) { // 다 읽은 경우..
					break;
				}
				
				sendData(buffer, readBytes); // 읽어온 파일데이터 전송하기
				
				totalReadBytes += readBytes;
				System.out.println(" 진행 상태: " + totalReadBytes + "/" + fileSize + " Byte(s) (" + (totalReadBytes * 100 / fileSize) + " %)");
			}
			
			long endTime = System.currentTimeMillis();
			long diffTime = endTime - startTime;
			double transferSpeed = fileSize / diffTime;
			
			System.out.println("걸린 시간: " + diffTime + " (ms)");
			System.out.println("평균 전송 속도: " + transferSpeed + " Bytes/ms");
			System.out.println("전송 완료!");
			
			fis.close();
			ds.close();
			
		} catch(IOException ex) {
			ex.printStackTrace();
		} 		
	}

	/**
	 * 바이트 배열 데이터 전송하기
	 * @param bytes 전송할 바이트 배열
	 * @throws IOException 
	 */
	private void sendData(byte[] bytes) throws IOException {
		
		sendData(bytes, bytes.length);
	}
	
	/**
	 * 바이트 배열 데이터 전송하기
	 * @param data 보낼 바이트 배열 데이터
	 * @param length 보낼 바이트 데이터 크기
	 * @throws IOException
	 */
	private void sendData(byte[] data, int length) throws IOException {
		dp = new DatagramPacket(data, length, receiveAddr, port);
		ds.send(dp);
	}
	
	public static void main(String[] args) throws Exception {
		new UdpFileSender("192.168.142.4", 8888).start();
	}
}
