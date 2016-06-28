package src.com.zz91;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;

import com.zz91.util.search.solr.SolrQueryUtil;
import com.zz91.util.search.solr.SolrReadHandler;
import com.zz91.util.search.solr.SolrUpdateUtil;

public class Solr4Test {
	
	public static final String SOLR_HOST="http://192.168.3.28:8580/solr";
//	public static SolrServer solrServer = new ConcurrentUpdateSolrServer(SOLR_HOST+"/tradesupply", 100, 4);
//	public static SolrServer solrServer = new HttpSolrServer(SOLR_HOST+"/tradesupply");

	public static void main(String[] args) {
		Solr4Test t=new Solr4Test();
		for(int i=101;i<=140;i=i+20){
			t.testUpdate(i);
			t.testUpdateCollection(i);
		}
		
		SolrUpdateUtil.getInstance().shutdown();
//		
//		solrServer.shutdown();
//		t.testQuery();
//		t.testQueryRang();
//		solrServer.shutdown();
	}
	
	public void testUpdate(int baseinfo){
		SolrServer solrServer = SolrUpdateUtil.getInstance().getSolrServer("tradesupply");
		
		List<SolrInputDocument> list=new ArrayList<SolrInputDocument>();
		for(int i=baseinfo;i<(baseinfo+20);i++){
			SolrInputDocument document=new SolrInputDocument();
			document.addField("id", i);
			document.addField("title", "title"+i);
			document.addField("textcn", "textcn简单，浙江杭州，中国"+i);
			document.addField("textik", "浙江杭州，中国"+i);
			document.addField("kwSimple", "kwSimple简单的中国杭州"+i);
			document.addField("kwMaxWord", "从300000创建索引kwMaxWord 混合 "+i);
			document.addField("kwComplex", "kwComplex 复杂 "+i);
			document.addField("kwInt", i);
			document.addField("kwLong", i*100l);
			document.addField("kwDouble", new Double("1."+i));
			document.addField("kwDate", new Date());
			document.addField("kwFloat", new Float("2."+i));
			document.addField("dynamic_i", i*100);
			document.addField("dynamic_s", "dynamic string "+i);
			
			list.add(document);
		}
		
		try {
			solrServer.add(list);
			solrServer.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void testUpdateCollection(int baseinfo){
		SolrServer solrServer = SolrUpdateUtil.getInstance().getSolrServer("collection1");
		
		List<SolrInputDocument> list=new ArrayList<SolrInputDocument>();
		for(int i=baseinfo;i<(baseinfo+20);i++){
			SolrInputDocument document=new SolrInputDocument();
			document.addField("id", i);
			
			list.add(document);
		}
		
		try {
			solrServer.add(list);
			solrServer.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void testQuery(){
		SolrServer solrServer = new HttpSolrServer(SOLR_HOST+"/tradesupply");
		
		SolrQuery query=new SolrQuery();
//		query.setQuery("kwSimple:%E6%9D%AD%E5%B7%9E");
		query.setQuery("kwSimple:杭州");
		query.setHighlight(true);
		query.addHighlightField("kwSimple");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		try {
			QueryResponse rsp = solrServer.query(query);
			System.out.println(rsp.getResults().getNumFound());
			List<SolrDocument> doclist=rsp.getResults();
			if(doclist.size()>0){
				System.out.println(doclist.get(0).getFieldValue("title")+" "+doclist.get(0).getFieldValue("kwSimple"));
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		solrServer.shutdown();
	}
	
	public void testQueryRang() throws SolrServerException{
		
		SolrQuery query=new SolrQuery();
//		query.setQuery("kwSimple:%E6%9D%AD%E5%B7%9E");
		query.setQuery("kwSimple:杭州");
		query.setHighlight(true);
		query.addHighlightField("kwSimple");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		
		final List<Integer> docList = new ArrayList<Integer>();
		
		SolrQueryUtil.getInstance().search("tradesupply", query, new SolrReadHandler() {
			
			@Override
			public void handlerReader(QueryResponse response)
					throws SolrServerException {
				List<SolrDocument> list = response.getResults();
				for(SolrDocument doc:list){
					docList.add(Integer.valueOf(""+doc.getFieldValue("id")));
				}
				//heightLight
				response.getHighlighting();
			}
		});
		
		for(Integer i:docList){
			System.out.println(i);
		}
		
//		try {
//			QueryResponse rsp = solrServer.query(query);
//			System.out.println(rsp.getResults().getNumFound());
//			List<SolrDocument> doclist=rsp.getResults();
//			if(doclist.size()>0){
//				System.out.println(doclist.get(0).getFieldValue("title")+" "+doclist.get(0).getFieldValue("kwSimple"));
//			}
//		} catch (SolrServerException e) {
//			e.printStackTrace();
//		}
		
	}
	
	public void testDemo(){
		//SOLR 语法
	}
	
}
