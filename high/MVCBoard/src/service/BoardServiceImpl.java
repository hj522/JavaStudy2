package service;

import java.util.List;

import dao.BoardDAOImpl;
import dao.IBoardDAO;
import vo.BoardVO;

public class BoardServiceImpl implements IBoardService {
	
	private IBoardDAO boardDao =new BoardDAOImpl();

	@Override
	
	public int registerBoard(BoardVO bv) {
		int cnt = boardDao.insertBoard(bv);
		return cnt;
	}

	@Override
	public boolean checkBoard(int boardNo) {
		boolean chk = boardDao.checkBoard(boardNo);
		return chk;
	}

	@Override
	public int modifyBoard(BoardVO bv) {
		int cnt = boardDao.updateBoard(bv);
		return cnt;
	}

	@Override
	public int removeBoard(int boardNo) {
		int cnt = boardDao.deleteBoard(boardNo);
		return cnt;
	}

	@Override
	public List<BoardVO> getAllBoardList() {
		List<BoardVO> boardList = boardDao.getAllBoardList();
		return boardList;
	}

	@Override
	public List<BoardVO> searchBoardList(BoardVO bv) {
		List<BoardVO> boardList = boardDao.searchBoardList(bv);
		return boardList;
	}

}
