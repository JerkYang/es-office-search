package cn.itstar.es.office.search.ftr.utils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itstar.es.office.search.common.entity.Page;
import cn.itstar.es.office.search.common.entity.Query;
import cn.itstar.es.office.search.common.utils.ElasticSearchUtils;
import cn.itstar.es.office.search.ftr.entity.OfficeFileEntity;

public class ESUtil {
	
	public final static Class clazz = ESUtil.class;
	
	//private static IndicesAdminClient adminClient;
	
//	public static TransportClient startTransportClient() throws Exception {
//		
//		// on startup
//		
//		client = new PreBuiltTransportClient(Settings.EMPTY)
//				.addTransportAddress(new TransportAddress(InetAddress.getByName("host1"), 9300))
//				.addTransportAddress(new TransportAddress(InetAddress.getByName("host2"), 9300));
//		
//		// on shutdown
//		
//		client.close();
//		
//		return client;
//		
//	}
	
	/**
	 * ElasticSearch初始化连接
	 */
	private static Client getClient() {
		return ElasticSearchUtils.getClient();
	}
	
	
	/**
	 * 将 Object对象数据 转换成为 json类型数据后再转换成为  map类型数据
	 * @param Object 实体类
	 * @return	map类型
	 * @throws IOException 
	 */
	public static Map<String, Object> jsonString2Map(Object obj) throws IOException{
		ObjectMapper mapper = new ObjectMapper(); // create once, reuse
		// generate json
		String json = mapper.writeValueAsString(obj);
		/*
		 * 将 json 数据转换成为 map类型的数据
		 */
		Map data = JSONObject.parseObject(json, Map.class);
		XContentParser parser = XContentFactory
				.xContent(XContentType.JSON)
				.createParser(NamedXContentRegistry.EMPTY, json);
		return parser.map();
	}
	
	/********************* 判断索引是否存在 **************************/
	
	/**
	 * 判断索引库是否存在
	 * @param index	索引名称_index
	 * @return
	 */
	public static boolean isExistsIndex(String indexName) {
		//client = ESClientManager.getESClient();
		IndicesExistsResponse  response = 
				getClient().admin().indices().exists( 
                        new IndicesExistsRequest().indices(new String[]{indexName})).actionGet();
		return response.isExists();
	}
	
	/**
	 * 判断指定的索引的类型是否存在
	 * 
	 * @param indexName
	 *            索引名 	_index
	 * @param indexType
	 *            索引类型	_type
	 * @return 存在：true; 不存在：false;
	 */
	public static boolean isExistsByNameAndType(String indexName, String indexType) {
		//client = ESClientManager.getESClient();
		TypesExistsResponse response = getClient().admin().indices()
				.typesExists(new TypesExistsRequest(new String[] { indexName }, indexType)).actionGet();
		return response.isExists();
	}
	
	
	/*public static void addAllIndex(Map<String, Object> params) {
		
		String allFilePath = (String) params.get("allFilePath");
		// 通过工具类获取所有的文件
		*//**
		 * [C:\Users\star\Desktop\Lucene\guge\ggu\-风中有朵雨做的云.txt,
		 * C:\Users\star\Desktop\Lucene\guge\ggu\ggo\东南西北风-黄安.txt,...]
		 *//*
		List<String> allFile = POIUtil.getAllFileNames(allFilePath);
		*//**
		 * 遍历文件集合中的所有的文件，得到单个文件的全路径
		 * C:\Users\star\Desktop\Lucene\guge\ggu\-风中有朵雨做的云.txt
		 *//*
		for (String fileName : allFile) {
			// 获取文件后缀名
			String sufName = fileName.substring(fileName.lastIndexOf(".") + 1);

			// 获取文件实际的名称
			String fileRealName = PreSubFixUtil.getFileNames(fileName);

			// 通过工具类利用文件名将后缀为 TXT 文件的内容读取出来
			// String doc = POIUtil.txt2String(new File(fileName));
			// 通过工具类利用文件名后缀将各种类型的文件内容读取出来
			String officeContent = POIUtil.off2String(new File(fileName), sufName);
			
			OfficeFileEntity officeFile = new OfficeFileEntity();
			officeFile.s
			
			
			
			 * 将 json 数据转换成为 map类型的数据
			 
			Map<String, Object> map = ESUtil.jsonString2Map(officeFile);
			
			 * 将JSON文档索引为一个名字为“twitter”，类型为“tweet”，id值为1的索引
			 
			//IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
			IndexResponse response = client.prepareIndex(esFileIndex, esFileType, officeFile.getId())
			        .setSource(map)
			        .execute()
			        .actionGet();
		}
		// 删除所有索引
		ESUtil.deleteAllIndex(ElasticSearchUtils.getESIndex());
		// 根据指定路径初始化所有索引
		ESUtil.addAllIndex(params, allFilePath);

		return null;
	}*/
	
	
	/**
	 * 添加索引
	 * 	 将 OfficeFile 对象转换成为 json 数据后添加到索引库中
	 * @param officeFile
	 * @throws IOException
	 */
	public static void addIndex(OfficeFileEntity officeFile, String name, String type, String id) throws IOException {
		//client = ESClientManager.getESClient();
		/*
		 * 将 json 数据转换成为 map类型的数据
		 */
		Map<String, Object> map = jsonString2Map(officeFile);
		/*
		 * 将JSON文档索引为一个名字为“twitter”，类型为“tweet”，id值为1的索引
		 */
		//IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
		IndexResponse response = getClient().prepareIndex(name, type, id)
		        .setSource(map)
		        .execute()
		        .actionGet();
	}
	
