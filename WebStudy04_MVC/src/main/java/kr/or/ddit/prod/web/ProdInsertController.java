package kr.or.ddit.prod.web;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.filter.multipart.MultipartFile;
import kr.or.ddit.filter.multipart.StandardMultipartHttpServletRequest;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.streotype.Controller;
import kr.or.ddit.mvc.annotation.streotype.RequestMapping;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidateUtils;
import kr.or.ddit.vo.ProdVO;

//@WebServlet("/prod/prodInsert.do")
//@MultipartConfig
@Controller
public class ProdInsertController {
	
	ProdService service = new ProdServiceImpl();
	OthersDAO othersDao = new OthersDAOImpl();
	
	private void addOthersData(HttpServletRequest req) {
		req.setAttribute("lprodList", othersDao.selectLprodList());
		req.setAttribute("buyerList", othersDao.selectBuyerList());
	}
	
	@RequestMapping("/prod/prodInsert.do")
	public String prodForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addOthersData(req);
		return "/prod/prodForm.tiles";
	}
	
	@RequestMapping(value="/prod/prodInsert.do", method=RequestMethod.POST)
	public String inserProcess(ProdVO prod, HttpServletRequest req) {
		addOthersData(req);
		
//		ProdVO prod = new ProdVO();
//		req.setAttribute("prod", prod);
//		try {
//			// 넘어온 parameter와 값이 같으면 reflection(값이 대응)이 됨
//			BeanUtils.populate(prod, req.getParameterMap());
//		} catch(IllegalAccessException | InvocationTargetException e) {
//			throw new RuntimeException(e);
//		}
		
		if(req instanceof StandardMultipartHttpServletRequest) {
			MultipartFile imageFile = ((StandardMultipartHttpServletRequest) req).getFile("prodImage"); // 파트명
			prod.setProdImage(imageFile);
		}
		
		// 상품 이미지 저장 처리
		String imageFolderUrl = "/resources/prodImages";
		String imageFolderPath = req.getServletContext().getRealPath(imageFolderUrl); 
		// └ 절대경로는 Servlet Context에서 가져와야 함
		// └ 서버를 기준(getServletContext().getRealPath())으로 해서 경로를 가져옴
		File imageFolder = new File(imageFolderPath);
		
		if(!imageFolder.exists()) {
			imageFolder.mkdirs();
		} // 폴더가 존재하지 않으면 폴더를 생성함
		
		String imageSaveName = UUID.randomUUID().toString();
		
		File prodImageFile = new File(imageFolder, imageSaveName); // 경로와 아이템을 설정 
		
		// 아직 prodImage를 설정하지 않았음
		MultipartFile imageFile = prod.getProdImage();
		//filePath가 null
		if(!imageFile.isEmpty()) {
			try {
				imageFile.transferTo(prodImageFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			prod.setProdImg(imageSaveName);
		}
		
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors); // 성공해서 redirection이 되기 전까지 유지되는 값
		boolean valid = ValidateUtils.validate(prod, errors, InsertGroup.class);
		
		String viewName = null;
		if(valid) {
			ServiceResult result = service.createProd(prod);
			if(ServiceResult.OK.equals(result)) { // Null Point에 안전한 코드가 되기 위해서는 확실한 녀석을 앞에다 두어야 함
				viewName = "redirect:/prod/prodView.do?what=" + prod.getProdId();
			} else {
				req.getSession().setAttribute("message", "서버 오류");
				viewName = "/prod/prodForm.tiles";
			}
		} else {
			viewName = "/prod/prodForm.tiles";
		}
		
		return viewName;
	}
}
