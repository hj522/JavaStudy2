package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.util.HotelJDBCUtil;
import kr.or.ddit.util.JDBCUtil;

/* 호텔운영 프로그램 테이블 생성 스크립트
create table hotel_mng (
    room_num number not null,  -- 방번호
    guest_name varchar2(10) not null -- 투숙객 이름
);
*/

public class HotelManagementProgram {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Scanner scan = new Scanner(System.in);
	
	// 메뉴를 출력하는 메서드
	public void displayMenu() {
		System.out.println("*******************************************");
		System.out.println("어떤 업무를 하시겠습니까?");
		System.out.println("1.체크인 2.체크아웃 3.객실상태 4.업무종료");
		System.out.println("*******************************************");
		System.out.print("메뉴 선택 => ");
	}
	
	// 프로그램 시작 메소드
	public void hotelBookingStart() {
		System.out.println("**************************");
		System.out.println("호텔 문을 열었습니다.");
		System.out.println("**************************");
		System.out.println();

		int menuNum;
		
		do {
			displayMenu();
			menuNum = scan.nextInt();

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
			default:
				System.out.println("다시 입력해주세요.");
			}
		} while(menuNum!=4);
	}
	
	
	
private void roomAll() {
	System.out.println();
	
	try {
		conn = HotelJDBCUtil.getConnection();
		
		String sql = "select * from hotel_mng";
		
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			String roomNum = rs.getString("room_num");
			String guestName = rs.getString("guest_name");
			
			System.out.println("방 번호: " + roomNum + ", 투숙객: " + guestName);
		}
	} catch(SQLException e) {
		e.printStackTrace();
	} finally {
		JDBCUtil.close(conn, stmt, pstmt, rs);
	}
}

/**
* 체크아웃 메소드
*/
private void checkOut() {
	System.out.println();
	System.out.println("어느 방을 체크아웃 하시겠습니까?");
	System.out.println("방 번호 입력 => ");
	
	String roomNum = scan.next();
	
	try {
		conn = HotelJDBCUtil.getConnection();
		
		String sql = "delete from hotel_mng where room_num = ?";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, roomNum);
		
		int cnt = pstmt.executeUpdate();
		
		if(cnt > 0) {
			System.out.println("체크아웃 되었습니다.");
		} else {
			System.out.println(roomNum + "번 방에는 체크인 한 사람이 없습니다.");
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		HotelJDBCUtil.close(conn, stmt, pstmt, rs);
	}
}


/**
 * 체크인 메소드
 */
private void checkIn() {
	
	boolean chk = false;
	
	String roomNum = ""; // 방 번호 저장
	
	// 객실 중복체크
	do {
		System.out.println();
		System.out.println("어느 방에 체크인 하시겠습니까?");
		System.out.println("방 번호 입력 => ");
		
		roomNum = scan.next();
		
		chk = checkRoomNum(roomNum);
		
		if(chk == true) { // 이미 객실이 차있을 경우
			System.out.println(roomNum + "번 방에는 이미 사람이 있습니다.");
			System.out.println("다시 입력해 주세요.");
		}
		
	} while(chk == true); // 빈 객실이면 정보 입력
			
			System.out.print("이름 => ");
			String guestName = scan.next();
			scan.nextLine();
			
			try {
				conn = HotelJDBCUtil.getConnection();
				
				String sql = "INSERT INTO hotel_mng (room_num, guest_name)" + " VALUES (?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, roomNum);
				pstmt.setString(2, guestName);
				
				int cnt = pstmt.executeUpdate();
				
				if (cnt > 0) {
					System.out.println("체크인 되었습니다.");
				} else {
					System.out.println(roomNum + "번 방에는 이미 사람이 있습니다.");
			}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				HotelJDBCUtil.close(conn, stmt, pstmt, rs);
		}
	}		

/**
 * 방 번호를 이용하여 이미 예약이 찼는지 알려주는 메소드
 * @param roomNo
 * @return
 */
private boolean checkRoomNum(String roomNum) {
	
	boolean chk = false;
	
	try {
		conn = JDBCUtil.getConnection();
		
		String sql = "select count(*) as cnt from hotel_mng" + " where room_num = ?";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, roomNum);
		
		rs = pstmt.executeQuery();
		
		int cnt = 0;
		
		if(rs.next()) {
			cnt = rs.getInt("cnt");
		}
		
		if(cnt > 0) {
			chk = true;
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		HotelJDBCUtil.close(conn, stmt, pstmt, rs);
	}
	
	return chk;
	
}
	
	public static void main(String[] args) {
		HotelManagementProgram hotelObj = new HotelManagementProgram();
		hotelObj.hotelBookingStart();
	}
}
