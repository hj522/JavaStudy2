package kr.or.ddit.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

	/**
	 * db.properties 파일의 내용으로 DB정보를 설정하는 방법
	 * 방법1) Properties 객체 이용하기
	 * 
	 * 장점: 유지보수에 도움이 된다!
	 */

public class JDBCUtil2 {

	static Properties prop; // Properties 객체 변수 선언
	
	//static블럭: 처음 클래스가 로딩되는 시점(JDBCUtil 사용 시)에 딱 1번만 호출된다.
	static {
		
		prop = new Properties();
		
		try {
			prop.load(new FileInputStream("res/db.properties"));
			
			Class.forName(prop.getProperty("driver"));
	//		Class.forName("oracle.jdbc.driver.OracleDriver"); //클래스객체로리턴
			System.out.println("드라이버 로딩 완료!");
			
		} catch(ClassNotFoundException ex) {
			System.out.println("드라이버 로딩 실패!!!"); //드라이버를 다시 확인.. 스펠링 틀리는 경우多 
		} catch(IOException ex) {
			System.out.println("해당 파일이 없거나 입출력 오류입니다.");
			ex.printStackTrace();
		}
	}
	
	/**
	 * 커넥션 객체 생성하기
	 * @return
	 */
	public static Connection getConnection() {
		try {
//			return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "pc07", "java");			
			return DriverManager.getConnection(
					prop.getProperty("url"),
					prop.getProperty("username"),
					prop.getProperty("password"));
		} catch (SQLException ex) {
			System.out.println("DB 연결 실패..");
			ex.printStackTrace();
			return null; 
		}
	}
	
	
	/**
	 * 자원 반납 메소드
	 * @param conn
	 * @param stmt
	 * @param pstmt
	 * @param rs
	 */
	public static void close(Connection conn, Statement stmt, PreparedStatement pstmt, ResultSet rs) {
		if(rs != null)
			try {
			rs.close();
			} catch(SQLException ex) {
		}
		if(stmt != null)
			try {
			stmt.close();
			} catch(SQLException ex) {
		}
		if(pstmt != null)
			try {
			pstmt.close();
			} catch(SQLException ex) {
		}
		if(conn != null)
			try {
			conn.close();
			} catch(SQLException ex) {
		}
	}
}
