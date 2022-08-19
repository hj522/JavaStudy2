package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.JDBCUtil;
import vo.BoardVO;

public class BoardDAOImpl implements IBoardDAO {
	
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	@Override
	public int insertBoard(BoardVO bv) {
		int cnt = 0;
		
		try {
			conn = JDBCUtil.getConnection();
			
			String sql = "INSERT INTO jdbc_board (board_no, board_title, board_writer, board_date, board_content) VALUES (board_seq.nextVal, ?, ?, sysdate, ?)"; //쿼리문
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bv.getBoardTitle());
			pstmt.setString(2, bv.getBoardWriter());
			pstmt.setString(3, bv.getBoardContent());
			
			cnt = pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("글 등록 과정 중 예외 발생!", ex);
		} finally {
			JDBCUtil.close(conn, stmt, pstmt, rs);
			}
		return cnt;
	}
	
	@Override
	public boolean checkBoard(int boardNo) {
		boolean chk = false;
		
		try {
			conn = JDBCUtil.getConnection();
			
			String sql = "select count(*) as cnt from jdbc_board" + " where board_no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
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
			throw new RuntimeException("글 등록 확인 중 예외 발생!", ex);
		} finally {
			JDBCUtil.close(conn, stmt, pstmt, rs); // 자원 반납!
		}
		return chk;
	}
	
	@Override
	public int updateBoard(BoardVO bv) {
		
		int cnt = 0;

		try {
			conn = JDBCUtil.getConnection();
			
			String sql = "UPDATE jdbc_board " + " SET board_writer = ?" + " ,"
					+ "board_title = ? " + " ,"+ "board_content = ? " + " WHERE board_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bv.getBoardWriter());
			pstmt.setString(2, bv.getBoardTitle());
			pstmt.setString(3, bv.getBoardContent());
			pstmt.setInt(4, bv.getBoardNo());
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("글 수정 과정 중 예외 발생!", e);
		} finally {
			JDBCUtil.close(conn, stmt, pstmt, rs);
		}
		return cnt;
	}
	
	@Override
	public int deleteBoard(int boardNo) {
		
	int cnt = 0;
		
		try {
			conn = JDBCUtil.getConnection();
			
			String sql = "delete from jdbc_board where board_no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("글 삭제 과정 중 예외 발생!", e);
		} finally {
			JDBCUtil.close(conn, stmt, pstmt, rs);
		}
		return cnt;
	}
	
	@Override
	public List<BoardVO> getAllBoardList() {
		
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		try {
			conn = JDBCUtil.getConnection();
			
			String sql = "select * from jdbc_board";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				BoardVO bv = new BoardVO(); // vo에 담아주기
				bv.setBoardNo(rs.getInt("board_no"));
				bv.setBoardTitle(rs.getString("board_title"));
				bv.setBoardWriter(rs.getString("board_writer"));
				bv.setBoardDate(rs.getDate("board_date"));
				bv.setBoardContent(rs.getString("board_content"));
				
				boardList.add(bv);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("전체 글 출력 중 예외 발생!", e);
		} finally {
			JDBCUtil.close(conn, stmt, pstmt, rs);
		}
		return boardList;
	}
	
	@Override
	public List<BoardVO> searchBoardList(BoardVO bv) {
		
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		try {
			conn = JDBCUtil.getConnection();
			
			String sql = "select * from jdbc_board where 1=1 ";
			
			// null도 아니고 빈 스트링도 아니면
			if(bv.getBoardNo() != 0) {
				sql += " and board_no = ?";
			}
			
			if(bv.getBoardWriter() != null && !bv.getBoardWriter().equals("")) {
				sql += " and board_writer like '%' || ? || '%' ";
			}
			
			if(bv.getBoardTitle() != null && !bv.getBoardTitle().equals("")) {
				sql += " and board_title like '%' || ? || '%' ";
			}
			
			if(bv.getBoardContent() != null && !bv.getBoardContent().equals("")) {
				sql += " and board_content like '%' || ? || '%' ";
			}
			
			if(bv.getBoardDate() != null && !bv.getBoardDate().equals("")) {
				sql += " and board_date = ?";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			int index = 1; //물음표위치
			
			if(bv.getBoardNo() != 0) {
				pstmt.setInt(index++, bv.getBoardNo());
			}
			
			if(bv.getBoardWriter() != null && !bv.getBoardWriter().equals("")) {
				pstmt.setString(index++, bv.getBoardWriter());
			}
			
			if(bv.getBoardTitle() != null && !bv.getBoardTitle().equals("")) {
				pstmt.setString(index++, bv.getBoardTitle());
			}
			
			if(bv.getBoardContent() != null && !bv.getBoardContent().equals("")) {
				pstmt.setString(index++, bv.getBoardContent());
			}
			
			if(bv.getBoardDate() != null && !bv.getBoardDate().equals("")) {
				pstmt.setDate(index++, bv.getBoardDate());
			}
			
			rs = pstmt.executeQuery();
			
		while(rs.next()) {
			BoardVO mv2 = new BoardVO();
			mv2.setBoardNo(rs.getInt("board_no"));
			mv2.setBoardWriter(rs.getString("board_writer"));
			mv2.setBoardTitle(rs.getString("board_title"));
			mv2.setBoardContent(rs.getString("board_content"));
			mv2.setBoardDate(rs.getDate("board_date"));
			
			boardList.add(mv2);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, stmt, pstmt, rs);
		}
		
		return boardList;
	}
	
}