	/**
	 * 创建一个UpdateRequest,然后将其发送给client
	 * @throws Exception 
	 */
	public static void updateIndex(OfficeFileEntity officeFile, String name, String type, String id) throws Exception{
		//client = ESClientManager.getESClient();
		Map<String, Object> map = jsonString2Map(officeFile);
		/*
		 * 更新方式一
		 */
		//创建修改请求  
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(name);
		updateRequest.type(type);
		updateRequest.id(id);
		updateRequest.doc(map);
		getClient().update(updateRequest).get();
		
		/*
		 * 更新方式二 
		 */
		/*client.prepareUpdate("star", "star", "1")
						.setDoc(map)
						.get();*/
	}
	
	/**
	 * 批量操作
	 * @param officeFiles
	 * @param name
	 * @param type
	 * @throws Exception
	 */
	public static void bulkRequest(List<OfficeFileEntity> officeFiles, String name, String type) throws Exception {  
		//client = ESClientManager.getESClient();
		BulkRequestBuilder bulkRequest = getClient().prepareBulk();  
  
        Map<String, Object> map = null;
      
        for (OfficeFileEntity officeFile : officeFiles) {
        	map = jsonString2Map(officeFile);
        	
        	// either use client#prepare, or use Requests# to directly build index/delete requests
        	bulkRequest.add(getClient().prepareIndex(name, type, UUID.randomUUID().toString())  
        			.setSource(map)  
        			);  
			
		}
        
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
		    // process failures by iterating through each bulk response item
		}
	}

	//手动 批量更新  
	public static void multipleBulkProcessor(List<OfficeFileEntity> officeFiles, String name, String type) throws Exception {
		//client = ESClientManager.getESClient();
		BulkRequestBuilder bulkRequest = getClient().prepareBulk();
		Map<String, Object> map = null;

		for (OfficeFileEntity officeFile : officeFiles) {
			map = jsonString2Map(officeFile);
			IndexRequestBuilder indexRequest = getClient().prepareIndex(name, type)
					// 指定不重复的ID
					.setSource(map).setId(String.valueOf(officeFile.getFileId()));
			// 添加到builder中
			bulkRequest.add(indexRequest);
		}
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			// process failures by iterating through each bulk response item
			System.out.println(bulkResponse.buildFailureMessage());
		}
	}
	
	/**  
     * -------------------------删(删除索引,删除文档)---------------------
     */
	/**  
     * 删除整个索引库  
     */  
    public static void deleteAllIndex(String indexName){
    	//getClient() = ESClientManager.getESClient();
    	// 判断索引库是否存在
    	boolean isExistsIndex = isExistsIndex(indexName);
    	/*
    	 * 如果存在则删除
    	 */
		if (isExistsIndex) {
			//2)
			//可以根据DeleteIndexResponse对象的isAcknowledged()方法判断删除是否成功,返回值为boolean类型.
			DeleteIndexResponse dResponse = getClient().admin().indices().prepareDelete(indexName)
					.execute().actionGet();
			System.out.println("是否删除成功:"+dResponse.isAcknowledged());
		}
    	//1)
    	/*//如果传人的indexName不存在会出现异常.可以先判断索引是否存在：
        IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(indexName);
          
        IndicesExistsResponse inExistsResponse = client.admin().indices()
                .exists(inExistsRequest).actionGet();
          */
        //根据IndicesExistsResponse对象的isExists()方法的boolean返回值可以判断索引库是否存在.
    }
    
    /**
     * 通过ID删除 
     * 删除api允许你通过id，从特定的索引中删除类型化的JSON文档
     */
    public static int deleteById(String indexName, String type, String id){
    	//client = ESClientManager.getESClient();
    	DeleteResponse dResponse = getClient().prepareDelete(indexName,type, id).execute().actionGet();
    	System.out.println(dResponse.status());
        if ("OK".equals(dResponse.status().toString())) {
            System.out.println("删除成功");
            return 1;
        } else if("NOT_FOUND".equals(dResponse.status().toString())) {
        	System.out.println("找不见该索引");
        	return 2;
        	
        } else {
            System.out.println("删除失败");
            return 3;
        }
    }
    
    /**
     *
     * bulk批量通过指定id删除方法
     * @param publishIds id集合
     * @param indexName
     * @param type
     */
    public static void batchUndercarriageFamilies(List<String> publishIds, String indexName, String type) {
    	//client = ESClientManager.getESClient();
    	publishIds=new ArrayList<>();
       //publishIds.add("AV49wyfCWmWw7AxKFxeY");
       //publishIds.add("AV49wyfCWmWw7AxKFxea");
       BulkRequestBuilder builder=getClient().prepareBulk();
       for(String publishId:publishIds){
          System.out.println(publishId);
          builder.add(getClient().prepareDelete(indexName, type, publishId).request());
            
       }
       BulkResponse bulkResponse = builder.get();
       System.out.println(bulkResponse.status());
    }
    
    
    /**  
     * ---------------------------查()--------------
     */
    
    /**  
     * 根据index、type、id进行查询  
     * @return 
     */  
    public static boolean queryByIndex(String name, String type, String id){
    	//client = ESClientManager.getESClient();
        GetResponse response = getClient().prepareGet(name, type, id).execute()
                .actionGet();
        String json = response.getSourceAsString();
        if (null != json) {
            System.out.println(json);
            return true;
        } else {
            System.out.println("未查询到任何结果！");
            return false;
        }
    }
	/**
	 * 获取API允许你通过id从索引中获取类型化的JSON文档
	 */
	public static OfficeFileEntity queryIndexById(String name, String type, String id){
		//client = ESClientManager.getESClient();
		//GetResponse response = client.prepareGet("twitter", "tweet", "1") 
		GetResponse response = getClient().prepareGet(name, type, id).get();
//		        .execute()
//		        .actionGet();
		String asString = response.getSourceAsString();
		JSONObject jsonObject = JSONObject.parseObject(asString);
    	OfficeFileEntity officeFile = JSON.toJavaObject(jsonObject, OfficeFileEntity.class);
    	
		/*SearchHits searchHits = response.getHits();
        long total = searchHits.totalHits;
        SearchHit[] hits = searchHits.getHits();
        
        for (SearchHit searchHit : hits) {
        	String string = searchHit.getSourceAsString();
        	JSONObject jsonObject = JSONObject.parseObject(string);
        	OfficeFile officeFile = JSON.toJavaObject(jsonObject, OfficeFile.class);
		Map<String, DocumentField> fields = response.getFields();*/
		return officeFile;
	}
    
    /**  
     * 查询 name 索引下的所有数据   
     * <a href='https://www.elastic.co/guide/en/elasticsearch/client/java-api/5.5/java-query-dsl-match-all-query.html'>  
     * @throws Exception  
     */  
    public static SearchHits matchAllQuery(String name) throws Exception{
    	//client = ESClientManager.getESClient();
    	QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        SearchResponse response = getClient().prepareSearch(name).setQuery(queryBuilder).get();
        SearchHits searchHits = response.getHits();
        // 遍历 SearchHits
        /*for (SearchHit searchHit : searchHits) {
			
		}*/
        return searchHits;
    }
    
    /**  
     * 查询name索引下的type类型的所有数据  
     * @throws Exception
     */  
    public static SearchHits queryAllByNameAndType(String name, String type) throws Exception{
    	//client = ESClientManager.getESClient();
    	SearchResponse response = getClient().prepareSearch(name).setTypes(type).get();
        SearchHits searchHits = response.getHits();
        /*for (SearchHit searchHit : searchHits) {
			
		}*/
        return searchHits;
    }
    
	/**
	 * 查询索引库中的所有索引
	 * @param page	PageUtil<Map<String, Object>>
	 * @param solrUrl	连接 SolrgetClient() 时所需的 URL
	 * @param strPageIndex	页号
	 * @return	PageUtil
	 */
	public static Page<OfficeFileEntity> queryAllByPage(Page<OfficeFileEntity> page, String name, String type, String keyword) {
		//List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		List<OfficeFileEntity> lists = new ArrayList<OfficeFileEntity>();
		
		SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(name).setTypes(type);
		searchRequestBuilder
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.matchAllQuery()) //查询所有
				//.setQuery(QueryBuilders.matchQuery("fileName", keyword).operator(Operator.AND)) //根据tom分词查询name,默认or  
                //.setQuery(QueryBuilders.multiMatchQuery("tom", "name", "age")) //指定查询的字段
                //.setQuery(QueryBuilders.queryString("name:to* AND age:[0 TO 19]")) //根据条件查询,支持通配符大于等于0小于等于19  
                //.setQuery(QueryBuilders.termQuery("fileName", "*"))//查询时不分词  
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                /*
                 * 分页查询 
                 */
                .setFrom(page.getPageNo())
                .setSize(page.getPageSize())//分页
                //.addSort("createAt", SortOrder.DESC)//排序  
				  // 设置查询关键词
				  //.setQuery(QueryBuilders.matchQuery(term, queryString))
				  //.addHighlightedField(term)
				  //.setHighlighterPreTags("<em>")
				  //.setHighlighterPostTags("</em>")
				  // 设置查询数据的位置,分页用
				//.setFrom(0)
				// 设置查询结果集的最大条数
				//.setSize(60)
				// 设置是否按查询匹配度排序
				.setExplain(true);
		
				/*
				 * 根据关键词分词查询 
				 */
				if(!"".equals(keyword) && null != keyword) {
					searchRequestBuilder.setQuery(QueryBuilders.matchQuery("fileName", keyword).operator(Operator.AND)); //根据keyword分词查询fileName,默认or 
				}
				// 最后就是返回搜索响应信息
				  //.execute()
				  //.actionGet();
				SearchResponse response = searchRequestBuilder.get();
        SearchHits searchHits = response.getHits();
        long total = searchHits.totalHits;
        SearchHit[] hits = searchHits.getHits();
        
        // 后缀名
     	String sufName = null;
        
        for (SearchHit searchHit : hits) {
        	String string = searchHit.getSourceAsString();
        	JSONObject jsonObject = JSONObject.parseObject(string);
        	OfficeFileEntity officeFile = JSON.toJavaObject(jsonObject, OfficeFileEntity.class);
        	//Map<String, Object> map = new HashMap<String, Object>();
        	// 获取文件后缀名
        	String filePath = officeFile.getFilePath();
        	String fileNameWithSub = PreSubFixUtil.getFileNameWithSub(filePath);
        	
        	sufName = PreSubFixUtil.getFileNameWithSuffix(fileNameWithSub);
        	// 通过工具类利用文件名后缀将各种类型的文件内容读取出来
        	String officeContent = POIUtil.off2String(new File(filePath), sufName);
        	officeFile.setContentText(officeContent);
        	
        	
        	/*map.put("fileId", officeFile.getFileId());
        	map.put("fileName", officeFile.getFileName());
        	map.put("filePath", officeFile.getFilePath());
        	map.put("userNameCreate", officeFile.getStatus());
        	map.put("userNameUpdate", officeFile.getUserNameCreate());
        	map.put("fileLevelId", officeFile.getUserNameUpdate());
        	map.put("status", officeFile.getStatus());
        	map.put("gmtCreate", officeFile.getGmtCreate());
        	map.put("gmtModified", officeFile.getGmtModified());
        	map.put("contentText", officeContent);*/
        	
        	lists.add(officeFile);
		}
        page.setRows(lists);
		return page;
	}
    
	/**
	 * 高亮查询索引库中的所有索引
	 * @param page	PageUtil<Map<String, Object>>
	 * @param form 
	 * @param solrUrl	连接 SolrClient 时所需的 URL
	 * @param strPageIndex	页号
	 * @return	PageUtil
	 */
	public static Page<OfficeFileEntity> queryAllHighlightByPage(Page<OfficeFileEntity> page, Query form, String name, String type) {
		
		List<OfficeFileEntity> lists = new ArrayList<OfficeFileEntity>();
		SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(name).setTypes(type);
		// 关键词
		String keyword = form.getAsString("keyword");
		QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(keyword);
        queryBuilder.analyzer("ik_smart");
        queryBuilder.field("fileName").field("contentText");
        //queryBuilder.defaultOperator(Operator.AND);
        
        
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field("fileName");
		highlightBuilder.field("contentText");
		highlightBuilder.preTags("<em style='color:red;font-style:normal'>");
		highlightBuilder.postTags("</em>");
		
		searchRequestBuilder
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.matchAllQuery()) //查询所有
				//.setQuery(QueryBuilders.matchQuery("fileName", keyword).operator(Operator.AND)) //根据tom分词查询name,默认or  
                //.setQuery(QueryBuilders.multiMatchQuery("tom", "name", "age")) //指定查询的字段  
                //.setQuery(QueryBuilders.queryString("name:to* AND age:[0 TO 19]")) //根据条件查询,支持通配符大于等于0小于等于19  
                //.setQuery(QueryBuilders.termQuery("fileName", "*"))//查询时不分词  
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                /*
                 * 分页查询 
                 */
                .setFrom(page.getOffset())
                .setSize(page.getPageSize())//分页，每页显示 10 条数据 
                /*
                 * 使用 IK 分词器
                 */
                .setQuery(queryBuilder)
                /*
                 * 高亮 查询
                 */
                .highlighter(highlightBuilder)
