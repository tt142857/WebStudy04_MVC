package kr.or.ddit.member.web;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidateUtils;
import kr.or.ddit.vo.MemberVO;

//@WebServlet("/member/memberInsert.do") // RESTful URI(X)
@Controller
public class MemberInsertController {
	MemberService service = new MemberServiceImpl();
	
	@RequestMapping("/member/memberInsert.do")
	public String memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		req.setAttribute("contents", "/WEB-INF/views/member/memberForm.jsp");
//		String view = "/WEB-INF/views/template.jsp";
//		req.getRequestDispatcher(view).forward(req, resp);
		return "/member/memberForm.tiles";
	}
	
	@RequestMapping(value="/member/memberInsert.do", method=RequestMethod.POST)
	public String insertProcess(@ModelAttribute("member") MemberVO member, HttpServletRequest req) {
		req.setAttribute("member", member);
//		member.setMemId(req.getParameter("memId"));
		
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = ValidateUtils.validate(member, errors, InsertGroup.class);
		String viewName = null;
		if(valid) {
			ServiceResult result = service.createMember(member);	
			switch (result) {
			case PKDUPLICATED:
				req.setAttribute("message", "아이디 중복");
				viewName = "/member/memberForm.tiles";
				break;
			case FAIL:
				req.setAttribute("message", "서버의 문제로 등록을 못했음. 쫌따 다시하셈.");
				viewName = "/member/memberForm.tiles";
				break;	
			default:
				req.getSession().setAttribute("message", "등록 성공");
				viewName = "redirect:/login/loginForm.jsp";
				break;
			}
		}else {
			viewName = "/member/memberForm.tiles";
		}

		return viewName;
	}
}

