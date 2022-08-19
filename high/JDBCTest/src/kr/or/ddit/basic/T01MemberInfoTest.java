package kr.or.ddit.basic;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.log4j.Logger;

import kr.or.ddit.util.JDBCUtil3;

// 08/05 CRUD 작업 해보기~

/*
	JDBC 순서

	1. 드라이버 체크(옵션)
	2. Connection 객체 가져오기(연결이 잘 됐을 경우)
	3. Statement 객체 생성 <- sql을 넣기위해
	4. ResultSet
	5. 자원 반납(close)
*/



/*
	회원정보를 관리하는 프로그램을 작성하는데 
	아래의 메뉴를 모두 구현하시오. (CRUD기능 구현하기)
	(DB의 MYMEMBER테이블을 이용하여 작업한다.)
	
	* 자료 삭제는 회원ID를 입력 받아서 삭제한다.
	
	예시메뉴)
	----------------------
		== 작업 선택 ==
		1. 자료 입력			---> insert
		2. 자료 삭제			---> delete
		3. 자료 수정			---> update
		4. 전체 자료 출력			---> select
		5. 작업 끝.
	----------------------
	 
	   
// 회원관리 프로그램 테이블 생성 스크립트 
create table mymember(
    mem_id varchar2(8) not null,  -- 회원ID
    mem_name varchar2(100) not null, -- 이름
    mem_tel varchar2(50) not null, -- 전화번호
    mem_addr varchar2(128),    -- 주소
    reg_dt DATE DEFAULT sysdate, -- 등록일
    CONSTRAINT MYMEMBER_PK PRIMARY KEY (mem_id)
);

*/
public class T01MemberInfoTest {
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Scanner scan = new Scanner(System.in); 
	
	private static final Logger SQL_LOGGER = Logger.getLogger("log4jexam.sql.Query");
	private static final Logger PARAM_LOGGER = Logger.getLogger("log4jexam.sql.Parameter");
	private static final Logger RESULT_LOGGER = Logger.getLogger(T01MemberInfoTest.class);
	
	/**
	 * 메뉴를 출력하는 메서드
	 */
	public void displayMenu(){
		System.out.println();
		System.out.println("----------------------");
		System.out.println("  === 작 업 선 택 ===");
		System.out.println("  1. 자료 입력");
		System.out.println("  2. 자료 삭제");
		System.out.println("  3. 자료 수정");
		System.out.println("  4. 전체 자료 출력");
		System.out.println("  5. 작업 끝");
		System.out.println("----------------------");
		System.out.print("원하는 작업 선택 >> ");
	}
	
