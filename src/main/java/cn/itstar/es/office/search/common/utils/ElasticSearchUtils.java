package cn.itstar.es.office.search.common.utils;

import java.net.InetAddress;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * ElasticSearch 工具类
 */
public class ElasticSearchUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchUtils.class);

	/**
	 * ES服务器IP
	 */
	private static String HOST = PropertiesUtils.getInstance("es").get("es.host");

	/**
	 * ES的端口号
	 */
	private static int PORT = PropertiesUtils.getInstance("es").getInt("es.port");

	/**
	 * ES的集群名称
	 */
	private static String LUSTERNAME = PropertiesUtils.getInstance("es").get("es.lusterName");
	
	/**
	 * ES的节点名称
	 */
	private static String NODENAME = PropertiesUtils.getInstance("es").get("es.nodeName");
	
	/**
	 * ES的索引名称
	 */
	private static String INDEX_ = PropertiesUtils.getInstance("es").get("es.Index_");
	
	/**
	 * ES的索引类型
	 */
	private static String TYPE_ = PropertiesUtils.getInstance("es").get("es.Type_");

	/**
	 * 可用连接实例的最大数目，默认值为8；
	 * 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	 *//*
	private static int MAX_ACTIVE = PropertiesUtils.getInstance("redis").getInt("redis.max_active");

	*//**
	 * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	 *//*
	private static int MAX_IDLE = PropertiesUtils.getInstance("redis").getInt("redis.max_idle");

	*//**
	 * 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	 *//*
	private static int MAX_WAIT = PropertiesUtils.getInstance("redis").getInt("redis.max_wait");

	*//**
	 * 超时时间
	 *//*
	private static int TIMEOUT = PropertiesUtils.getInstance("redis").getInt("redis.timeout");*/
	
	private static Client client = null;

	/**
	 * 连接 ElasticSearch
	 * @return
	 */
	@SuppressWarnings("resource")
	public static Client initClient() {
		
		//Client client = null;

		try {
			
//			client = new PreBuiltTransportClient(Settings.EMPTY)
//					.addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.1.20"), 9300))
////					.addTransportAddress(new TransportAddress(InetAddress.getByName("host2"), 9300));

//					;
					//			LoggerUtils.fmtDebug(clazz, "创建Elasticsearch Client 开始");
			Settings settings = Settings.builder().put("cluster.name", LUSTERNAME)
					.put("client.transport.sniff", true).build();
			client  = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(InetAddress.getByName(HOST), PORT));
		
		} catch (Exception e) {
			System.out.println("创建Client异常:" + e);
		}
		return client;
	}
	
	public static Client getClient() {
		return client;
	}
	
	public static String getESIndex() {
		return INDEX_;
	}
	
	public static String getESType() {
		return TYPE_;
	}
}