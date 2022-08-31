package kr.or.ddit.comm.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.comm.vo.AtchFileVO;

// 공통 첨부파일 개념으로 사용

public interface IAtchFileDAO {
	
	/**
	 * 첨부파일 저장
	 * @param atchFileVO
	 * @return
	 */
	public int insertAtchFile(SqlSession session, AtchFileVO atchFileVO); 
	
	/**
	 * 첨부파일 세부 정보 저장
	 * @param atchFileVO
	 * @return
	 */
	public int insertAtchFileDetail(SqlSession session, AtchFileVO atchFileVO); 
	
	/**
	 * 첨부파일 목록 조회하기
	 * @param atchFileVO
	 * @return
	 */
	public List<AtchFileVO> getAtchFileList(SqlSession session, AtchFileVO atchFileVO);
	
	/**
	 * 첨부파일 세부정보 조회하기
	 * @param atchFileVO
	 * @return 첨부파일 세부정보를 담은 AtchFileVO객체
	 */
	public AtchFileVO getAtchFileDetail(SqlSession session, AtchFileVO atchFileVO);
}
