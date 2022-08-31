package kr.or.ddit.comm.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.comm.dao.AtchFileDAOImpl;
import kr.or.ddit.comm.dao.IAtchFileDAO;
import kr.or.ddit.comm.vo.AtchFileVO;
import kr.or.ddit.util.MyBatisUtil;


public class AtchFileServiceImpl implements IAtchFileService {
	
	private static final String UPLOAD_DIR = "e:/D_Other/upload_files";
	
	private static IAtchFileService fileService;
	
	private IAtchFileDAO fileDao;
	
	public AtchFileServiceImpl() {
		fileDao = AtchFileDAOImpl.getInstance();
	}
	
	public static IAtchFileService getInstance() {
		if(fileService == null) {
			fileService = new AtchFileServiceImpl();
		}
		
		return fileService;
	}
	

	@Override
	public AtchFileVO saveAtchFileList(HttpServletRequest req) {
		
		// 웹애플리케이션 루트 디렉토리 기준 업로드 경로 설정하기
		File uploadDir = new File("e:/D_Other/upload_files"); // exist 사용해보려고 객체 생성
		if(!uploadDir.exists()) { // 없으면 만들어랏
			uploadDir.mkdir();
		}
		
		AtchFileVO atchFileVO = null;
		
		SqlSession session = MyBatisUtil.getInstance();
		
		try {
			String fileName = "";
			
			boolean isFirstFile = true; // 첫번째 파일 여부
			
			for(Part part: req.getParts()) {
				
				fileName = getFileName(part); // 파일명 추출
				
				if(fileName != null && !fileName.equals("")) { // 파일이름을 가져오는거니까,, 파일일 경우
					
					if(isFirstFile) {
						isFirstFile = false;
						
						// 파일 기본정보 저장하기
						atchFileVO = new AtchFileVO();
						
						fileDao.insertAtchFile(session, atchFileVO);
					}
					
					String orignFileName = fileName; // 원본파일명
					long fileSize = part.getSize(); // 파일 사이즈 가져오기
					String saveFileName = ""; // 저장 파일명
					String saveFilePath = ""; // 저장 파일 경로
					File storeFile = null; // 저장 파일 객체
					
					do {
						saveFileName = UUID.randomUUID().toString().replace("-", "");
						saveFilePath = UPLOAD_DIR + File.separator + saveFileName;
						storeFile = new File(saveFilePath);
						
					}while(storeFile.exists());
					
					part.write(saveFilePath); // 파일 저장
					
					// 확장자 추출     ex) coffee.jpg라면 jpg만 추출
					String fileExtension = orignFileName.lastIndexOf(".") < 0 ? "" : orignFileName.substring(orignFileName.lastIndexOf(".") + 1);
					
					atchFileVO.setStreFileNm(saveFileName);
					atchFileVO.setFileSize(fileSize);
					atchFileVO.setOrignlFileNm(orignFileName);
					atchFileVO.setFileStreCours(saveFilePath);
					atchFileVO.setFileExtsn(fileExtension);
					
					fileDao.insertAtchFileDetail(session, atchFileVO);
					
					part.delete(); // 임시 업로드 파일 삭제하기
					
					System.out.println("파일명: " + fileName + " 업로드 완료!");
					
					session.commit(); // 커밋
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally {
			session.close();
		}

		return atchFileVO; // 첨부파일 키값인 *atchfileId값이 들어있어서 리턴하도록 설정!
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

	
	@Override
	public List<AtchFileVO> getAtchFileList(AtchFileVO atchFileVO) {
		
		SqlSession session = MyBatisUtil.getInstance();
		
		List<AtchFileVO> atchFileList = null;

		try {
			atchFileList = fileDao.getAtchFileList(session, atchFileVO);
			
		} finally {
			session.close();
		}
		return atchFileList;
	}
	
	@Override
	public AtchFileVO getAtchFileDetail(AtchFileVO atchFileVO) {
		
		SqlSession session = MyBatisUtil.getInstance();
		
		AtchFileVO fileVO = null;

		try {
			fileVO = fileDao.getAtchFileDetail(session, atchFileVO);
			
		} finally {
			session.close();
		}
		return fileVO;
	}
}
