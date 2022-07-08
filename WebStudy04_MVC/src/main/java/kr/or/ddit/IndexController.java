package kr.or.ddit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.DelegatingViewResolver;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;

//@WebServlet("/index.do")
@Controller
public class IndexController {
	
//		req.setAttribute("contents", "/WEB-INF/views/index.jsp");
	@RequestMapping("/")
	public String toIndex(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String view = "/WEB-INF/views/template.jsp";
//		req.getRequestDispatcher(view).forward(req, resp);
		return "/index.tiles";
	}
}
