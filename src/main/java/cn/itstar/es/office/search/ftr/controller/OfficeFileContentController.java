package cn.itstar.es.office.search.ftr.controller;
//package cn.itstar.office.search.web;
//
//import java.io.IOException;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//@Controller
//@RequestMapping("/officefile")
//public class OfficeFileContentController {
//
//
//	@Value("#{solrConfig[url]}")
//	private String solrUrl;
//	@Autowired
//	private SolrJ solrJ;
//
//	/**
//	 * 在页面列表中打开终端页
//	 */
//	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
//	public String toArticleIndex(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		// 获取当前所在页的页号
//		String pageIndex = req.getParameter("curPage");
//		req.setAttribute("curPage", pageIndex);
//		String strId = req.getParameter("id");
//		// OfficeFile officeFile = officeFileMapper.getById(strId);
//		SolrDocument officeFile = solrJ.queryIndexByID(solrUrl, strId);
//		if (officeFile == null) {
//			return null;
//		}
//		req.setAttribute("officeFile", officeFile);
//		return "web/officefile/index";
//	}
//
//}
