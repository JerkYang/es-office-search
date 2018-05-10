package cn.itstar.es.office.search.ftr.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import cn.itstar.es.office.search.common.constant.SystemConstant;
import cn.itstar.es.office.search.common.controller.AbstractController;
import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.common.entity.SysUserEntity;
import cn.itstar.es.office.search.common.utils.ElasticSearchUtils;
import cn.itstar.es.office.search.common.utils.ShiroUtils;
import cn.itstar.es.office.search.ftr.service.IndexFileService;
import cn.itstar.es.office.search.ftr.service.OfficeFileService;

/**
 * 任务列表
 */
@Component("indexFileTask")
public class IndexFileTask {
	
	@Autowired
	private IndexFileService indexFileService;
	
	@Autowired
	private OfficeFileService officeFileService;


	/**
	 * 为数据库中的所有文件创建索引
	 * @return 
	 * @throws SolrServerException
	 * @throws IOException
	 */
	
	public R addDBIndexByDay() {
		return indexFileService.addDBIndexFile();
	}
	
	
}
