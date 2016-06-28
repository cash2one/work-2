package com.zz91.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtils {

	private final static Logger LOG = Logger.getLogger(HttpUtils.class);

	private static HttpUtils _instance = null;

	private static PoolingClientConnectionManager connectionManager = null;

	private static HttpClient client = null;

	static {

		// // 每主机最大连接数和总共最大连接数，通过hosfConfiguration设置host来区分每个主机
		//
		// client.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(8);
		//
		// client.getHttpConnectionManager().getParams().setMaxTotalConnections(48);
		//
		//// client.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(16);
		////
		//// client.getHttpConnectionManager().getParams().setMaxTotalConnections(100);
		//
		// client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		//
		// client.getHttpConnectionManager().getParams().setSoTimeout(5000);
		//
		// client.getHttpConnectionManager().getParams().setTcpNoDelay(true);
		//
		// client.getHttpConnectionManager().getParams().setLinger(1000);
		//
		// // 失败的情况下会进行3次尝试,成功之后不会再尝试
		//
		// client.getHttpConnectionManager().getParams().setParameter(
		// HttpMethodParams.RETRY_HANDLER,
		// new DefaultHttpMethodRetryHandler());
		// 以上是老代码
		connectionManager = new PoolingClientConnectionManager();
		connectionManager.setMaxTotal(1);
		client = new DefaultHttpClient(connectionManager);
		client.getParams().setParameter("http.socket.timeout", 10*1000);
		client.getParams().setParameter("http.connection.timeout", 10*1000);
		client.getParams().setParameter("http.connection-manager.timeout", 100000000L);

	}

	public static synchronized HttpUtils getInstance() {
		if (_instance == null) {
			_instance = new HttpUtils();
		}
		return _instance;
	}

	/**
	 * 获取用户的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		if (ip != null && ip.contains(",")) {
			ip = ip.split(",")[0].trim();
		}

		return ip;
	}

	/**
	 * 设置cookie
	 * 
	 * @param response
	 *            : 从外部传进来的response对象,不可以为null
	 * @param key
	 *            : cookie的健
	 * @param value
	 *            : cookie的值
	 * @param domain
	 *            : cookie所在的域,可以为null,为null时按时默认的域存储
	 * @param maxAge
	 *            : cookie最大时效,以秒为单位,为null时表示不设置最大时效,按照浏览器进程结束
	 */
	public void setCookie(HttpServletResponse response, String key, String value, String domain, Integer maxAge) {
		Cookie c = new Cookie(key, value);
		if (domain != null && domain.length() > 0) {
			c.setDomain(domain);
		}
		if (maxAge != null) {
			c.setMaxAge(maxAge);
		}
		c.setPath("/");
		response.addCookie(c);
	}

	/**
	 * 从cookie中得到值
	 * 
	 * @param request
	 * @param key
	 *            :cookie名称
	 * @param domain
	 *            :域名
	 * @return
	 */
	public String getCookie(HttpServletRequest request, String key, String domain) {
		if (key == null || "".equals(key)) {
			return null;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if (key.equals(c.getName())) {
					return c.getValue();
				}
			}
		}
		return null;
	}

	public final static String CHARSET_DEFAULT = "ISO-8859-1";
	public final static String CHARSET_UTF8 = "utf-8";
	public final static int HTTP_CONNECT_TIMEOUT = 100000;
	public final static int HTTP_SO_TIMEOUT = 1200000;

	/**
	 * 默认连接10秒超时，数据读取120秒超时
	 * 
	 * @param url
	 * @param charset
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String httpGet(String url, String charset){
		return httpGet(url, charset, HTTP_CONNECT_TIMEOUT, HTTP_SO_TIMEOUT);
	}

	/**
	 * @param url
	 *            ：请求的URL
	 * @param charset
	 *            ：编码
	 * @param connectionTimeout
	 *            ：连接超时时间，单位毫秒
	 * @param soTimeout
	 *            ：数据读取超时时间，单位毫秒
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String httpGet(String url, String charset, int connectionTimeout, int soTimeout){
		if (charset == null) {
			charset = HttpUtils.CHARSET_DEFAULT;
		}
		String responseString = "";
		HttpUriRequest httpget = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpget);

			HttpEntity entity = response.getEntity();

			// System.out.println("----------------------------------------");
			// System.out.println(response.getStatusLine());
			if (entity != null) {
				// System.out.println("Responsecontent length: " +
				// entity.getContentLength());
				responseString = EntityUtils.toString(entity, charset);
				// System.out.println(EntityUtils.toString(entity));
			}
			// System.out.println("----------------------------------------");

		} catch (ClientProtocolException e) {
			// System.err.println(e);
		} catch (IOException e) {
			// System.err.println(e);
		} catch(Exception e){
			LOG.error("http error Failed connect to api. error msg:"+e.getMessage()+" url:"+url);
		}finally {
			if (httpget != null) {
				httpget.abort();
			}
		}
		return responseString;

	}

	public String httpPost(String url, NameValuePair[] data, String charset) throws HttpException, IOException {
		return httpPost(url, data, charset, HTTP_CONNECT_TIMEOUT, HTTP_SO_TIMEOUT);
	}

	/**
	 * @param url
	 * @param data
	 *            : NameValuePair[] data = { new NameValuePair("id",
	 *            "youUserName"), new NameValuePair("passwd", "yourPwd") };
	 * @param charset
	 *            : 针对post后返回的页面设置字符编码
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String httpPost(String url, NameValuePair[] data, String charset, int connectionTimeout, int soTimeout)
			throws HttpException, IOException {
		if (charset == null) {
			charset = CHARSET_DEFAULT;
		}
		String responseString = "";
		HttpPost httpRequst = new HttpPost(url);// 创建HttpPost对象
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (NameValuePair obj:data) {
			params.add(obj);
		}
//		params.add(new BasicNameValuePair("str", "I am Post String"));
		try {
			httpRequst.setEntity(new UrlEncodedFormEntity(params, charset));
			HttpResponse httpResponse = client.execute(httpRequst);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				responseString = EntityUtils.toString(httpEntity);// 取出应答字符串
			}
		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
			responseString = e.getMessage().toString();
		} catch (ClientProtocolException e) {
//			e.printStackTrace();
			responseString = e.getMessage().toString();
		} catch (IOException e) {
//			e.printStackTrace();
			responseString = e.getMessage().toString();
		}finally {
			if (httpRequst != null) {
				httpRequst.abort();
			}
		}
		return responseString;
	}

	public String httpResponseAsString(InputStream is, String encode) throws IOException {
		if (is == null) {
			return "";
		}

		StringBuffer out = new StringBuffer();
		String line;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, encode));
			while ((line = reader.readLine()) != null) {
				out.append(line).append("\n");
			}
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return out.toString();
	}

	public String httpClientHtml(String url, String strCharset)
			throws URISyntaxException, ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpProtocolParams.setUserAgent(httpclient.getParams(),
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9");
		String charset = strCharset;
		HttpGet httpget = new HttpGet();
		String content = "";
		httpget.setURI(new java.net.URI(url));
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// 使用EntityUtils的toString方法，传递默认编码，在EntityUtils中的默认编码是ISO-8859-1
			content = EntityUtils.toString(entity, charset);
			httpget.abort();
			httpclient.getConnectionManager().shutdown();
		}
		return content;
	}

	public static void main(String[] args) {
		System.out.println(HttpUtils.getInstance().httpGet("http://sym.zz91.com/", CHARSET_UTF8));
	}

}
