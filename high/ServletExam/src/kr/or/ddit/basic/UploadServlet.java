package kr.or.ddit.basic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
// 아파치 commons를 이용한 업로드 방법

@WebServlet("/upload.do")
public class UploadServlet extends HttpServlet {

	private static final String UPLOAD_DIR = "upload_files";
	
	// 메모리 임계크기(이 크기가 넘어가면 레파지토리의 위치에 임시파일로 저장됨)
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB가 넘어가면 임시파일로 저장
	// 파일 한 개당 최대 크기
	private static final long MAX_FILE_SIZE = 1024 * 1024 * 40; //40MB
	// 요청 파일의 최대 크기
	private static final long MAX_REQUEST_SIZE = 1024 * 1024 * 50;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
/*		System.out.println("=============================");
		
//		Reader rd = new InputStreamReader(req.getInputStream());
//		BufferedReader br = new BufferedReader(rd);
		
		// 위 2줄을 1줄로 만들어서 사용..
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		
		String readLine = "";
		while((readLine = br.readLine()) != null) {
			System.out.println(readLine);
		}
		
		System.out.println("=============================");
*/
		
		// Multipart 파싱 전 파라미터 정보 조회해 보기
		System.out.println("Multipart 파싱 전 => " + req.getParameter("sender"));
		
		if(ServletFileUpload.isMultipartContent(req)) { // 멀티파트가 맞으면
			
			// 폼 필드 데이터 저장용 맵
			Map<String, String> formMap = new HashMap<String, String>();
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(MEMORY_THRESHOLD);
			factory.setRepository(new File(System.getProperty("java.io.tmpdir"))); // 한계치를 넘었을 때 어디에 저장할지 설정 
			System.out.println("임시 저장 위치: " + System.getProperty("java.io.tmpdir"));
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(MAX_FILE_SIZE);
			upload.setSizeMax(MAX_REQUEST_SIZE);
			
			// 웹애플리케이션 루트 디렉토리 기준 업로드 경로 설정하기
			String uploadPath = getServletContext().getRealPath("/") + File.separator + UPLOAD_DIR; // getrealpath: ServletExam (??)
			
			File uploadDir = new File(uploadPath); // exist 사용해보려고 객체 생성
			if(!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			
			try {
				
				List<FileItem> formItems = upload.parseRequest(req); // 리퀘스트를 파싱
				
				if(formItems != null && formItems.size() > 0) { 
					for(FileItem item : formItems) {
						if(!item.isFormField()) { // 파일인 경우 (이게 폼필드가 아니면)
							
							String fileName = item.getName();
							String filePath = uploadPath + File.separator + fileName;
							File storeFile = new File(filePath);
							
							item.write(storeFile); // 내가 원하는 경로로 업로드 파일 저장. write-실제 업로드 시킬때! 
							System.out.println("업로드 완료됨 => 파일명: " + fileName);
						} else { // sender같이 일반 폼 데이터인 경우
							// 폼 필드의 값이 한글인 경우에는 해당 문자열을 적절히 변환을 해주어야 한다.
							formMap.put(item.getFieldName(), item.getString("UTF-8"));
						}
					}
				}
				
			} catch(Exception ex) {
				ex.printStackTrace();
			}
			
			// Multipart 파싱 후 파라미터 정보 조회해 보기
			System.out.println("Multipart 파싱 후 => " + formMap.get("sender"));
			
			resp.setContentType("text/html");
			resp.getWriter().print("업로드 서블릿 종료됨!");
		}
	}
}
