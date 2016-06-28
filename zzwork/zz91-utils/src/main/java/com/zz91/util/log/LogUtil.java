package com.zz91.util.log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.zz91.util.file.FileUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


/**LogUtil是一个记录应用系统操作行为的一个系统
 * 通过调用客户端log方法你可以传入自定义的参数来完成你的日志保存行为
 * 使用方法：log.client.host和log.appcode两个参数
 * 第一步：在web.properties文件中配置
 * 第二步：通过LogUtil.getInstance()得到LogUtil实例
 * 第二步：通过调用LogUtil实例的init()初始化host以及appcode信息
 * 第三步：通过调用LogUtil实例的log方法来记录日志信息
 * @author Leon
 *
 */
public class LogUtil {
	
	private static LogUtil _instances = null;
	private static String DEFAULT_PROP="logclient.properties";
	private static String APP_CODE="log";
	private static String HOST="http://192.168.110.119:8201/zz91-log";
	private static Map<String,String> compares=new HashMap<String, String>();	
	
	static {
		//compares
		compares =new HashMap<String, String>();
		compares.put("<", "$lt" );
		compares.put("<=", "$lte");
		compares.put(">", "$gt");
		compares.put(">=", "$gte");
		compares.put("!=", "$ne");
	}
	
	private LogUtil() {}
	/**
	 * 获取LogUtil实例
	 * @return
	 */
	public static synchronized LogUtil getInstance() {
		if (_instances == null) {
			_instances = new LogUtil();
		}
		return _instances;
	}
	

	/**
	 * 初始化方法
	 * @param properties
	 */
	public void init(){
		init(DEFAULT_PROP);
	}
	
	@SuppressWarnings("unchecked")
	public void init(String properties){
		Map<String, String> map=null;
		try {
			map = FileUtils.readPropertyFile(properties, "utf-8");
		} catch (IOException e) {
		}
		if(map!=null){
			HOST = map.get("log.host");
			APP_CODE=map.get("log.appcode");
		}
	}
	
	/**
	 * 模糊查询
	 * @param value
	 * @return	{"$regex":"value"}
	 */
	public JSONObject mgLike(String value){
		JSONObject data=new JSONObject();
		data.put("$regex", value);
		return data;
	}
	/**
	 * 逻辑运算符查询
	 * @param type	<,<=,>,>=,!=
	 * @param value
	 * @return {"$gte":value}
	 */
	public JSONObject mgCompare(String type,Object value){
		JSONObject data=new JSONObject();
		data.put(compares.get(type).toString(), value);
		return data;
	}
	/**
	 * 逻辑运算符查询(范围)
	 * @param type	<,<=,>,>=,!=
	 * @param value
	 * @return {"$gte":value}
	 */
	public JSONObject mgCompare(String staType,Object staValue,String endType,Object endValue){
		JSONObject data=new JSONObject();
		data.put(compares.get(staType).toString(), staValue);
		data.put(compares.get(endType).toString(), endValue);
		return data;
	}

	/**
	 * 要查询的列
	 * @param cl
	 * @return	{"column":1,"column":1}
	 * column后面可跟参数1,0.若1是指查询该列,若0是指排除该列
	 */
	public JSONObject mgColumns(String... column){
		JSONObject columns=new JSONObject();
		for(String cl:column){
			columns.put(cl, 1);
		}
		return columns;
	}
	
	/**
	 * 将字符串组装成JSON格式数组的字符串
	 * @param value
	 * @return  [value,value,value,....]
	 */
	public String mgArray(String... value){
		StringBuffer strB=new StringBuffer();
		strB.append("[");
		for(int i=0;i<value.length;i++){
			strB.append(value[i]);
			if(i<value.length-1){
				strB.append(",");
			}
		}
		strB.append("]");
		return strB.toString();
	}
	/**
	 * 将key-value转换为JSON模式字符串
	 * @param key
	 * @param value
	 * @return {"key":value}
	 * 
	 */
	public JSONObject mgkv(String key,Object value){
		JSONObject jb=new JSONObject();
		jb.put(key, value);
		return jb;
	}
	/**
	 * in条件查询
	 * @param obj
	 * @return {"$in":[obj,obj,obj,obj]}
	 */
	public JSONObject mgIn(Object... obj){
		JSONObject jb=new JSONObject();
		jb.put("$in", obj);
		return jb;
	}
	
