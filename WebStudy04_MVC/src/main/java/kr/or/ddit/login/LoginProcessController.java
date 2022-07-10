package kr.or.ddit.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.AuthenticateService;
import kr.or.ddit.member.service.AuthenticateServiceImpl;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.vo.MemberVO;

//@WebServlet("/login/loginProcess.do")
@Controller
public class LoginProcessController {
	
	AuthenticateService service = new AuthenticateServiceImpl();
	
	private boolean validate(MemberVO member) {
		return StringUtils.isNotBlank(member.getMemId()) && StringUtils.isNotBlank(member.getMemPass());
	}
	
	@RequestMapping(value="/login/loginProcess.do", method=RequestMethod.POST)
	public String login(HttpServletRequest req, HttpSession session, HttpServletResponse resp) throws ServletException, IOException {
		if(session.isNew()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		
		MemberVO inputData = new MemberVO();
		inputData.setMemId(req.getParameter("memId"));
		inputData.setMemPass(req.getParameter("memPass"));
//		1. 검증
		boolean valid = validate(inputData);
		String viewName = null;
		if(valid) {
//		   - 통과
//		   	 2. 처리(로그인 여부 판단)
			ServiceResult result = service.authenticate(inputData);
			if(ServiceResult.OK.equals(result)) {
//				Post-Redirect-Get 패턴
//		   	 	- 로그인 성공 : welcome 페이지로 이동 (redirect)
				MemberVO authMember = inputData;
				req.getSession().setAttribute("message", "로그인 성공");
				req.getSession().setAttribute("authMember", authMember);
				viewName = "redirect:/";
			}else {
//		   	 	- 실패 : loginForm 으로 이동(forward)
				String message = null;
				if(ServiceResult.NOTEXIST.equals(result)) {
					message = "회원 가입이 필요함.";
				}else {
					message = "비밀번호 오류.";
				}
				req.getSession().setAttribute("message", message);
				viewName = "/login/loginForm.jsp";
			}
		} else {
//		   - 불통 : loginForm 으로 이동(forward)
			req.getSession().setAttribute("message", "검증 실패");
			viewName = "redirect:/login/loginForm.jsp";
		}
		
		return viewName;
	}
}










