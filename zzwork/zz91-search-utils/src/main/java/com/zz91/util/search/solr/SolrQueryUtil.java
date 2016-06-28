/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2012-01-05
 */
package com.zz91.util.search.solr;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.http.client.HttpClient;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
//import org.apache.solr.client.solrj.impl.StreamingUpdateSolrServer;

/**
 * 该工具类用于提供搜索查询
 * 
 * 示例：
 * 系统启动
 * SolrQueryUtil.getInstance().init("web.properties");
 * 
 * 查询操作
 * SolrQuery query=new SolrQuery();
 * query.setQuery("kwSimple:杭州");
 * 
 * final List<Integer> docList = new ArrayList<Integer>();
 * 
 * SolrQueryUtil.getInstance().search("tradesupply", query, new SolrReadHandler() {
 * 
 * 		@Override
 * 		public void handlerReader(QueryResponse response)
 * 			throws SolrServerException {
 * 			List<SolrDocument> list = response.getResults();
 * 			for(SolrDocument doc:list){
 * 				docList.add(Integer.valueOf(""+doc.getFieldValue("id")));
 * 			}
 *			// response.getHighlighting();
 * 		}
 * });
 * 
 * 
 * 
 * @author mays (mays@asto.com.cn)
 * 
 *         created on 2012-11-21
 */
public class SolrQueryUtil {
	
	private static Logger LOG = Logger.getLogger(SolrQueryUtil.class);
	
	private String DEFAULT_PROP = "search.properties";
	
	private static String solrHost="http://192.168.3.30:8580/solr";
	private static int soTimeout=10000;
	private static int connectionTimeout=10000;
	
	private static SolrQueryUtil _instance;
	
	public static synchronized SolrQueryUtil getInstance(){
		if(_instance==null){
			_instance =new SolrQueryUtil();
		}
		return _instance;
	}
	
	public void init(){
		init(DEFAULT_PROP);
	}
	
	@SuppressWarnings("unchecked")
	public void init(String properties) {
		
//		LOG.debug("Loading search properties:" + properties);
		try {
			
			Map<String, Object> map = readPropertyFile(properties, "utf-8");
			solrHost=(String) map.get("search.url");
			soTimeout = Integer.valueOf(map.get("search.soTimeout").toString());
			connectionTimeout = Integer.valueOf(map.get("search.connectionTimeout").toString());
//			this.defaultMaxConnectionsPerHost =  Integer.valueOf(map.get("search.defaultMaxConnectionsPerHost").toString());
//			this.maxTotalConnections = Integer.valueOf(map.get("search.maxTotalConnections").toString());
//			this.maxRetries =  Integer.valueOf(map.get("search.maxRetries").toString());
			LOG.debug("Finish loading search properties:" + properties);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * search method
	 * @param core：即 solr core 名称，如 "/demo"
	 * @param query
	 * @param reader
	 * @throws SolrServerException 
	 */
	public void search(String coreName, SolrParams query, SolrReadHandler reader) throws SolrServerException{
		
		SolrServer solrServer = getSolrServer(coreName);
		
		try {
			QueryResponse rsp = solrServer.query(query);
			reader.handlerReader(rsp);
		} catch (SolrServerException e) {
			throw new SolrServerException(e);
		}finally{
			if(solrServer!=null){
				solrServer.shutdown();
			}
		}
		
	}
	
	private SolrServer getSolrServer(String coreName){
		return getSolrServer(coreName, null);
	}
	
	private SolrServer getSolrServer(String coreName, HttpClient httpclient){
		coreName = coreName==null?"":coreName;
		if(!coreName.startsWith("/")){
			coreName = "/"+ coreName;
		}
		HttpSolrServer solrServer = new HttpSolrServer(solrHost+coreName, httpclient);
		solrServer.setSoTimeout(soTimeout);
		solrServer.setConnectionTimeout(connectionTimeout);
		return solrServer;
	}
	
	@SuppressWarnings({ "resource", "rawtypes", "unchecked" })
	private HashMap readPropertyFile(String file,String charsetName) throws IOException {
		if (charsetName==null || charsetName.trim().length()==0){
			charsetName="utf-8";
		}
		HashMap map = new HashMap();
		InputStream is =null;
		if(file.startsWith("file:"))
			is=new FileInputStream(new File(file.substring(5)));
		else
			is=SolrQueryUtil.class.getClassLoader().getResourceAsStream(file);
		Properties properties = new Properties();
		properties.load(is);
		Enumeration en = properties.propertyNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String code = new String(properties.getProperty(key).getBytes(
					"ISO-8859-1"), charsetName);
			map.put(key, code);
		}
		return map;
	}
}