	public void mongo(String operator, String operation, String ip, String data){
		NameValuePair[] param=new NameValuePair[]{
				new BasicNameValuePair("appCode", APP_CODE),
				new BasicNameValuePair("operator", operator),
				new BasicNameValuePair("operation", operation),
				new BasicNameValuePair("ip", ip),
				new BasicNameValuePair("time", String.valueOf(System.currentTimeMillis())),
				new BasicNameValuePair("data", data)
				};
		
		try {
			HttpUtils.getInstance().httpPost(HOST+"/mongo", param, HttpUtils.CHARSET_UTF8);
		} catch (HttpException e) {
		} catch (IOException e) {
		}
		
	}
	
	public void mongo(String operator, String operation, String ip){
		mongo(operator, operation, ip, null);
	}
	
	public void mongo(String operator, String operation){
		mongo(operator, operation, null, null);
	}
	
	public void log(String operator, String operation, String ip, String data,String appCode){
		if (StringUtils.isEmpty(appCode)) {
			appCode = APP_CODE;
		}
		NameValuePair[] param=new NameValuePair[]{
				new BasicNameValuePair("appCode", appCode),
				new BasicNameValuePair("operator", operator),
				new BasicNameValuePair("operation", operation),
				new BasicNameValuePair("ip", ip),
				new BasicNameValuePair("time", String.valueOf(System.currentTimeMillis())),
				new BasicNameValuePair("data", data)
				};
		try {
			HttpUtils.getInstance().httpPost(HOST+"/log", param, HttpUtils.CHARSET_UTF8);
		} catch (HttpException e) {
		} catch (IOException e) {
		}
	}
	
	public void log(String operator, String operation, String ip, String data){
		log(operator, operation, ip, data,null);
	}
	
	public void log(String operator, String operation, String ip){
		log(operator, operation, ip, null);
	}
	
	public void log(String operator, String operation){
		log(operator, operation, null, null);
	}
	
	/**
	 * @param param
	 * @param start
	 * @param limit
	 * @return 返回PageDto一样属性的JSONObject,其中集合为records(JSONArray)
	 * totals;  		//总数据数
	 * start;  		//页首
	 * sort;  			//排序字段
	 * dir; 			//desc or asc
	 * limit;  			//分页大小
	 * records;// 记录集
	 **/
	public JSONObject readMongo(Map<String, Object> param, Integer start, Integer limit){
		try {
			NameValuePair[] params=new NameValuePair[]{
					new BasicNameValuePair("params",JSONObject.fromObject(param).toString()),
					new BasicNameValuePair("start", start.toString()),
					new BasicNameValuePair("limit", limit.toString())
			};
			String resText = HttpUtils.getInstance().httpPost(HOST+"/logRead", params, HttpUtils.CHARSET_UTF8);
			return JSONObject.fromObject(resText);
		} catch (Exception e) {
			
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		LogUtil logutil=new LogUtil();
		
		//select test
		//LogUtil.HOST="apps.zz91.com/zz91-log/zz91-log";
		LogUtil.HOST="http://localhost:580/zz91-log";
		//param
		Map<String, Object> param=new HashMap<String, Object>();
		//param.put("operator", logutil.mgLike("zhangsan"));	//模糊查询
		//param.put("appCode", "esite");
		param.put("time",logutil.mgCompare(">=","1346122272998","<=","1346122272998"));	//逻辑运算符查询
		//param.put("columns", logutil.mgColumns("appCode","time"));		//要查询的列,可不指定
		//param.put("sort", "time");							//排序字段
		//param.put("dir", "desc");							//排序 asc,desc
		//param.put("data.age", logutil.mgIn(18,22,33,21));	//in查询
		//param.put("or",new JSONObject[]{logutil.mgkv("appCode","news"),
		//		logutil.mgkv("appCode", "esite")});	//逻辑or查询,
		//result		
		try {
			//logutil.mongo("erwer", "sdfsdf");
			
			JSONObject res=logutil.readMongo(param, 0, 10);
			List<JSONObject> list =res.getJSONArray("records"); 
			for(int i=0;i<list.size();i++){
				JSONObject j = (JSONObject)JSONSerializer.toJSON(list.get(i));
				System.out.println(j);
			}
			long endTime = System.currentTimeMillis();
			System.out.println("时间:"+(endTime-startTime));
			System.out.println(res.get("totals"));
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
