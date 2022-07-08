package kr.or.ddit.prod.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.ProdVO;

//@WebServlet("/prod/prodView.do")
@Controller
public class ProdViewController {
	ProdService service = new ProdServiceImpl();
	
	@RequestMapping("/prod/prodView.do")
	public String prodView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String prodId = req.getParameter("what");
		if(StringUtils.isBlank(prodId)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "어떤 상품 조회?");
			return null;
		}
		ProdVO prod = service.retrieveProd(prodId);
		req.setAttribute("prod", prod);
		
		return "/prod/prodView.tiles";
	}
}
