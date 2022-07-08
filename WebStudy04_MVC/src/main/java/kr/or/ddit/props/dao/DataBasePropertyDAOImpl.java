package kr.or.ddit.props.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.DataBasePropertyVO;

public class DataBasePropertyDAOImpl implements DataBasePropertyDAO {
	private SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
	
	@Override
	public List<DataBasePropertyVO> selectDataBaseProperties() {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			DataBasePropertyDAO mapperProxy = sqlSession.getMapper(DataBasePropertyDAO.class);
			return mapperProxy.selectDataBaseProperties();
		}
	}
}

