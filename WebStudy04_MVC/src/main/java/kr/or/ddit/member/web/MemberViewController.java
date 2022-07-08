package kr.or.ddit.member.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.vo.MemberVO;

//@WebServlet("/member/memberView.do")
@Controller
public class MemberViewController {
	
	MemberService service = new MemberServiceImpl();
	
	@RequestMapping("/member/memberView.do")
	public String memberView(@RequestParam(value="who", required=true) HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String memId = req.getParameter("who");
		String layout = req.getParameter("layout");
		
		if(StringUtils.isBlank(memId)) { // 필수파라미터(클라이언트) X => 400 에러
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "필수파라미터 누락");
			return null;
		}
		
		MemberVO member = service.retrieveMember(memId);
		req.setAttribute("member", member);
		
		String viewName = null;
		if("grid".equals(layout)) {
			viewName = "member/memberView.tiles";
		} else {
			viewName = "member/memberView";
		}
		
		return viewName;
	}
}
