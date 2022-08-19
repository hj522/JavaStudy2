package assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class LottoProgram {
	private Scanner scanner;
	private Scanner scan;
	
	public LottoProgram() {
		scanner = new Scanner(System.in);
		Random random = new Random();
	}
	
	public void displayMenu() {
		System.out.println();
		System.out.println("1. Lotto 구입");
		System.out.println("2. 프로그램 종료");
		System.out.println("==========================");
		System.out.println("메뉴 선택: ");
	}
		
	public void LottoStart() {
		System.out.println("==========================");
		System.out.println("      Lotto 프로그램");
		System.out.println("--------------------------");

		while(true) {
				
			displayMenu();
				
			int menuNum = scanner.nextInt();
			
			switch(menuNum) {
			case 1: buy();
				break;
			case 2:
				System.out.println("감사합니다.");
				return;
			default:
				System.out.println("다시 입력해주세요.");
			}

		}
	}
		
		private void buy() {
			
		System.out.println();
		System.out.println("Lotto 구입 시작");
		System.out.println();
		System.out.println("(1000원에 로또 번호 하나입니다.)");
		System.out.println("금액 입력: ");
		scanner.nextLine();
		
		int won = scanner.nextInt();
		
		Set<Integer> intNum = new HashSet<Integer>();
					
		System.out.println();
		System.out.println("행운의 로또번호는 아래와 같습니다.");

		int cnt = 0;
		
		for (int i=1; i<=won/1000; i++) {
			intNum.clear();
			cnt++;

			while(intNum.size() < 6) {
			int num1 = (int) (Math.random() * 45 + 1);
			intNum.add(num1);
		}
			System.out.println("로또번호" + cnt + ": " + intNum);
		}
			System.out.println("받은 금액은 " + won + "원이고 거스름돈은 " + won%1000 + "원입니다.");
		}
			
		public static void main(String[] args) {
			new LottoProgram().LottoStart();
		}
}
	
