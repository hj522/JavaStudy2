package kr.or.ddit.basic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 3, maxFileSize = 1024 * 1024 * 40, maxRequestSize = 1024 * 1024 * 50)
@WebServlet("/upload2.do")
public class UploadServlet2 extends HttpServlet {
	// upload니까 post사용
	
	private static final String UPLOAD_DIR = "upload_files";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		// Multipart 파싱 전 파라미터 정보 조회해 보기
		System.out.println("Multipart 파싱 전 => " + req.getParameter("sender"));
		
		// 웹애플리케이션 루트 디렉토리 기준 업로드 경로 설정하기
					String uploadPath = getServletContext().getRealPath("/") + File.separator + UPLOAD_DIR; // getrealpath: ServletExam (??)
					
					File uploadDir = new File(uploadPath); // exist 사용해보려고 객체 생성
					if(!uploadDir.exists()) {
						uploadDir.mkdir();
					}
					
					try {
						String fileName = ""; // 파일 이름을 저장하기 위한 변수
						
						for(Part part : req.getParts()) { // 서블릿3.0에는 getparts가 추가됨
							System.out.println(part.getHeader("content-disposition"));
							
							fileName = getFileName(part); // 파일 이름을 알아내는 작업
							
							if(fileName != null && !fileName.equals("")) { // empty가 아닐 때.. 파일선택은했는데 첨부를 안했을때(?)
								part.write(uploadPath + File.separator + fileName);
								System.out.println("파일명: " + fileName + "업로드 완료!");
							}
						}
						
					} catch(FileNotFoundException ex) {
						ex.printStackTrace();
					}
					
					System.out.println("파라미터 값: " + req.getParameter("sender"));
					
					resp.setContentType("text/html");
					resp.getWriter().print("업로드 완료..!!!");
				}

	/**
	 * Part객체 파싱하여 파일 이름 추출하기
	 * @param part Part객체
	 * @return 파일명 : 존재하지 않으면 null 리턴(폼필드인 경우...)
	 */
	private String getFileName(Part part) {
	/*
	 	Content-Disposition: form-data
	 	Content-Disposition: form-data; name="fieldName"
	 	Content-Disposition: form-data; name="fieldName"; filename="a.jpg"
	 */
		
		for(String content : part.getHeader("Content-Disposition").split(";")) {
			
			if(content.trim().startsWith("filename")) {
				return content.substring(content.indexOf("=") + 1).trim().replace("\"", "");
			}
		}
		
		return null; // filename이 존재하지 않는 경우..(폼필드)
	}
}
