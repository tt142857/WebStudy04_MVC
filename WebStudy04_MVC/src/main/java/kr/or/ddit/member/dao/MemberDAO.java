package kr.or.ddit.member.dao;

import java.util.List;

import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;

/**
 * 회원관리(CRUD) 및 인증구조를 위한 Persistence Layer
 *
 */
public interface MemberDAO {
	public MemberVO selectMemberForAuth(MemberVO inputData);
	
	/**
	 * 회원 정보 등록
	 * @param member
	 * @return 등록한 레코드 수 if > 0 : 성공, else : 실패
	 */
	public int insertMember(MemberVO member);
	
	/**
	 * 페이징 처리를 위한 레코드 수 조회
	 * @param pagingVO : 검색 조건을 가진 VO
	 * @return 총 레코드 수
	 */
	public int selectTotalRecord(PagingVO<MemberVO> pagingVO);
	
	/**
	 * 회원 목록 조회
	 * @return 존재하지 않는 경우, size()==0
	 */
	public List<MemberVO> selectMemberList(PagingVO<MemberVO> pagingVO);

	/**
	 * 회원 상세 조회
	 * @param memId 조회할 회원의 아이디
	 * @return 존재하지 않는 경우, null 반환
	 */
	public MemberVO selectMember(String memId);
	
	/**
	 * 회원 정보 수정
	 * @param member 수정할 회원의 정보를 가진 VO
	 * @return 수정한 레코드 수 if > 0 : 성공, else : 실패
	 */
	public int updateMember(MemberVO member);
	
	/**
	 * 회원 정보 삭제
	 * @param memId 삭제할 회원의 아이디
	 * @return 삭제한 레코드 수 if > 0 : 성공, else : 실패
	 */
	public int deleteMember(String memId);
}
