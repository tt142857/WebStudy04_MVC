package kr.or.ddit.login;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;

//@WebServlet("/login/logout.do")
@Controller
public class LogoutController {
	
	@RequestMapping(value="/login/logout.do", method=RequestMethod.POST)
	public String logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session == null || session.isNew()) {
			resp.sendError(400);
			return null;
		}
		session.invalidate();
		String message = URLEncoder.encode("로그아웃", "UTF-8");
		
		req.getSession().setAttribute("message", message);
		return req.getContextPath();
	}
}
