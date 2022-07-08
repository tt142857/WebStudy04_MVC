package kr.or.ddit.props.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.DelegatingViewResolver;
import kr.or.ddit.props.service.DataBasePropertyService;
import kr.or.ddit.props.service.DataBasePropertyServiceImpl;
import kr.or.ddit.vo.DataBasePropertyVO;

@WebServlet("/11/jdbdDesc.do")
public class DataBasePropertyRetrieveServlet extends HttpServlet{
	
	DataBasePropertyService service = new DataBasePropertyServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<DataBasePropertyVO> dataList = service.retrieveDataBaseProperties();
		
		req.setAttribute("dataList", dataList);
		
		String viewName = "/11/jdbcDesc.tiles";
		new DelegatingViewResolver().viewResolve(viewName, req, resp);
	}
}




















