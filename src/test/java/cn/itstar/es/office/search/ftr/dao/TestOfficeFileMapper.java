package cn.itstar.es.office.search.ftr.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itstar.es.office.search.common.entity.SysUserEntity;
import cn.itstar.es.office.search.ftr.entity.OfficeFileEntity;
import cn.itstar.es.office.search.shiro.dao.SysUserMapper;

/**
 * 系统用户dao
 */
public class TestOfficeFileMapper {
	
	private SqlSessionFactory sqlSessionFactory;
	
	@Before
	public void init() throws IOException {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-jdbc.xml");
		//ctx.getBean(arg0)
		
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource );
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	@Test
	public void demo2(){
	}
	
	@Test
	public void demo1(){
		SqlSession session = sqlSessionFactory.openSession();
		
		List<OfficeFileEntity> listForPage = null;
		try {
			listForPage = session.selectList("cn.itstar.es.office.search.ftr.dao.OfficeFileMapper.listForPage", "");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.out.println(listForPage);
	}
}
