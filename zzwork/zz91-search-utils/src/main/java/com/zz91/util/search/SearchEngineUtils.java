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

import com.zz91.util.lang.StringUtils;

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
	
	private static Map<String, SphinxClient> _instanceMap = new HashMap<String, SphinxClient>();
	
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
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(properties);
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			LOG.error("An error occurred when load search properties:"
					+ properties, e);
		}
		
		this.host = p.getProperty("search.host");
		this.port = Integer.valueOf(p.getProperty("search.port"));
		
		// 新搜索引擎接口添加
		initNewEngine(p);
	}
	
	public void initNewEngine(Properties p){
		for (Object key:p.keySet()) {
			if (key==null) {
				continue;
			}
			String keyStr = key.toString();
			String name = "";
			String config = "";
			if (keyStr.endsWith(".search.config")) {
				name = keyStr.replace(".search.config", "");
				config = p.getProperty(keyStr);
			}
			String[] configArray = config.split(":");
			if (configArray.length!=2) {
				continue;
			}
			if (name==null||"".equals(name)) {
				continue;
			}
			_instanceMap.put(name, new SphinxClient(configArray[0], Integer.valueOf(configArray[1])));
		}
	}
	
	public SphinxClient getClient(){
		return new SphinxClient(host, port);
	}
	
	public SphinxClient getClient(String name){
		do {
			if (StringUtils.isEmpty(name)) {
				break;
			}
			SphinxClient sc = _instanceMap.get(name);
			if (sc==null) {
				break;
			}
			return sc;
			
		} while (false);
		return new SphinxClient(host, port);
	}
	
	public Map<String, Object> resolveResult(SphinxResult res, SphinxMatch info){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		for ( int a=0; a<res.attrNames.length; a++ ){
			if ( res.attrTypes[a]==SphinxClient.SPH_ATTR_MULTI || res.attrTypes[a]==SphinxClient.SPH_ATTR_MULTI64 ){
				continue;
			} else {
				resultMap.put(res.attrNames[a], info.attrValues.get(a));
			}
		}
		return resultMap;
	}
}
