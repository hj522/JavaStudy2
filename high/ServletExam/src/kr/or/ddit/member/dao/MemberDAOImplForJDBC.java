package kr.or.ddit.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.util.JDBCUtil3;

public class MemberDAOImplForJDBC implements IMemberDAO {
	
// 싱글톤 패턴 사용
	
/*1*/
	private static IMemberDAO memDao; //인터페이스 타입의 변수 선언
	
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;

/*2*/
	private MemberDAOImplForJDBC() {
	}

/*3*/
	public static IMemberDAO GetInstance() {
	
		if(memDao == null) {
			memDao = new MemberDAOImplForJDBC();
		}
		
		return memDao;
	}
	
	@Override
	public int insertMember(MemberVO mv) {
		
		int cnt = 0;
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "INSERT INTO mymember (mem_id, mem_name, mem_tel, mem_addr, reg_dt)" + " VALUES (?, ?, ?, ?, sysdate)"; //쿼리문
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mv.getMemId());	 // 물음표에 들어갈 데이터들
			pstmt.setString(2, mv.getMemName());
			pstmt.setString(3, mv.getMemTel());
			pstmt.setString(4, mv.getMemAddr());
			
			cnt = pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("회원등록 과정 중 예외 발생!", ex);
		} finally {

			JDBCUtil3.close(conn, stmt, pstmt, rs);
			}
		return cnt;
	}

	@Override
	public boolean checkMember(String memId) {
		
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
			throw new RuntimeException("회원등록 확인 중 예외 발생!", ex);
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs); // 자원 반납!
		}
		return chk;
	}

	@Override
	public int updateMember(MemberVO mv)  {
		
		int cnt = 0;

		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "UPDATE mymember " + " SET mem_name = ? " + " ,mem_tel = ?" + " ,mem_addr = ? " + " WHERE mem_id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mv.getMemName());
			pstmt.setString(2, mv.getMemTel());
			pstmt.setString(3, mv.getMemAddr());
			pstmt.setString(4, mv.getMemId());
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("회원 수정 과정 중 예외 발생!", e);
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return cnt;
	}

	@Override
	public int deleteMember(String memId) {
		
		int cnt = 0;
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "delete from mymember where mem_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("회원 삭제 과정 중 예외 발생!", e);
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMemberList() {
		
		List<MemberVO> memList = new ArrayList<MemberVO>();
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "select * from mymember";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				MemberVO mv = new MemberVO(); // vo에 담아주기
				mv.setMemId(rs.getString("mem_id"));
				mv.setMemName(rs.getString("mem_name"));
				mv.setMemTel(rs.getString("mem_tel"));
				mv.setMemAddr(rs.getString("mem_addr"));
				
				memList.add(mv);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("전체회원 조회 중 예외 발생!", e);
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return memList;
	}

	@Override
	public List<MemberVO> searchMemberList(MemberVO mv) {
		
		List<MemberVO> memList = new ArrayList<MemberVO>();
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "select * from mymember where 1=1 ";
			
			// null도 아니고 빈 스트링도 아니면
			if(mv.getMemId() != null && !mv.getMemId().equals("")) {
				sql += " and mem_id = ? ";
			}
			
			if(mv.getMemName() != null && !mv.getMemName().equals("")) {
				sql += " and mem_name = ? ";
			}
			
			if(mv.getMemTel() != null && !mv.getMemTel().equals("")) {
				sql += " and mem_tel = ? ";
			}
			
			if(mv.getMemAddr() != null && !mv.getMemAddr().equals("")) {
				sql += " and mem_addr like '%' || ? || '%' ";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			int index = 1; //물음표위치
			
			if(mv.getMemId() != null && !mv.getMemId().equals("")) {
				pstmt.setString(index++, mv.getMemId());
			}
			
			if(mv.getMemName() != null && !mv.getMemName().equals("")) {
				pstmt.setString(index++, mv.getMemName());
			}
			
			if(mv.getMemTel() != null && !mv.getMemTel().equals("")) {
				pstmt.setString(index++, mv.getMemTel());
			}
			
			if(mv.getMemAddr() != null && !mv.getMemAddr().equals("")) {
				pstmt.setString(index++, mv.getMemAddr());
			}
			
			rs = pstmt.executeQuery();
			
		while(rs.next()) {
			MemberVO mv2 = new MemberVO();
			mv2.setMemId(rs.getString("mem_id"));
			mv2.setMemName(rs.getString("mem_name"));
			mv2.setMemTel(rs.getString("mem_tel"));
			mv2.setMemAddr(rs.getString("mem_addr"));
			
			memList.add(mv2);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		
		return memList;
	}
}
