package src.com.zz91;

import java.io.IOException;

import org.apache.http.HttpException;

import com.zz91.util.http.HttpUtils;

/**
 * 控制线程,用来启动和监控行为跟踪器
 * 
 * @author Leon
 * 
 */
@SuppressWarnings("unused")
public class HttpRunThread extends Thread {

	private long sleep=100;
	
	public HttpRunThread (){
		
	}
	
	public HttpRunThread (long sleeptime, String name){
		sleep=sleeptime;
		setName(name);
	}
	
	// 每一秒钟检查一次处理状态
	public void run() {
		do{
			
			HttpUtils.getInstance().httpGet("http://192.168.2.178:8080/zz91/fragment/huzhu/newestPost.htm?categoryId=1&size=8", HttpUtils.CHARSET_UTF8);
			
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}while(true);
	}

}
