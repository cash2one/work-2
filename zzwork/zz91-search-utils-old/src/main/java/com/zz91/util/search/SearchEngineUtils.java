/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-7-15
 */
package com.zz91.util.search;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-7-15
 */
public class SearchEngineUtils {
	
	private static Logger LOG = Logger.getLogger(SearchEngineUtils.class);
	
//	private SphinxClient client;
	private String DEFAULT_PROP = "search.properties";
	private String host="";
	private Integer port=9313;
	
	private static SearchEngineUtils _instance;
	
	public static synchronized SearchEngineUtils getInstance(){
		if(_instance==null){
			_instance =new SearchEngineUtils();
		}
		return _instance;
	}
	
	public void init(){
		init(DEFAULT_PROP);
	}
	
	public void init(String properties) {
		// 从配置文件读取缓存服务器信息
		LOG.debug("Loading search properties:" + properties);
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(
				properties);
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			LOG.error("An error occurred when load search properties:"
					+ properties, e);
		}
		
		this.host = p.getProperty("search.host");
		this.port = Integer.valueOf(p.getProperty("search.port"));
		
		// 初始化memcache client
//		createClient(p.getProperty("search.host"), Integer.valueOf(p.getProperty("search.port")));
	}
	
//	public void createClient(String host, Integer port) {
//			client = new SphinxClient(host, port);
//			LOG.debug("Search engine client initialized.");
//	}
	
	public SphinxClient getClient(){
//		if(client==null){
//			init();
//		}
//		return client;
		return new SphinxClient(host, port);
	}
	
	public Map<String, Object> resolveResult(SphinxResult res, SphinxMatch info){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		for ( int a=0; a<res.attrNames.length; a++ ){
			if ( res.attrTypes[a]==SphinxClient.SPH_ATTR_MULTI || res.attrTypes[a]==SphinxClient.SPH_ATTR_MULTI64 ){
//				System.out.print ( "(attr-type=" + res.attrTypes[a] + ")" );
				continue;
			} else {
				resultMap.put(res.attrNames[a], info.attrValues.get(a));
			}
		}
		return resultMap;
	}
}
