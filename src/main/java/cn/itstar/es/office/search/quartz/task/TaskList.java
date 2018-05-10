package cn.itstar.es.office.search.quartz.task;

import org.elasticsearch.client.Client;
import org.springframework.stereotype.Component;

import cn.itstar.es.office.search.common.utils.ElasticSearchUtils;

/**
 * 任务列表
 */
@Component("taskList")
public class TaskList {

	/**
	 * 初始化 ElasticSearch 的方法
	 */
	public Client initClient() {
		Client client = ElasticSearchUtils.initClient();
		return client;
	}
	
	
}
