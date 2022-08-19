package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import util.JDBCUtil;
import util.MyBatisUtil;
import vo.BoardVO;

public class BoardDAOImpl implements IBoardDAO {
	
	private static IBoardDAO boardDao;
	
	private SqlSession sqlSession;
	
	private BoardDAOImpl() {
		sqlSession = MyBatisUtil.getInstance(true);
	}
	
	public static IBoardDAO getInstance() {
		if(boardDao == null) {
			boardDao = new BoardDAOImpl();
		}
		return boardDao;
	}
	
	@Override
	public int insertBoard(BoardVO bv) {
		
		int cnt = sqlSession.insert("board.insertBoard", bv);
		
		return cnt;
	}

	@Override
	public boolean checkBoard(int boardNo) {
		boolean chk = false;
		
		int cnt = sqlSession.selectOne("board.checkBoard", boardNo);
		
		if(cnt > 0) {
			chk = true;
		}
		
		return chk;
	}

	@Override
	public int updateBoard(BoardVO bv) {
		int cnt = sqlSession.update("board.insertBoard", bv);
		return cnt;
	}

	@Override
	public int deleteBoard(int boardNo) {
		int cnt = sqlSession.delete("board.deleteBoard", boardNo);
		return cnt;
	}

	@Override
	public List<BoardVO> getAllBoardList() {
		List<BoardVO> boardList = sqlSession.selectList("board.boardAllList");
		return boardList;
	}
	
	@Override
	public List<BoardVO> searchBoardList(BoardVO bv) {
		List<BoardVO> boardList = sqlSession.selectList("board.searchBoardList", bv);
		return boardList;
	}
}
