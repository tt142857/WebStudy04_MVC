package kr.or.ddit.prod.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.mvc.DelegatingViewResolver;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;


//@WebServlet("/prod/prodList.do")
@Controller
public class ProdListController {
	ProdService service = new ProdServiceImpl();
	OthersDAO othersDAO = new OthersDAOImpl();
	
	private String processHTML(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "/prod/prodList.tiles";
	}
	
	private String processJsonData(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProdVO detailCondition = new ProdVO();
		try {
			BeanUtils.populate(detailCondition, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		
		String pageParam = req.getParameter("page");
		int currentPage = 1;
		if(StringUtils.isNumeric(pageParam)) { // 파라미터가 숫자로 구성되어 있는지 확인함
			currentPage = Integer.parseInt(pageParam);
		}
		PagingVO<ProdVO> pagingVO = new PagingVO<>(3, 2);
		pagingVO.setCurrentPage(currentPage);
		// pagingVO.setSimpleCondition(searchVO);
		pagingVO.setDetailCondition(detailCondition);
		
		service.retrieveProdList(pagingVO);
		
		req.setAttribute("pagingVO", pagingVO);
		
		return "forward:/jsonView.do";
	}

	@RequestMapping("/prod/prodList.do")
	public String prodList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("lprodList", othersDAO.selectLprodList());
		req.setAttribute("buyerList", othersDAO.selectBuyerList());
		
		String accept = req.getHeader("accept");
		
		String viewName = null;
		if(StringUtils.containsIgnoreCase(accept, "json")) {
			viewName = processJsonData(req, resp);
		} else {
			viewName = processHTML(req, resp);
		}
		
		return viewName;
	}
}
