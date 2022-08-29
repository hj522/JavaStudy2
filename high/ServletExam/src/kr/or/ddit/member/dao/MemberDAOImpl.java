package kr.or.ddit.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.util.MyBatisUtil;

//mybatis사용
public class MemberDAOImpl implements IMemberDAO {

	private static IMemberDAO memDao; //싱글톤
	
	private SqlSession SqlSession;
	
	private MemberDAOImpl() {
		SqlSession = MyBatisUtil.getInstance(true);
	}
	
	public static IMemberDAO getInstance() {
		if(memDao == null) {
			memDao = new MemberDAOImpl();
		}
		return memDao;
	}
	
	@Override
	public int insertMember(MemberVO mv) {
		
		int cnt = SqlSession.insert("member.insertMember", mv);
		
		return cnt;
	}

	@Override
	public boolean checkMember(String memId) {
		boolean chk = false;
		
		int cnt = SqlSession.selectOne("member.checkMember", memId);
		
		if(cnt > 0) {
			chk = true;
		}
		
		return false;
	}

	@Override
	public int updateMember(MemberVO mv) {
		int cnt = SqlSession.update("member.updateMember", mv);
		return cnt;
	}

	@Override
	public int deleteMember(String memId) {
		int cnt = SqlSession.delete("member.deleteMember", memId);
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMemberList() {
		List<MemberVO> memList = SqlSession.selectList("member.memberAllList");
		return memList;
	}
	
	@Override
	public List<MemberVO> searchMemberList(MemberVO mv) {
		List<MemberVO> memList = SqlSession.selectList("member.searchMemberList", mv);
		return memList;
	}

	@Override
	public MemberVO getMember(String memId) {
		
		MemberVO memVO = SqlSession.selectOne("member.getMember", memId);
		
		return memVO;
	}
}
