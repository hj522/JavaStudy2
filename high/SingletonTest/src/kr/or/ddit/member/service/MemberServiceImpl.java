package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.member.vo.MemberVO;
import sun.security.jca.GetInstance;

public class MemberServiceImpl implements IMemberService {
	
	private IMemberDAO memDao;

/*1*/
	private static IMemberService memService;

	
/*2*/	
//	public MemberServiceImpl() {
	private MemberServiceImpl() {
		memDao = MemberDAOImpl.GetInstance(); //객체 얻어오기
	}
	
/*3*/
	public static IMemberService GetInstance() {
		if(memService == null) {
			memService = new MemberServiceImpl();
		}
		return memService;
	}
	
	@Override
	public int RegisterMember(MemberVO mv) {
		int cnt = memDao.insertMember(mv);
		return cnt;
	}

	@Override
	public boolean checkMember(String memId) {
		boolean chk = memDao.checkMember(memId);
		return chk;
	}

	@Override
	public int modifyMember(MemberVO mv) {
		int cnt = memDao.updateMember(mv);
		return cnt;
	}

	@Override
	public int removeMember(String memId) {
		int cnt = memDao.deleteMember(memId);
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMemberList() {
		List<MemberVO> memList = memDao.getAllMemberList();
		return memList;
	}
	
	@Override
	public List<MemberVO> searchMemberList(MemberVO mv) {
		List<MemberVO> memList = memDao.searchMemberList(mv);
		return memList;
	}
}
