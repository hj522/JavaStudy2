package dao;

import java.util.List;

import vo.BoardVO;

public interface IBoardDAO {

	public int insertBoard(BoardVO bv);
	
	public boolean checkBoard(int boardNo);
	
	public int updateBoard(BoardVO bv);
	
	public int deleteBoard(int boardNo);
	
	public List<BoardVO> getAllBoardList();

	List<BoardVO> searchBoardList(BoardVO bv);
	
}
