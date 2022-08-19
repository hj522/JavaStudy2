package kr.or.ddit.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class T15ObjectStreamTest {
	public static void main(String[] args) {
		
		// Member객체 생성하기
		Member mem1 = new Member("홍길동", 20, "대전");
		Member mem2 = new Member("일지매", 30, "경기");
		Member mem3 = new Member("이몽룡", 40, "강원");
		Member mem4 = new Member("성춘향", 50, "광주");
		
		ObjectOutputStream oos = null; //보조스트림
		
		try {
			// 객체를 파일에 저장하기
			
			// 출력용 스트림 객체 생성하기
			oos = new ObjectOutputStream(
					new BufferedOutputStream(	// 버퍼 기능 추가
						new FileOutputStream("e:/D_Other/memObj.bin")));  //기반스트림
			
			/* 쓰기 작업(이때 직렬화 발생) */
			oos.writeObject(mem1);
			oos.writeObject(mem2);
			oos.writeObject(mem3);
			oos.writeObject(mem4);
			
			System.out.println("쓰기 작업 완료...");
			
		} catch(IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(
					new BufferedInputStream(	// 버퍼 기능 추가
						new FileInputStream("e:/D_Other/memObj.bin"))); // 기반스트림
			
			Object obj = null; 
			
			 /* readObject 역직렬화 발생 */
			while((obj = ois.readObject()) != null) {
				// 파일의 마지막에 다다르면 EOF Exception 발생함. (End Of File)
				
				// 읽어온 객체를 원래의 타입으로 변환 후 사용한다.
				Member mem = (Member) obj;
				System.out.println("이름: " + mem.getName());
				System.out.println("나이: " + mem.getAge());
				System.out.println("주소: " + mem.getAddr());
				System.out.println("---------------------------");
			}
			
		} catch (IOException ex) {
		//	ex.printStackTrace();
			// 더이상 읽어올 객체가 없으면 예외 발생! (EOF Exception)
			System.out.println("출력 작업 끝...");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

// VO 클래스(데이터를 담기 위한 클래스. Value Object)
class Member implements Serializable {
	
	// 자바는 Serializable 인터페이스를 구현한 클래스만 직렬화 할 수 있도록 제한하고 있다.
	
	// Serializable: 직렬화. 힙 메모리에 저장되어있는 객체를 연속적인 바이트로 늘어놓는 것
	// 직렬화 된 연속적인 바이트를 읽어서 다시 객체로 만들어주는 작업 => 역직렬화(직렬화 반대 개념)
	
	/*
	  	trasient => 직렬화가 되지 않을 멤버변수에 지정한다. (static필드도 직렬화가 되지 않는다.)
	 				직렬화가 되지 않는 멤버변수는 기본값으로 저장된다. (참조형 변수: null, 숫자형 변수: 0)
	 				주민등록번호, 패스워드 등에 주로 사용!
	 */
	
	transient private String name; // 직렬화 대상에서 제외
	transient private int age;
	private String addr;
	
	
	public Member(String name, int age, String addr) {
		super();
		this.name = name;
		this.age = age;
		this.addr = addr;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getAddr() {
		return addr;
	}
	
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	
	@Override
	public String toString() {
		return "Member [name=" + name + ", age=" + age + ", addr=" + addr + "]";
	}
}
