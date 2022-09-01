package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.comm.service.AtchFileServiceImpl;
import kr.or.ddit.comm.service.IAtchFileService;
import kr.or.ddit.comm.vo.AtchFileVO;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVO;

// 서블릿으로 update controller 생성해봄..

/**
 * Servlet implementation class UpdateMemberController
 */
@MultipartConfig
@WebServlet("/member/update.do")
public class UpdateMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String memId = req.getParameter("memId");
		
		// 1. 서비스 객체 생성하기
		IMemberService memService = MemberServiceImpl.getInstance();
		MemberVO mv = memService.getMember(memId); 
		
		if(mv.getAtchFileId() > 0) { // 첨부파일이 존재하면
			// 1-2. 첨부파일 정보 조회
			IAtchFileService fileService = AtchFileServiceImpl.getInstance();

			AtchFileVO fileVO = new AtchFileVO();
			fileVO.setAtchFileId(mv.getAtchFileId());
			
			List<AtchFileVO> atchFileList = fileService.getAtchFileList(fileVO);
			
			req.setAttribute("atchFileList", atchFileList);
		}
		
		req.setAttribute("mv", mv);

		req.getRequestDispatcher("/view/member/updateForm.jsp").forward(req, resp); //첨부파일이 존재하면 저장
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 1. 요청파라미터 정보 가져오기
		String memId = req.getParameter("memId");
		String memName = req.getParameter("memName");
		String memTel = req.getParameter("memTel");
		String memAddr = req.getParameter("memAddr");
		
		String atchFileId = req.getParameter("fileId");
		
		// 2. 서비스 객체 생성하기
		IMemberService memService = MemberServiceImpl.getInstance();
		IAtchFileService fileService = AtchFileServiceImpl.getInstance();
				
		// 첨부파일 저장하기
		AtchFileVO atchFileVO = fileService.saveAtchFileList(req);
		
		// 3. 회원 정보 수정하기
		MemberVO mv = new MemberVO();
		mv.setMemId(memId);
		mv.setMemName(memName);
		mv.setMemTel(memTel);
		mv.setMemAddr(memAddr);
		
		if(atchFileVO == null) { // 새로운 첨부파일이 존재하지 않으면..
			// 기본 첨부파일 ID 설정하기
			mv.setAtchFileId(Long.parseLong(atchFileId)); // 기존파일을 유지할지 새로운 파일로 바꿀지 
		} else {
			// 신규 첨부파일ID 설정하기
			mv.setAtchFileId(atchFileVO.getAtchFileId());
		}
		
		int cnt = memService.modifyMember(mv);
		
		String msg = "";
		
		if(cnt > 0) {
			msg = "성공";
		} else {
			msg = "실패";
		}
		
//		req.setAttribute("msg", msg); // 리퀘스트 저장 내용이 응답하기 전까지만 유지가 된다.
		
		HttpSession session = req.getSession();
		session.setAttribute("msg", msg);
		
		// 4. 목록 조회 화면으로 이동
		String redirectUrl = req.getContextPath() + "/member/list.do";
		
		resp.sendRedirect(redirectUrl);
	}
}

