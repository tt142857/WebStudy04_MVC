package kr.or.ddit.prod.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BuyerVO;

/**
 * 상품 분류 선택 UI와 거래처 선택 UI를 동적 완성하기 위해 사용함
 *
 */
public interface OthersDAO {
	public List<Map<String, Object>> selectLprodList();
	public List<BuyerVO> selectBuyerList();
}
