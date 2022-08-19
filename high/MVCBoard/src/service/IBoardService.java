package service;

import java.util.List;

import vo.BoardVO;

public interface IBoardService {

	public int registerBoard(BoardVO bv); // 작성
	
	public boolean checkBoard(int boardNo); // 해당글이있는지조회
	
	public int modifyBoard(BoardVO bv); // 수정
	
	public int removeBoard(int boardNo); // 삭제
	
	public List<BoardVO> getAllBoardList(); // 전체 글 목록
	
	public List<BoardVO> searchBoardList(BoardVO bv); // 검색
	
	
}
