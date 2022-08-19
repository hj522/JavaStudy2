package kr.or.ddit.basic;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import kr.or.ddit.member.vo.MemberVO;

public class MyBatisTest {
	public static void main(String[] args) {
		
		// JDBC코딩을 대체
		
		/* MyBatis를 이용하여 DB자료를 처리하는 작업 순서 */
		
		/* 1. myBatis의 환경 설정파일을 읽어와 실행시킨다. */
		
		SqlSession sqlSession = null; // 핵심 객체 sqlSession을 사용하여 CRUD 작업
		
		try {
			
			// 1-1. xml설정문서 읽어오기
			// 설정파일의 인코딩 정보 설정. 경로 세팅(한글처리를 위해서..)
			Charset charset = Charset.forName("UTF-8");
			Resources.setCharset(charset);
			Reader rd = Resources.getResourceAsReader("config/mybatis-config.xml"); // xml파일을 읽어오기 위한 문자 기반 스트림 객체
			
			// 1-2. 위에서 읽어온 Reader객체를 이용하여 실제 작업을 진행할 객체 생성하기
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(rd);
			
			// 오토커밋 여부 설정
			sqlSession = sessionFactory.openSession(true); // 트랜잭션 관리(?)
			
			rd.close(); // 자원 반납
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		/* 2. 실행할 SQl문에 맞는 쿼리문을 호출해서 원하는 작업을 수행한다. */
		
		// 2-1. Insert 작업 연습
		System.out.println("insert 작업 시작...");
		
		// 1) 저장할 데이터를 VO에 담는다.
		MemberVO mv = new MemberVO();
		mv.setMemId("d001");
		mv.setMemName("김효정");
		mv.setMemTel("1111-1111");
		mv.setMemAddr("대전시 서구 탄방동");
		
		// 2) SqlSession객체 변수를 이용하여 해당 쿼리문을 실행한다.
		//	  -형식) sqlSession.insert("namespace값.id값", 파라미터객체);
		//	  -반환값: 성공한 레코드 수
		
		int cnt = sqlSession.insert("memberTest.insertMember", mv);
		
		if(cnt > 0) {
			System.out.println("insert 성공!");
		} else {
			System.out.println("insert 실패..");
		}
		
		// 2-2. update 연습
		System.out.println("update 작업 시작...");
		
		MemberVO mv2 = new MemberVO();
		mv2.setMemId("d001");
		mv2.setMemName("윤다영");
		mv2.setMemTel("666-666");
		mv2.setMemAddr("부산시 해운대구");
		
		// update()메서드의 반환값도 성공한 레코드 수이다.
		cnt = sqlSession.update("memberTest.updateMember", mv2);
		
		if(cnt > 0) {
			System.out.println("update 성공!");
		} else {
			System.out.println("update 실패!!");
		}
		
/*		// 2-3. delete 연습
		System.out.println("delete 작업 시작...");
		
		// delete메소드의 반환값: 성공한 레코드 수
		
		cnt = sqlSession.delete("memberTest.deleteMember", "d001");
		
		if(cnt > 0) {
			System.out.println("delete 성공!");
		} else {
			System.out.println("delete 실패!!");
		}
*/		
		// 2-4. select 연습
		// 1) 응답 결과가 여러 개일 경우
/*		System.out.println("select 연습 시작(결과가 여러 개일 경우...)");
		
		// 응답 결과가 여러 개일 경우에는 selectList 메소드를 사용한다.
		// 이 메소드는 여러 개의 레코드를 VO에 담은 후 이 VO 데이터를 List에 추가해 주는 작업을 자동으로 수행한다.
		List<MemberVO> memList = sqlSession.selectList("memberTest.memberAllList");
		for(MemberVO mv : memList) {
			System.out.println("ID : " + mv.getMemId());
			System.out.println("이름 : " + mv.getMemName());
			System.out.println("전화 : " + mv.getMemTel());
			System.out.println("주소 : " + mv.getMemAddr());
			System.out.println("======================================");
		}
		System.out.println("출력 끝..."); 
*/		
		// 2) 응답 결과가 1개일 경우
		System.out.println("select 연습 시작(결과가 1개인 경우)");
		
		// 응답결과가 1개가 확실할 경우에는 selectOne()메서드를 사용한다.
		MemberVO mv3 = (MemberVO)sqlSession.selectOne("memberTest.getMember", "d001");
		System.out.println("ID : " + mv3.getMemId());
		System.out.println("이름 : " + mv3.getMemName());
		System.out.println("전화 : " + mv3.getMemTel());
		System.out.println("주소 : " + mv3.getMemAddr());
		System.out.println("출력 끝...");
	}
}
