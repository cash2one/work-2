package com.zz91.util.mongo;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.file.FileUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

public class MongoDBUtils {
private static Logger LOG = Logger.getLogger(MongoDBUtils.class);//日志对象
	
	private static Mongo mongo =null;//mongoDB对象
	private static DB db = null;//数据库
	public static String DB_NAME = "javadb";
	private static DBCollection dbc = null;//集合
	
	private String MONGO_PROP = "web.properties";
	private String IP="";
	private Integer PORT=27017;
	
	//懒汉式单例
	private static MongoDBUtils _instance = null;
	
	public synchronized static MongoDBUtils getInstance() {
		if (_instance == null) {
			_instance = new MongoDBUtils();
		}
		return _instance;
	}
	
	/**
	 * 初始化函数
	 * @param properties
	 */
	public void init(String properties,String dbName) {
		if (StringUtils.isEmpty(properties)) {
			properties = MONGO_PROP;
		}
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = FileUtils.readPropertyFile(properties,
					HttpUtils.CHARSET_UTF8);
			IP = (String) map.get("sensitive.ip");
			String port = ""+ map.get("sensitive.port");
			PORT = Integer.valueOf(port);
			mongo = getConnection(IP, PORT);//获得连接
			if(StringUtils.isNotEmpty(dbName)){
				db = mongo.getDB(dbName);
			}else{
				db = mongo.getDB(DB_NAME);	
			}
		} catch (IOException e) {
			LOG.error("An error occurred when load sms properties:"
					+ properties, e);
		}
	}
	
	/**
	 * 连接mongo客户端
	 * @param serverIp
	 * @param port
	 * @return
	 */
	private Mongo getConnection(String serverIp , Integer port) {
		try {
			return new Mongo(serverIp, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public DBObject findByCompanyId(Integer companyId) {
		DBObject queryObject = new BasicDBObject().append("$and",
				new BasicDBObject[] { new BasicDBObject("companyId", companyId) }); 
		return dbc.findOne(queryObject);
	}
	
	/**
	 * 获得集合
	 * @param collection
	 * @return
	 */
	private static DBCollection getCollection(String collection) {
		dbc = db.getCollection(collection);
		return dbc;
	}
	
	/**
	 * 添加索引
	 * @param collection
	 * @param basicDBObject
	 */
	public void createIndex(String collection , BasicDBObject basicDBObject){
		getCollection(collection).createIndex(basicDBObject);
	}
	
	/**
	 * 插入
	 * @param collection 集合名
	 * @param map 文档数据
	 */
	public void insert(String collection , Map<String, Object> map) {
		try {
			DBObject dbObject = map2Obj(map);
			getCollection(collection).insert(dbObject);
		} catch (MongoException e) {
			LOG.error("MongoException:" + e.getMessage());
		}
	}
	
	/**
	 * 获得集合的条数
	 * @param collection
	 * @param map
	 * @return
	 */
	public long getCount(String collection) {
		return getCollection(collection).getCount();
	}
	
	/**
	 * 根据集合名与companId查找记录
	 * @param collection
	 * @param companyId
	 * @return 
	 */
	public DBObject find(String collection , Integer companyId) {
		DBObject queryObject = new BasicDBObject().append("$and",
				new BasicDBObject[] { new BasicDBObject("companyId", companyId) });
		return getCollection(collection).findOne(queryObject);
	}
	
	/**
	 * 根据集合名与companId与xml文件名查找记录
	 * @param collection
	 * @param companyId
	 * @param fileName
	 * @return
	 */
	public DBObject find(String collection ,Integer companyId,String fileName){
		DBObject queryObject = new BasicDBObject().append("$and",
				new BasicDBObject[] { new BasicDBObject("companyId", companyId),
				new BasicDBObject("fileName", fileName)}); 
		return getCollection(collection).findOne(queryObject);
	}
	
	/**
	 * 通过company更新url
	 * @param companyId
	 * @param urlList
	 * @return
	 */
	public WriteResult updateUrlListByCompanyId(String collection,Integer companyId,List<String> urlList){
    	//条件
    	 DBObject updateCondition=new BasicDBObject(); 
 		updateCondition.put("companyId", companyId);
 		//设值
 		DBObject updatedValue=new BasicDBObject();
 		updatedValue.put("urlList", urlList);
 		updatedValue.put("gmt_modified",DateUtil.toString(new Date(),"yyyy-MM-dd HH:mm:ss"));
 		
 		DBObject updateSetValue=new BasicDBObject("$set",updatedValue);
 		return getCollection(collection).update(updateCondition, updateSetValue);
    }
	
	/**
	 * 根据companyId更新fileName
	 * @param collection
	 * @param companyId
	 * @param newFileName
	 * @return
	 */
	public WriteResult updateFileNameByCompanyId(String collection , Integer companyId , String newFileName) {
		//条件
		DBObject updateCondition=new BasicDBObject(); 
		updateCondition.put("companyId", companyId);
		//设值
		DBObject updatedValue=new BasicDBObject();
		updatedValue.put("fileName", newFileName);
		updatedValue.put("gmt_modified",DateUtil.toString(new Date(),"yyyy-MM-dd HH:mm:ss"));
		
		DBObject updateSetValue=new BasicDBObject("$set",updatedValue);
		
		return getCollection(collection).update(updateCondition, updateSetValue);
	}
	
	private static DBObject map2Obj(Map<String, Object> map) {
		DBObject dbo = new BasicDBObject();
		dbo.putAll(map);
		return dbo;
	}
}
