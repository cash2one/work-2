package com.zz91.util.lang;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.file.FileUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.log.LogUtil;

import net.sf.json.JSONObject;
/**
 * 敏感词过滤工具 
 * PS：本地要使用这个工具，必须在web.properties里写上 7006服务器的 ip地址。不然会卡死，不能读取敏感词
 * 
 * @author kongsj
 * @date 2013-01-28
 *
 */
public class SensitiveUtils {
	
	final static String ZZ91_SENSITIVE= "zz91_sensitive";
	final static String SENSITIVE_URL = "/mnt/data/keylimit/limit";
	
	public final static String KEY_RESULT = "filterValue"; //map中 过滤结果 的key
	public final static String KEY_SET = "sensitiveSet"; // map中 敏感词set 的key
	
	private static Mongo mongo =null;
	private static DB db = null;
	private static DBCollection dbc = null;
	public static String DB_NAME = "sensitive";
	public static String DB_COLLECTION = "info";
	public static String IP = "10.171.223.228"; // 7006 服务器
	public static Integer PORT = 27017;
	
	/**
	 * 初始化配置
	 */
	@SuppressWarnings({ "unchecked" })
	public static void init(String properties){
		// 从配置文件读取缓存服务器信息
//		LOG.debug("Loading cache properties:" + properties);
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			map = FileUtils.readPropertyFile(properties,HttpUtils.CHARSET_UTF8);
		} catch (IOException e) {
			init();
		}
		// 初始化 IP 和 端口 port
		String ip = ""+map.get("sensitive.ip");
		if (StringUtils.isNotEmpty(ip)&&!"null".equals(ip)) {
			IP = ip;
		}
		String port = ""+ map.get("sensitive.port");
		if (StringUtils.isNotEmpty(port)&&!"null".equals(port)&&StringUtils.isNumber(port)) {
			PORT = Integer.valueOf(port);
		}
		init();
	}
	
	/**
	 * 过滤敏感信息，替换为 * 
	 * 提示 敏感词有 哪些
	 * @param filterText 
	 * @return map 包含两个重要参数 sensitiveSet 发现的敏感词 type:set;
	 * 								filterValue 敏感词过滤后的信息内容 type:string
	 * @throws Exception
	 */
	
	private static void init(){
		try {
			mongo = new Mongo(IP, PORT);
		} catch (UnknownHostException e1) {
		} catch (MongoException e1) {
		}
		// 连接池配置
		MongoOptions options = mongo.getMongoOptions();
		options.autoConnectRetry = true;
		options.connectionsPerHost = 20;
		options.connectTimeout = 0;
		options.maxAutoConnectRetryTime = 12000;
		options.maxWaitTime = 12000;
		options.socketKeepAlive = true;
		options.socketTimeout = 0;
		db = mongo.getDB(DB_NAME);		// 初始化数据库
		dbc = db.getCollection(DB_COLLECTION); // 获取dbc
		updateCache();
	}
	
	/**
	 * 
	 * @param filterText
	 * @return map key有两个 sensitiveSet：敏感词组成的set;filterValue：过滤后的结果(敏感词以*代替)
	 * @throws Exception
	 */
	public static Map<String, Object> getSensitiveFilter(String filterText) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isEmpty(filterText)) {
			map.put("sensitiveSet", "");
			map.put("filterValue", "");
			return map;
		}
		// 获取敏感词 list
		List<String> listWord = getlistWord();
		
		Matcher m = null;
		Set<String> sensitiveSet = new HashSet<String>();
		for (int i = 0; i < listWord.size(); i++) {
			Pattern p = Pattern.compile(listWord.get(i),Pattern.CASE_INSENSITIVE);
			StringBuffer sb = new StringBuffer();
			m = p.matcher(filterText);
			while (m.find()) {
				sensitiveSet.add(m.group());
				m.appendReplacement(sb, "*");
			}
			m.appendTail(sb);
			filterText = sb.toString();
		}
		map.put("sensitiveSet", sensitiveSet);
		map.put("filterValue", filterText);
		return map;
	}
	
	/**
	 * 验证是否有敏感词 
	 * @param filterText
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public static Boolean validateSensitiveFilter(String filterText) throws IOException {
		if (StringUtils.isEmpty(filterText)) {
			return false;
		}
		// 获取敏感词 list
		List<String> listWord = getlistWord();
		
		Matcher m = null;
		for (int i = 0; i < listWord.size(); i++) {
			Pattern p = Pattern.compile(listWord.get(i),Pattern.CASE_INSENSITIVE);
			m = p.matcher(filterText);
			while (m.find()) {
				LogUtil.getInstance().log("admin", "sensitiveUtils", "127.0.0.1", listWord.get(i), "minganci");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 过滤敏感信息
	 * @param filterText 需要过滤的信息
	 * @param filter 过滤的 符号例如："*" 则所有敏感词过滤以"*"代替
	 * @return 
	 * @throws Exception
	 */
	public static String getSensitiveValue(String filterText,String filter) throws Exception {
		if (StringUtils.isEmpty(filterText)) {
			return "";
		}
		// 获取敏感词 list
		List<String> listWord = getlistWord();
		// 判断过滤符号
		if(filter==null){
			filter = "*";
		}
		Matcher m = null;
		for (int i = 0; i < listWord.size(); i++) {
			Pattern p = Pattern.compile(listWord.get(i),Pattern.CASE_INSENSITIVE);
			StringBuffer sb = new StringBuffer();
			m = p.matcher(filterText);
			while (m.find()) {
				m.appendReplacement(sb, filter);
			}
			m.appendTail(sb);
			filterText = sb.toString();
		}
		return filterText;
	}

	/**
	 * 更新敏感词缓存 接口 缓存有效时间 12小时
	 * @throws IOException 
	 * @throws Exception
	 */
	public static void updateCache(){
		List<String> listWord = new ArrayList<String>();
		DBCursor dbCursor = dbc.find();
		while (dbCursor.hasNext()) {
			JSONObject jo = JSONObject.fromObject(dbCursor.next());
			listWord.add(""+jo.get("words"));
		}
		MemcachedUtils.getInstance().getClient().set(ZZ91_SENSITIVE,0, listWord);
	}
	
	/**
	 * 添加敏感词并更新缓存
	 * @param words
	 */
	public static void add(String words){
		// 新插入数据
		DBObject dbo = new BasicDBObject();
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("words", words);
		dbo.putAll(map);
		// 检索是否重复
		DBObject result = dbc.findOne(dbo); //检索
		if (result!=null) {
			return ;
		}
		dbc.save(dbo); // 插入
		updateCache(); // 更新缓存
	}
	
	/**
	 * 获取搜索敏感词从mongodb
	 * @return
	 * @throws IOException
	 */
	public static List<String> queryAll(){
		List<String> result = new ArrayList<String>();
		DBCursor dbCursor = dbc.find();
		while (dbCursor.hasNext()) {
			JSONObject jo = JSONObject.fromObject(dbCursor.next());
			result.add(jo.getString("words"));
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private static List<String> getlistWord() throws IOException{
		List<String> listWord = (List<String>) MemcachedUtils.getInstance().getClient().get(ZZ91_SENSITIVE);
		if (listWord == null || listWord.size() <= 0) {
			updateCache();
			listWord = (List<String>) MemcachedUtils.getInstance().getClient().get(ZZ91_SENSITIVE);
			// 如果 mongodb或者 memcache出现问题， 则获取本地文件。
			if (listWord == null || listWord.size() <= 0) {
				FileReader reader = new FileReader(SENSITIVE_URL);
				BufferedReader br = new BufferedReader(reader);
				String s = null;
				while ((s = br.readLine()) != null) {
					if(StringUtils.isNotEmpty(s)&&s.trim().length()>1){
						listWord.add(s.trim());
					}
				}
				br.close();
				reader.close();
				MemcachedUtils.getInstance().getClient().set(ZZ91_SENSITIVE,3600*12, listWord);
			}
			
		}
		return listWord;
	}

	public static void main(String[] args) throws Exception {
		String filterText =" dfdf毛泽东";
		MemcachedUtils.getInstance().init("web.properties");
		init("web.properties");
		updateCache();
		System.out.println(SensitiveUtils.getSensitiveFilter(filterText));
		System.out.println(SensitiveUtils.validateSensitiveFilter(filterText));
		System.out.println(SensitiveUtils.getSensitiveValue(filterText, ""));
	}
}
