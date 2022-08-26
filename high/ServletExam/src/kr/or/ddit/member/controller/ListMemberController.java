package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVO;

// get방식; 정보 달라고 요청하기
// post방식; 서버쪽으로 정보 보내주기


@WebServlet(value = "/member/list.do") // url패턴 넣어주기
public class ListMemberController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 1. 서비스 객체 생성하기
		IMemberService memService = MemberServiceImpl.getInstance();
		
		// 2. 회원 목록 조회
		List<MemberVO> memList = memService.getAllMemberList();
		
		req.setAttribute("memList", memList);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/view/member/list.jsp");
		
		dispatcher.forward(req, resp); //  여기까지가 컨트롤러(서블릿이) 하는 역할! 뷰 페이지로 전달.
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
