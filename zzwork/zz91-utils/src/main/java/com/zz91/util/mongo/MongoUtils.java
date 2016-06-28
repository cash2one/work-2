package com.zz91.util.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;

/**
 * mongo工具
 * 
 * 搜索符号对比 "<" => "$lt" "<=" => "$lte" ">" => "$gt" ">=" => "$gte" "!=" => "$ne"
 * 
 * @author kongsj
 *
 */
public class MongoUtils {

	public static String DB_NAME = "sensitive";
	public static String DB_COLLECTION = "info";
	public static String IP = "121.40.103.220"; // 7006 服务器
	public static Integer PORT = 27017;

	public static void add(String words) {
		
	}

	public static void del(String words) {

	}

	public static List<String> queryAll() {
		List<String> resultList = new ArrayList<String>();
		return resultList;
	}

	public static void main(String[] args) throws UnknownHostException, MongoException {
		Mongo mongo = new Mongo(IP, PORT);
		// 连接池配置
		MongoOptions options = mongo.getMongoOptions();
		options.autoConnectRetry = true;
		options.connectionsPerHost = 20;
		options.connectTimeout = 0;
		options.maxAutoConnectRetryTime = 12000;
		options.maxWaitTime = 12000;
		options.socketKeepAlive = true;
		options.socketTimeout = 0;
		DB db = mongo.getDB(DB_NAME);
		DBCollection dbc = db.getCollection(DB_COLLECTION);
		// 新插入数据
//		DBObject dbo = new BasicDBObject();
//		Map<String , Object> map = new HashMap<String, Object>();
//		map.put("words", "老虎机");
//		map.put("gmt_created", new Date().getTime());
//		dbo.putAll(map);
//		dbc.save(dbo);

		// 检索
		DBObject dbo2 = new BasicDBObject();
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("words", "老虎机1");
		dbo2.putAll(map2);
		DBObject dbCursor = dbc.findOne(dbo2);
		if (dbCursor!=null) {
			
		}
		
		// 删除
//		DBObject dbo3 = new BasicDBObject();
//		Map<String, Object> map3 = new HashMap<String, Object>();
//		map3.put("words", "老虎机");
//		dbo3.putAll(map3);
//		dbc.remove(dbo3);

		System.out.println("ye");
	}

}