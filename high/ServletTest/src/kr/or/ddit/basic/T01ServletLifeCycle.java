package kr.or.ddit.basic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class T01ServletLifeCycle extends HttpServlet {

	/*
	 	'서블릿(Servlet)'이란?
	 	컨테이너(서블릿 엔진)에 의해 관리되는 자바 기반 웹 컴포넌트로서,
	 	동적인 웹 컨텐츠 생성을 가능하게 해준다.
	 */
	
	@Override
	public void init() throws ServletException {
		
		// 초기화 코드 작성
		System.out.println("init() 호출됨...");
	}
	
	public T01ServletLifeCycle() {
		System.out.println("T01ServletLifeCycle 생성자 호출됨"); // 가장 먼저 호출됨
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 실제적인 작업 수행이 시작되는 지점...(자바의 main()메서드 역할)
		System.out.println("service() 호출됨");
		
		super.service(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 메소드 방식이 GET인 경우 호출됨
		System.out.println("doGet() 호출됨");
		
		throw new ServletException("서블릿에서 에러가 발생했습니다!");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 메소드 방식이 POST인 경우 호출됨
		System.out.println("doPost() 호출됨");
	}
	
	@Override
	public void destroy() {
		// 객체 소멸 시(컨테이너로부터 서블릿 객체 제거 시) 필요한 코드 작성
		System.out.println("destroy() 호출됨");
	}
}
