package kr.or.ddit.member.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.vo.MemberVO;

//@WebServlet("/myPage.do")
@Controller
public class MyPageController {
	MemberService service = new MemberServiceImpl();

	@RequestMapping("/myPage.do")
	public String myPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberVO authMember = (MemberVO) req.getSession().getAttribute("authMember");
		
		String viewName = null;
		if(ObjectUtils.isEmpty(authMember)) {
			viewName = "/index.tiles";
		} else {
			String memId = authMember.getMemId();
			MemberVO member = service.retrieveMember(memId);
			req.setAttribute("member", member);
			viewName = "/member/myPage.tiles";			
		}
		
		return viewName;
	}
}
