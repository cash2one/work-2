package com.zz91.util.search.solr;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;

public interface SolrReadHandler {

	public abstract void handlerReader(QueryResponse response) throws SolrServerException ;
	
}
