package kr.or.ddit.comm.vo;

import java.util.Date;

public class AtchFileVO {

	// shift alt A 누르면 한번에 타이핑 가능
	// 드래그 하고 ctr shift f 누르면 간격 정리됨
	
	private long atchFileId = -1; 	// 첨부파일ID
	private Date creatDt; 			// 생성일자
	private int fileSn = 1; 		// 파일 순번
	private String fileStreCours;	// 파일저장경로
	private String streFileNm; 		// 저장파일이름
	private String orignlFileNm;	// 원본파일이름
	private String fileExtsn; 		// 파일확장자
	private String fileCn = "";			// 파일내용
	private long fileSize = 0; 		// 파일크기
	
	
	public long getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(long atchFileId) {
		this.atchFileId = atchFileId;
	}
	public Date getCreatDt() {
		return creatDt;
	}
	public void setCreatDt(Date creatDt) {
		this.creatDt = creatDt;
	}
	public int getFileSn() {
		return fileSn;
	}
	public void setFileSn(int fileSn) {
		this.fileSn = fileSn;
	}
	public String getFileStreCours() {
		return fileStreCours;
	}
	public void setFileStreCours(String fileStreCours) {
		this.fileStreCours = fileStreCours;
	}
	public String getStreFileNm() {
		return streFileNm;
	}
	public void setStreFileNm(String streFileNm) {
		this.streFileNm = streFileNm;
	}
	public String getOrignlFileNm() {
		return orignlFileNm;
	}
	public void setOrignlFileNm(String orignlFileNm) {
		this.orignlFileNm = orignlFileNm;
	}
	public String getFileExtsn() {
		return fileExtsn;
	}
	public void setFileExtsn(String fileExtsn) {
		this.fileExtsn = fileExtsn;
	}
	public String getFileCn() {
		return fileCn;
	}
	public void setFileCn(String fileCn) {
		this.fileCn = fileCn;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
}

