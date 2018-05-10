package cn.itstar.es.office.search.ftr.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;
import org.junit.Test;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itstar.es.office.search.common.manager.SysLogManager;
import cn.itstar.es.office.search.common.utils.SpringContextUtils;
import cn.itstar.es.office.search.ftr.utils.POIUtil;

public class TestDemo {
	
	@Test
	public void demo4() {
		
		HttpServletRequest request;
		HttpServletResponse response = null;
		String path="D:/Program Files/MyEclipse/MyEclipse_2016/Workspaces/es-office-search/src/main/webapp/upload/开题报告-刘超.doc";
		// 得到要下载文件的输入流
		try {
			InputStream in = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 使用输出流操作
		try {
			OutputStream out = response.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void demo3() {
		String[] configLocations = {"applicationContext.xml",
	            "applicationContext-shiro.xml"
	            ,"applicationContext-quartz.xml"
	            ,"applicationContext-jdbc.xml"
	            ,"applicationContext-ehcache.xml"
			};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);
		Object bean = ctx.getBean("indexFileService");
		System.out.println(bean);
		SpringContextUtils.getBean("indexFileService");
		
	}
	
	@Test
	public void demo2(){
		//String clientName = "C:\\localhost\\temp\\文件.doc";
		//String clientName = "文件.doc";
		String clientName = "C:\\localhost\\temp\\文件.文件.doc";
		 String sufName = null;
		 sufName = clientName.substring(clientName.lastIndexOf(".") + 1);
		/*if (clientName.contains("\\")) {//如果包含"\"表示是一个带路径的名字,则截取最后的文件名
			 sufName  = clientName.substring(clientName.lastIndexOf("\\")).substring(clientName.lastIndexOf(".") + 1);
        }else {
       	 	sufName = clientName.substring(clientName.lastIndexOf(".") + 1);
        }*/
		System.out.println(sufName);
	}
	@Test
	public void demo5() throws IOException, XmlException, OpenXML4JException {
		String file="E:\\testDir\\123.pptx";
	 	System.out.println(POIUtil.pptx2String(file));
	 	
	}
	
}
