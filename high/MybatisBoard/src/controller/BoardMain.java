package controller;
// BoardMain

import java.util.List;
import java.util.Scanner;

import service.BoardServiceImpl;
import service.IBoardService;
import vo.BoardVO;

/*

위의 테이블을 작성하고 게시판을 관리하는
다음 기능들을 구현하시오.

기능 구현하기 ==> 전체 목록 출력, 새글작성, 수정, 삭제, 검색 
 
------------------------------------------------------------

게시판 테이블 구조 및 시퀀스

create table jdbc_board(
    board_no number not null,  -- 번호(자동증가)
    board_title varchar2(100) not null, -- 제목
    board_writer varchar2(50) not null, -- 작성자
    board_date date not null,   -- 작성날짜
    board_content clob,     -- 내용
    constraint pk_jdbc_board primary key (board_no)
);
create sequence board_seq
    start with 1   -- 시작번호
    increment by 1; -- 증가값
		
----------------------------------------------------------

// 시퀀스의 다음 값 구하기
//  시퀀스이름.nextVal

 */

public class BoardMain {
	
	private Scanner scan = new Scanner(System.in);

	private IBoardService boardService;
	
	public BoardMain() {
		boardService = BoardServiceImpl.getInstance();
	}
	
	// 메뉴 출력 
	public void displayMenu(){
		System.out.println();
		System.out.println("----------------------");
		System.out.println("  === 작 업 선 택 ===");
		System.out.println("  1. 글 작성");
		System.out.println("  2. 글 삭제");
		System.out.println("  3. 글 수정");
		System.out.println("  4. 글 목록");
		System.out.println("  5. 글 검색");
		System.out.println("  6. 작업 끝");
		System.out.println("----------------------");
		System.out.print("원하는 작업 선택 >> ");
	}
	
	public void start(){
		int choice;
		do{
			displayMenu(); //메뉴 출력
			choice = scan.nextInt(); // 메뉴번호 입력받기
			switch(choice){
				case 1 :  // 자료 입력
					insertBoard();
					break;
				case 2 :  // 자료 삭제
					deleteBoard();
					break;
				case 3 :  // 자료 수정
					updateBoard();
					break;
				case 4 :  // 전체 자료 출력
					displayBoardAll();
					break;
				case 5 :  // 자료 검색
					searchBoard();
					break;
				case 6 :  // 작업 끝
					System.out.println("작업을 마칩니다.");
					break;
				default :
					System.out.println("잘못 입력했습니다. 다시 입력하세요");
			}
		}while(choice!=5);
	}

	private void searchBoard() {
		scan.nextLine();
		System.out.println();
		
		System.out.println("검색할 글 정보를 입력하세요.");
		
		System.out.println("글 번호 >> ");
		int boardNo = scan.nextInt();
		
		System.out.println("작성자 >> ");
		String boardWriter = scan.nextLine().trim();
		
		System.out.println("제목 >> ");
		String boardTitle = scan.nextLine().trim();
		
		System.out.println("내용 >> ");
		String boardContent = scan.nextLine().trim();
		
		BoardVO bv = new BoardVO();
		bv.setBoardNo(boardNo);
		bv.setBoardWriter(boardWriter);
		bv.setBoardTitle(boardTitle);
		bv.setBoardContent(boardContent);
		
		List<BoardVO> boardList = boardService.searchBoardList(bv);
		
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println(" No.\t제 목\t작성자\t\t작성일\t내 용");
		System.out.println("-------------------------------------------------");
		
		if(boardList.size() == 0) {
			System.out.println(" 검색된 회원 정보가 없습니다.");
		} else {
			for(BoardVO bv2 : boardList) {
				System.out.println(bv2.getBoardNo() + "\t" + bv2.getBoardTitle() 
				+ "\t" + bv2.getBoardWriter() + "\t" + bv2.getBoardDate() + "\t" + bv2.getBoardContent());
			}
		}
		
		System.out.println("-------------------------------------------------");
		System.out.println("검색 끝!");
		
	}

	private void displayBoardAll() {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println(" No.\t제 목\t작성자\t\t작성일\t내 용");
		System.out.println("-------------------------------------------------");
		
		List<BoardVO> boardList = boardService.getAllBoardList();
		
		if(boardList.size() == 0) {
			System.out.println("해당 글이 없습니다.");
		} else {
			for(BoardVO bv : boardList) {
				System.out.println(bv.getBoardNo() + "\t" + bv.getBoardTitle() 
				+ "\t" + bv.getBoardWriter() + "\t" + bv.getBoardDate() + "\t" + bv.getBoardContent());
			}
		}
		
		System.out.println("-------------------------------------------------");
		System.out.println("글 목록 출력 완료");
	}

	private void deleteBoard() {
		System.out.println();
		System.out.println("삭제할 글 번호를 입력하세요.");
		System.out.println("글 번호 >> ");
		
		int boardNo = scan.nextInt();
		
		int cnt = boardService.removeBoard(boardNo);
		
		if(cnt > 0) {
			System.out.println(boardNo + "번 글 삭제 완료!");
		} else {
			System.out.println(boardNo + "번 글 삭제 실패");
		}
		
	}

	private void updateBoard() {
		
		boolean chk = false;
		
		int boardNo = 0;
		
		do {
			System.out.println();
			System.out.println("수정할 글 번호를 입력하세요.");
			System.out.println("글 번호 >> ");
			
			boardNo = scan.nextInt();
			
			chk = checkBoard(boardNo);
			
			if(chk == false) {
				System.out.println(boardNo + "번 글은 존재하지 않습니다. ");
				System.out.println("다시 입력해주세요.");
		}
			
	} while(chk == false);
		
		System.out.println("작성자 >> ");
		String boardWriter = scan.next();
		
		System.out.println("제목 >> ");
		String boardTitle = scan.next();
		
		scan.nextLine();
		
		System.out.println("내용 >> ");
		String boardContent = scan.next();
		
		
		BoardVO bv = new BoardVO();
		bv.setBoardNo(boardNo);
		bv.setBoardWriter(boardWriter);
		bv.setBoardTitle(boardTitle);
		bv.setBoardContent(boardContent);
		
		int cnt = boardService.modifyBoard(bv);
		
		if(cnt > 0) {
			System.out.println(boardNo + "번 글 수정 성공!");
		} else {
			System.out.println(boardNo + "번 글 수정 실패");
		}
	}
	

	private void insertBoard() {
		
		System.out.println("제목 >> ");
		String boardTitle = scan.next();

		System.out.println("이름 >> ");
		String boardWriter = scan.next();
		
		scan.nextLine();
		
		System.out.println("내용 >> ");
		String boardContent = scan.next();
		
		BoardVO bv = new BoardVO();
		bv.setBoardTitle(boardTitle);
		bv.setBoardWriter(boardWriter);
		bv.setBoardContent(boardContent);
		
		int cnt = boardService.registerBoard(bv);
		
		if (cnt > 0) {
			System.out.println(boardWriter + "님 글 등록 성공!");
		} else {
			System.out.println(boardWriter + "님 글 등록 실패!");
		}
	}
	
	private boolean checkBoard(int boardNo) {
		
		boolean chk = boardService.checkBoard(boardNo);
		
		return chk;
	}
	
	public static void main(String[] args) {
		BoardMain boardObj = new BoardMain();
		boardObj.start();
	}
	
}
