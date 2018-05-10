//package cn.itstar.es.office.search.ftr.controller;
//
//import java.io.IOException;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.elasticsearch.client.Client;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import cn.itstar.es.office.search.common.utils.ElasticSearchUtils;
//import cn.itstar.es.office.search.ftr.utils.ConstantUtil;
//import cn.itstar.es.office.search.ftr.utils.ESUtil;
//import cn.itstar.es.office.search.ftr.utils.PageUtil;
//
//
//@Controller
//@RequestMapping("/search")
//public class SearchFileController {
//
//	
//	// 每页记录数
//	private long pageSize = ConstantUtil.pageSize;
//
//	@RequestMapping(value = "/query.do")
//	public String query(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		Client client = ElasticSearchUtils.getClient();
//		// 获取关键词
//		String keyword = req.getParameter("keyword");
//		// 获取请求编码格式
//		String charset = (String) req.getCharacterEncoding();
//		
//		// 若是GET请求且页面请求的字符编码为 ISO8859-1 ，则转换编码
//		if ("GET".equals(req.getMethod()) && "ISO8859-1".equals(charset)) {
//			keyword = new String(keyword.getBytes("ISO8859-1"), "UTF-8");
//		}
//		// 页号
//		String strPageIndex = req.getParameter("pageIndex");
//		long pageIndex = strPageIndex == null ? 1 : Long.parseLong(strPageIndex);
//		PageUtil<Map<String, Object>> page = new PageUtil<Map<String, Object>>();
//		page.setKeyword(keyword);
//		page.setCurPage(pageIndex);
//		boolean isExitsIndex = ESUtil.isExistsIndex(esFileIndex);
//		if(isExitsIndex) {
//			ESUtil.queryAllHighlightByPage(page, client, esFileIndex, esFileType);
//		}
//		//page.setCurPage(pageIndex);
//		page.setPageSize(pageSize);
//		req.setAttribute("page", page);
//		req.setAttribute("keyword", keyword);
//		return "web/search/list";
//	}
//
//}
