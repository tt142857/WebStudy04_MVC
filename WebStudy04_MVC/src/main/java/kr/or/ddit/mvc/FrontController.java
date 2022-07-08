package kr.or.ddit.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.HandlerAdapter;
import kr.or.ddit.mvc.annotation.HandlerMapping;
import kr.or.ddit.mvc.annotation.RequestMappingHandlerAdapter;
import kr.or.ddit.mvc.annotation.RequestMappingHandlerMapping;
import kr.or.ddit.mvc.annotation.RequestMappingInfo;
import kr.or.ddit.mvc.annotation.resolvers.BadRequestException;

/**
 * Front Controller Pattern을 적용하고, 모든 요청에 대한 사전 처리를 담당
 * /member/memberList.do
 * /member/memberInsert.do
 * /prod/prodInsert.do
 * 
 */
public class FrontController extends HttpServlet {
	
	private HandlerMapping handlerMapping;
	private HandlerAdapter handlerAdapter;
	private ViewResolver viewResolver;
	
	@Override
	public void init() throws ServletException {
		super.init();
		handlerMapping = new RequestMappingHandlerMapping("kr.or.ddit");
		handlerAdapter = new RequestMappingHandlerAdapter();
		viewResolver = new DelegatingViewResolver();
	}
	
	// 요청해야 되는 곳이 get, post 여러 방법이 있기 때문에 공통적으로 해결 가능한 service로 처리함
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 뒤에 있는 컨트롤러가 바뀌더라도 Front를 수정할 일은 없다.
		RequestMappingInfo mappingInfo = handlerMapping.findCommandHandler(req); // 핸들러의 정보를 불러온다.
		int sc = 200; // status_code 200 성공
		
		String message = null;
		
		if (mappingInfo != null) { // 핸들러를 찾았을 때
			try {
				String viewName = handlerAdapter.invokeHandler(mappingInfo, req, resp); // 논리적 viewName을 불러온다.
				if(viewName != null) {
					viewResolver.viewResolve(viewName, req, resp);
				} else { // viewName 자체가 null일 경우, 다른 컨트롤러에서 상황에 따라 null을 반환한 경우
					if(!resp.isCommitted()) { // viewName이 잘못되었을 경우 500
						sc = 500;
						message = "요청을 처리하는 과정에서 문제가 발생했습니다.(LVN이 없습니다.)";
					}
				}
			} catch (BadRequestException e) {
				sc = 400;
				message = e.getMessage();
			}
		} else {
			sc = 404;
			message = "현재 요청은 제공하지 않는 서비스입니다.";
		}
		
		if (sc != 200) {
			resp.sendError(sc, message);
		}
	}
}
