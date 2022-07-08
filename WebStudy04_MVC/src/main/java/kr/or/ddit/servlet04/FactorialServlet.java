package kr.or.ddit.servlet04;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/04/factorial.do")
public class FactorialServlet extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("요청 메소드 : "+req.getMethod());
		String accept = req.getHeader("Accept");
		// 클라이언트 보내는 모든 데이터는 검증이 필요함.
		String param = req.getParameter("number");
		if(param!=null && param.matches("\\d+")){
			String pattern = "%d! = %d";
			int number = Integer.parseInt(param);
		// 	재귀 호출: recursive call
			long result = factorial(number);
			String expression = String.format(pattern, number, result);  // 6! = 720
			req.setAttribute("expression", expression);
			req.setAttribute("result", result);
			req.setAttribute("test", "한글데이터");
			String view = null;
			if(accept.contains("json")) {
				view = "/jsonView.do";
			}else if(accept.contains("xml")) {
				view = "/xmlView.do";
			}else {
				view = "/WEB-INF/views/factorialView.jsp";
			}
			req.getRequestDispatcher(view).forward(req, resp);
			
		}else if(param!=null && !param.matches("\\d+")){
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "팩토리얼 연산은 양수와 숫자만으로 처리");
			return;
		}	
	}
	
	public long factorial(int number){
		if(number<0)
			throw new IllegalArgumentException("음수에 대해서는 연산 불가"); 
		if(number==0){
			return 1;
		}else{
			return number * factorial(number - 1);
		}
	}

}
