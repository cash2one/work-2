package com.zz91.util.search.solr;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Properties;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.lang.StringUtils;

/**
 * 请使用 SolrUtil 提供的方法
 * @author totly (totly@huanbao.com)
 * 
 *         created on 2012-01-05
 */
public class SorlUtil {
	
//	private static Logger LOG = Logger.getLogger(SeoUtil.class);
	
	private String DEFAULT_PROP = "search.properties";
	
	private String url="http://localhost:8080/solr";
	// 连接超时
	private Integer soTimeout = 10000;
	private Integer connectionTimeout = 10000;
	// 每个主机的默认最大连接数
	private Integer defaultMaxConnectionsPerHost = 100000;
	// 最大连接
	private Integer maxTotalConnections = 200000;
	// 最大重试
	private Integer maxRetries = 1;
	
	private static SorlUtil _instance;
	
	
	public static synchronized SorlUtil getInstance(){
		if(_instance==null){
			_instance =new SorlUtil();
		}
		return _instance;
	}
	
	public void init(){
		init(DEFAULT_PROP);
	}
	
	public void init(String properties) {
		// 从配置文件读取搜索参数信息
//		LOG.debug("Loading search properties:" + properties);
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(properties);
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
//			LOG.error("An error occurred when load search properties:" + properties, e);
		}
		this.url = p.getProperty("search.url");
		this.soTimeout = Integer.valueOf(p.getProperty("search.soTimeout"));
		this.connectionTimeout = Integer.valueOf(p.getProperty("search.connectionTimeout"));
		this.defaultMaxConnectionsPerHost = Integer.valueOf(p.getProperty("search.defaultMaxConnectionsPerHost"));
		this.maxTotalConnections = Integer.valueOf(p.getProperty("search.maxTotalConnections"));
		this.maxRetries = Integer.valueOf(p.getProperty("search.maxRetries"));
	}
	
	public SolrServer getSolrServer() {
		return getSolrServer(null);
	}
	
	public SolrServer getSolrServer(String model) {
		CommonsHttpSolrServer server = null;
		try {
			server = new CommonsHttpSolrServer(url + model);
		} catch (MalformedURLException e) {
//			LOG.debug("An error occurred when load server：", e);
		}
        server.setSoTimeout(soTimeout);
        // 连接超时
        server.setConnectionTimeout(connectionTimeout);
        // 每个主机的默认最大连接数
        server.setDefaultMaxConnectionsPerHost(defaultMaxConnectionsPerHost);
        // 最大连接
        server.setMaxTotalConnections(maxTotalConnections);
        // 最大重试
        server.setMaxRetries(maxRetries);
        // 跟踪重定向
        server.setFollowRedirects(false);
        // 允许压缩
        server.setAllowCompression(true);
        // 提升性能采用流输出方式
        server.setRequestWriter(new BinaryRequestWriter());
		return server;
	}
	
	/**
	 * 创建查询条件
	 * @param keyword 关键字（用于主查询）
	 * @param filterMap 需要过滤的字段（区间查询：query.addFilterQuery("Age:[* TO 50]");）
	 * @param sortMap 排序（排序字段）Map<字段，asc/desc>
	 * @param hight 是否支持高亮
	 * @param highlightField 需要高亮的字段
	 * @param start 分页开始
	 * @param limit 分页每页限制
	 * @return
	 */
	public static SolrQuery buildSolrQuery(String keyword, Map<String,Object> filterMap, Map<String,Object> sortMap,
			boolean hight, String[] highlightField, int start, int limit){
		SolrQuery query = new SolrQuery();
        //主查询
		if (StringUtils.isNotEmpty(keyword)) {
			query.setQuery(keyword);
		} else {
			query.setQuery("*:*");
		}
        //设置高亮
        if (hight) {
            query.setHighlight(true);
            for (String field:highlightField) {
            	 query.addHighlightField(field); 
			}
            query.setHighlightSimplePre("<em>"); 
            query.setHighlightSimplePost("</em>");
		}
        //采用过滤器查询可以提高性能
        if (filterMap != null) {
            for(String filtField : filterMap.keySet()) {
            	query.addFilterQuery(filtField+":"+filterMap.get(filtField)); 
            }
		} 
//      添加排序 默认按评分排序
        if (sortMap != null) {
            for(String sortField : sortMap.keySet()) {
            	if ("desc".equals(sortMap.get(sortField))) {
            		query.addSortField(sortField, ORDER.desc);
				} else {
					query.addSortField(sortField, ORDER.asc);
				}
            }
		} else {
			query.addSortField("score", ORDER.desc);
		}
        //分页返回结果
        query.setStart(start);
        query.setRows(limit);
        return query;
	}
}