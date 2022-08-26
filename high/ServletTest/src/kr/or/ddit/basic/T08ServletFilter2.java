package kr.or.ddit.basic;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

// 필터로 로그인처리 활용가능

public class T08ServletFilter2 implements Filter {

	@Override
	public void destroy() {
		
		// 필터객체가 컨테이너에 의해 서비스로부터 제거되기 전에 호출됨
		System.out.println("T08ServletFilter2 => destroy() 호출됨.");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc)
			throws IOException, ServletException {
		
		System.out.println("T08ServletFilter2 => doFilter() 시작.");
		
		// 서블릿 수행시간 계산하기
		long startTime = System.currentTimeMillis();
		
		// 필터 체인을 실행하기 (필터 호출 작업)
		fc.doFilter(req, resp);
		
		System.out.println("수행 시간(ms) : " + (System.currentTimeMillis() - startTime));
		
		System.out.println("T08ServletFilter2 => doFilter() 끝!");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("T08ServletFilter2 => init() 호출됨."); // 이게 뜨면 객체가 생성됐다는 거임 
		
		// 초기화 파라미터 정보 가져오기
		String initParam = filterConfig.getInitParameter("init-param");
		System.out.println("init-param: " + initParam);
	}
}
