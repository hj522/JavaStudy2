package kr.or.ddit.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class T12ImageServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		resp.setCharacterEncoding("UTF-8"); // 문자인 경우에는 했지만 바이너리 데이터(이미지 데이터)에는 소용이 없으니까 안 씀!
		
		resp.setContentType("image/jpg");
		ServletOutputStream out = resp.getOutputStream(); //출력을 위한 기본 스트림
		
		FileInputStream fis = new FileInputStream(""); // <-보낼 파일 경로 입력
		
		BufferedInputStream bis = new BufferedInputStream(fis);
		BufferedOutputStream bos = new BufferedOutputStream(out);
		
		int BytesCnt = 0;
		
		while((BytesCnt = bis.read()) != -1) {	// 버퍼를 이용해서 IO작업 ~~
			bos.write(BytesCnt);
		}
	
		bis.close();
		bos.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

// post -> 회원가입 시 정보를 보낼 때 사용하면 됨! (폼 데이터 전송)
// get -> a태그(링크) 페이지 내용 가져오기 . ..