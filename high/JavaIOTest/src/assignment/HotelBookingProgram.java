package assignment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.sun.imageio.plugins.common.InputStreamAdapter;

public class HotelBookingProgram {

	private Scanner scan;
	private HashMap<String, Hotel> hotelBookingMap;
	
	private String fileName = "e:/D_Other/HotelData.bin";
	

	public HotelBookingProgram() {
		scan = new Scanner(System.in);
		hotelBookingMap = new HashMap<String, Hotel>();
		
		hotelBookingMap = load();
		
		// 파일이 없거나 입출력 오류일 때
		if(hotelBookingMap==null) { 
			hotelBookingMap = new HashMap<>();
		}
	}

	private void save() {
		
		ObjectOutputStream oos = null;
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fileName)); // 저장용 출력 스트림 객체 생성
			oos.writeObject(hotelBookingMap); // Map객체를 파일로 저장
			
			System.out.println("저장 완료~^_^");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(oos!=null)
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private HashMap<String, Hotel> load() {
		
		HashMap<String, Hotel> hMap = null;
		
		File file = new File(fileName);
		
		// 저장된 파일이 없을 경우
		if(!file.exists()) {
			return null;
		}
		
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new FileInputStream(file)); // 파일 입력용 스트림 객체 생성
			
			// 파일 내용을 읽어와 Map객체 변수에 저장
			hMap = (HashMap<String, Hotel>) ois.readObject();
			
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		} finally {
			if(ois!=null)
			try {
				ois.close();
			} catch (IOException e) {
			}
		}
		return hMap;
	}
	
	public void displayMenu() {
		System.out.println("*******************************************");
		System.out.println("어떤 업무를 하시겠습니까?");
		System.out.println("1.체크인 2.체크아웃 3.객실상태 4.업무종료 5.저장");
		System.out.println("*******************************************");
		System.out.print("메뉴 선택 => ");
	}

	public void hotelBookingStart() {
		System.out.println("**************************");
		System.out.println("호텔 문을 열었습니다.");
		System.out.println("**************************");
		System.out.println();

		while (true) {

			displayMenu();

			int menuNum = scan.nextInt();

			switch (menuNum) {
			case 1:
				checkIn(); // 등록(체크인)
				break;

			case 2:
				checkOut(); // 삭제(체크아웃)
				break;

			case 3:
				roomAll(); // 전체 출력
				break;

			case 4:
				System.out.println("**************************");
				System.out.println("호텔 문을 닫았습니다.");
				System.out.println("**************************");
				return;
				
			case 5:
				save();
				break;
				
			default:
				System.out.println("다시 입력해주세요.");
			}
		}
	}

	private void checkIn() {
		System.out.println();
		System.out.println("어느 방에 체크인 하시겠습니까?");
		System.out.println("방 번호 입력 => ");
		String roomNo = scan.next();

		if (hotelBookingMap.get(roomNo) != null) {
			System.out.println(roomNo + "번 방에는 이미 사람이 있습니다.");
			return;
		}

		System.out.print("이름 => ");
		String name = scan.next();

		scan.nextLine();

		hotelBookingMap.put(roomNo, new Hotel(roomNo, name));
		System.out.println("체크인 되었습니다.");
	}

	private void checkOut() {
		System.out.println();
		System.out.println("어느 방을 체크아웃 하시겠습니까?");
		System.out.println("방 번호 입력 => ");
		String roomNo = scan.next();

		if (hotelBookingMap.remove(roomNo) == null) {
			System.out.println(roomNo + "번 방에는 체크인 한 사람이 없습니다.");
		} else {
			System.out.println("체크아웃 되었습니다.");
		}
	}

	private void roomAll() {
		Set<String> keySet = hotelBookingMap.keySet();

		if (keySet.size() == 0) {
			System.out.println("체크인 한 사람이 없습니다.");
		} else {
			Iterator<String> it = keySet.iterator();
			while (it.hasNext()) {
				String roomNo = it.next();
				Hotel h = hotelBookingMap.get(roomNo);
				System.out.println("방 번호: " + h.getRoomNo() + ", 투숙객: " + h.getName());
			}
		}
	}
	
	public static void main(String[] args) {
		new HotelBookingProgram().hotelBookingStart();
	}
}

class Hotel implements Serializable { // 직렬화
	private String roomNo; // 방번호 
	private String name; // 이름
	
	public Hotel(String roomNo, String name) {
		super();
		this.roomNo = roomNo;
		this.name = name;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Hotel [roomNo=" + roomNo + ", name=" + name + "]";
	}

}