	/**
	 * 프로그램 시작 메서드
	 */
	public void start(){
		int choice;
		do{
			displayMenu(); //메뉴 출력
			choice = scan.nextInt(); // 메뉴번호 입력받기
			switch(choice){
				case 1 :  // 자료 입력
					insertMember();
					break;
				case 2 :  // 자료 삭제
					deleteMember();
					break;
				case 3 :  // 자료 수정
					updateMember();
					break;
				case 4 :  // 전체 자료 출력
					displayMemberAll();
					break;
				case 5 :  // 작업 끝
					System.out.println("작업을 마칩니다.");
					break;
				default :
					System.out.println("번호를 잘못 입력했습니다. 다시입력하세요");
			}
		}while(choice!=5);
	}
	
	
	/**
	 * 전체 회원 정보를 출력하는 메소드
	 */
	private void displayMemberAll() {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println(" ID\t이 름\t전화번호\t\t주  소");
		System.out.println("-------------------------------------------------");
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "select * from mymember";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			//한줄씩 접근
			while(rs.next()) {
				String memId = rs.getString("mem_id");
				String memName = rs.getString("mem_name");
				String memTel = rs.getString("mem_tel");
				String memAddr = rs.getString("mem_addr");

				System.out.println(memId + "\t" + memName + "\t" + memTel + "\t" + memAddr);
			}
			
			System.out.println("-------------------------------------------------");
			System.out.println("출력 작업 끝!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
	}

	/**
	 * 회원 정보를 삭제하는 메소드
	 */
	private void deleteMember() {
		System.out.println();
		System.out.println("삭제할 회원 정보를 입력하세요.");
		System.out.print("회원 ID >> ");
		
		String memId = scan.next();
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "delete from mymember where mem_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println(memId + " 회원 정보 삭제 성공~!");
			} else {
				System.out.println(memId + "회원 정보 삭제 실패...");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
	}

	
	/**
	 * 회원 정보를 수정하는 메소드
	 */
	private void updateMember() {
		
		boolean chk = false;
		
		String memId = ""; //회원아이디 저장할 변수
		
		//아이디 중복체크
		do {
			System.out.println();
			System.out.println("수정할 회원 정보를 입력하세요.");
			System.out.print("회원 ID >> ");
			
			memId = scan.next();
			
			chk = checkMember(memId);
			
			if(chk == false) { // true여야 수정작업을 할 수 있음(회원이 존재해야 정보수정이 되니까)
				System.out.println("회원 ID가 " + memId + "인 회원은 존재하지 않습니다.");
				System.out.println("다시 입력해 주세요.");
			}
			
		} while(chk == false);
		
		System.out.println("회원 이름 >> ");
		String memName = scan.next();
		
		System.out.print("회원 전화번호 >> ");
		String memTel = scan.next();

		scan.nextLine(); // 입력 버퍼 비우기
		
		System.out.print("회원 주소 >> ");
		String memAddr = scan.nextLine();
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "UPDATE mymember " + " SET mem_name = ? " + " ,mem_tel = ?" + " ,mem_addr = ? " + " WHERE mem_id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1, memName);
			pstmt.setNString(2, memTel);
			pstmt.setNString(3, memAddr);
			pstmt.setNString(4, memId);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println(memId + " 회원 정보 수정 성공!");
			} else {
				System.out.println(memId + " 회원 정보 수정 실패!!!!!!!!!!");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
	}

	/**
	 * 회원 정보를 추가하는 메소드
	 */
	private void insertMember() {
		
		boolean chk = false;
		
		String memId = ""; //회원아이디 저장할 변수
		
		//아이디 중복체크
		do {
			System.out.println();
			System.out.println("추가할 회원 정보를 입력하세요.");
			System.out.print("회원 ID >> ");
			
			memId = scan.next();
			
			chk = checkMember(memId);
			
			if(chk == true) { //아이디가 중복일 경우
				System.out.println("회원 ID가 " + memId + "인 회원은 이미 존재합니다.");
				System.out.println("다시 입력해 주세요.");
			}
			
		} while(chk == true);
		
		System.out.println("회원 이름 >> ");
		String memName = scan.next();
		
		System.out.print("회원 전화번호 >> ");
		String memTel = scan.next();

		scan.nextLine(); // 입력 버퍼 비우기
		
		System.out.print("회원 주소 >> ");
		String memAddr = scan.nextLine();
		
		// JDBC 코딩
		try {
			conn = JDBCUtil3.getConnection();
			// conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "pc07", "java"); // 연결 성공하면 커넥션타입의 객체가 리턴됨
			
			String sql = "INSERT INTO mymember (mem_id, mem_name, mem_tel, mem_addr, reg_dt)" + " VALUES (?, ?, ?, ?, sysdate)"; //쿼리문
			
			SQL_LOGGER.debug("쿼리: " + sql);
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);	 // 물음표에 들어갈 데이터들
			pstmt.setString(2, memName);
			pstmt.setString(3, memTel);
			pstmt.setString(4, memAddr);
			
			PARAM_LOGGER.debug("memId: " + memId + ", memName: " + memName + ", memTel: " + memTel + ", memAddr: " + memAddr);
			
			int cnt = pstmt.executeUpdate(); //int값으로 리턴
			/* 리턴타입이 다르기 때문에 두개중에 맞는걸로 선택해야함
			 insert update delete -> executeUpdate
			 select -> executeQuery 셀렉트한 결과를 리턴받음 */
			
			RESULT_LOGGER.info("실행 결과: " + cnt);
			
			
			if (cnt > 0) {
				System.out.println(memId + "회원 추가 작업 성공!");
			} else {
				System.out.println(memId + "회원 추가 작업 실패!!!");
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			// 자원 반납...
			JDBCUtil3.close(conn, stmt, pstmt, rs);
			}
		}
	
	
	/**
	 * 회원ID를 이용하여 회원이 존재하는지 알려주는 메소드
	 * @param memId
	 * @return 회원이 존재하면 true, 없으면 false
	 */
	private boolean checkMember(String memId) {
		
		boolean chk = false;
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "select count(*) as cnt from mymember" + " where mem_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			
			rs = pstmt.executeQuery(); //select라서 executeQuery
			
			int cnt = 0;
			
			//코드하나씩접근
			if(rs.next()) {
				cnt = rs.getInt("cnt"); //컬럼이름 or 인덱스값(1,2..) 넣기
			}
			
			if(cnt > 0) {
				chk = true; 
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs); // 자원 반납!
		}
		
		return chk;
	}

	public static void main(String[] args) {
		T01MemberInfoTest memObj = new T01MemberInfoTest();
		memObj.start();
	}
}

