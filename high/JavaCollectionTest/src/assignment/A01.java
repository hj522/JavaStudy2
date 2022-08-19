package assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class A01 {
	public static void main(String[] args) {
		
		List<Student> stuList = new ArrayList<Student>();
		
		stuList.add(new Student("101", "김효정", 90, 80, 70));
		stuList.add(new Student("102", "윤다영", 90, 80, 70));
		stuList.add(new Student("103", "성민정", 70, 85, 100));
		stuList.add(new Student("104", "길선주", 70, 90, 75));
		stuList.add(new Student("105", "이승연", 95, 85, 85));
		stuList.add(new Student("106", "허지현", 100, 80, 65));
		
		
		
		Collections.sort(stuList);
		System.out.println("학번 오름차순 정렬: ");
		for(Student stu : stuList) {
			System.out.println(stu);
		}
		
		System.out.println("--------------------------------");
		
		Collections.sort(stuList, new SortScoreDesc());
		System.out.println("총점 내림차순 정렬: ");
		
		 for(Student stu : stuList) {
			 System.out.println(stu);
		 }
	}
	
}


class SortScoreDesc implements Comparator<Student> {
	
	@Override
	public int compare(Student stu1, Student stu2) {
		
		if(stu1.getScore() == stu2.getScore()) {
			return -1;
		} else {
			return new Integer(stu1.getScore()).compareTo(stu2.getScore()) * -1;
		}
	}
}

class Student implements Comparable<Student> {
	
	private String num; // 학번
	private String name; // 이름
	private int Korean; // 국어점수
	private int English; // 영어점수
	private int Math; // 수학점수
	private int Score; // 총점
	private int Grade; // 등수
	
	public Student(String num, String name, int korean, int english, int math) {
		super();
		this.num = num;
		this.name = name;
		this.Korean = korean;
		this.English = english;
		this.Math = math;
		this.Score = korean + english + math;
//		this.Grade = Grade;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKorean() {
		return Korean;
	}

	public void setKorean(int korean) {
		Korean = korean;
	}

	public int getEnglish() {
		return English;
	}

	public void setEnglish(int english) {
		English = english;
	}

	public int getMath() {
		return Math;
	}

	public void setMath(int math) {
		Math = math;
	}

	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		Score = score;

	}

	public int getGrade() {
		return Grade;
	}

	public void setGrade(int grade) {
		Grade = grade;
	}
	
	// 등수: for문 돌려서 학생1이 학생2 점수보다 적으면 ~!~!~~~!~@! 조건 주기

	@Override
	public String toString() {
		return "Student [num=" + num + ", name=" + name + ", Korean=" + Korean + ", English=" + English + ", Math="
				+ Math + ", Score=" + Score + ", Grade=" + Grade + "]";
	}
	

	@Override
	public int compareTo(Student stu) {
		return this.getNum().compareTo(stu.getNum());
	}
}
	
	
/*
 * 학번, 이름, 국어점수, 영어점수, 수학점수, 총점, 등수를 멤버로 갖는 Student 클래스를 만든다.
	  생성자는 학번(스트링), 이름, 국어, 영어, 수학 점수만 매개변수로 받아서 처리한다.
	  
	  이 Student객체들은 List에 저장하여 관리한다.
	 List에 저장된 데이터들을 학번의 오름차순으로 정렬하여 출력하는 부분과
	  총점의 역순으로 정렬하는 부분을 프로그램 하시오.
	 (총점이 같으면 학번의 내림차순으로 정렬되도록 한다.) - 객체생성시 같은 총점의 더미를 만들어야됨
	 (학번 정렬기준은 Student클래스 자체에서 제공하도록 하고, - comparable
	   총점 정렬기준은 외부클래스에서 제공하도록 한다.) -외부정렬자.. 
 */
