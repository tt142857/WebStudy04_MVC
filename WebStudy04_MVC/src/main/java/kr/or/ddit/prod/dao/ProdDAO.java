package kr.or.ddit.prod.dao;

import java.util.List;

import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

/**
 * 상품 관리 (CRUD) Persistence Layer
 *
 */
public interface ProdDAO {
	
	/**
	 * 페이징 처리를 위한 레코드 수 조회
	 * @param pagingVO
	 * @return
	 */
	public int selectTotalRecord(PagingVO<ProdVO> pagingVO);
	
	/**
	 * 상품 신규 등록
	 * @param prod
	 * @return >0 : 성공, <=0 : 실패
	 */
	public int insertProd(ProdVO prod);
	
	
	/**
	 * 상품 목록 조회(차후 페이징과 검색 적용 예정)
	 * @param pagingVO TODO
	 * @return 존재하지 않으면 null 반환
	 */
	public List<ProdVO> selectProdList(PagingVO<ProdVO> pagingVO);
	
	
	/**
	 * 상품 상세 조회
	 * @param prodId
	 * @return 존재하지 않으면 null 반환
	 */
	public ProdVO selectProd(String prodId);
	
	
	/**
	 * 상품 정보 수정
	 * @param prodId
	 * @return >0 : 성공, <=0 : 실패
	 */
	public int updateProd(ProdVO prod);
//	deleteProd
}