//                .highlighter(highlightBuilder2)
                //.addSort("createAt", SortOrder.DESC)//排序  
				// 设置是否按查询匹配度排序
				.setExplain(true);
		
				/*
				 * 根据关键词分词查询 
				 */
				if(!"".equals(keyword) && null != keyword) {
					searchRequestBuilder.setQuery(QueryBuilders.multiMatchQuery(keyword, "fileName", "contentText").operator(Operator.AND));
					//searchRequestBuilder.setQuery(QueryBuilders.matchQuery("fileName", keyword).operator(Operator.AND)); //根据keyword分词查询fileName,默认or
					//searchRequestBuilder.setQuery(QueryBuilders.matchQuery("contentText", keyword).operator(Operator.AND)); //根据keyword分词查询fileName,默认or 
				}
				// 最后就是返回搜索响应信息
				  //.execute()
				  //.actionGet();
				SearchResponse response = searchRequestBuilder.get();
        SearchHits searchHits = response.getHits();
        int total = (int) searchHits.totalHits;
        page.setTotal(total);
        SearchHit[] hits = searchHits.getHits();
      
        for (SearchHit searchHit : hits) {
			String string = searchHit.getSourceAsString();
			Pattern compile = Pattern.compile("\\s*|\t|\r|\n");
			Matcher matcher = compile.matcher(string);
			string = matcher.replaceAll("");
        	JSONObject jsonObject = JSONObject.parseObject(string);
        	OfficeFileEntity officeFile = JSON.toJavaObject(jsonObject, OfficeFileEntity.class);
        	// 获取对应的高亮域
        	Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
        	// 从设定的高亮域中取得指定域
        	HighlightField highlightFileName = highlightFields.get("fileName");
        	if(highlightFileName != null) {
        		// 取得定义的高亮标签
        		Text[] fileNames = highlightFileName.fragments();
        		// 为title串值增加自定义的高亮标签
        		String title = "";
                for (Text text : fileNames) {
                    title += text;
                }
                // 将追加了高亮标签的串值重新填充到对应的对象
                officeFile.setFileName(title);
        	}
        	
        	// 从设定的高亮域中取得指定域
        	HighlightField highlightFileContent = highlightFields.get("contentText");
        	if(highlightFileContent != null) {
        		// 取得定义的高亮标签
        		Text[] contentTexts = highlightFileContent.fragments();
        		// 为title串值增加自定义的高亮标签
                String contentText = "";
                for (Text text : contentTexts) {
                	contentText += text;
                }
               
                contentText = contentText.length() > 500 ? contentText.substring(0, 500) + "..." : contentText;
                // 将追加了高亮标签的串值重新填充到对应的对象
                officeFile.setContentText(contentText);
        		
        	} else {
        		String contentText = "";
        		contentText = ConstantUtil.contentText;
        		// map.put("digest", contentText);
        		officeFile.setContentText(contentText);
        	}
        	lists.add(officeFile);
		}
        page.setRows(lists);
		return page;
	}
    
    
    
    
	/**
	 * 关闭
	 */
	public static void close(){
		//client = ESClientManager.getESClient();
		if(null != getClient()){
			try {
				getClient().close();
			} catch (Exception e) {
				
			}
		}
	}

}
