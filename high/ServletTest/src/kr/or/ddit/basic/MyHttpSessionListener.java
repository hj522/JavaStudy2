package kr.or.ddit.basic;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//T10HttpSessionListenerTest
// 리스너: 톰캣에서 발생할수있는 여러 이벤트가 발생했을때 적절한 리스너 객체를 통해 홈페이지에 등록하여 자동으로 호출

public class MyHttpSessionListener implements HttpSessionListener, HttpSessionAttributeListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("[MyHttpSessionListener] sessionCreated => " + se);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("[MyHttpSessionListener] sessionDestroyed => " + se);
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		System.out.println("[MyHttpSessionListener] attributeAdded => " + event.getName() + " 추가됨");
		
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		System.out.println("[MyHttpSessionListener] attributeRemoved => " + event.getName() + " 삭제됨");
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		System.out.println("[MyHttpSessionListener] attributeReplaced => " + event.getName() + " 변경됨");
	}
}
