package src.com.zz91;

import java.io.Serializable;
import org.apache.solr.client.solrj.beans.Field;

public class CompanySearchDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Field
	public Integer id;
	@Field
	public String name;
	@Field
	public String detailsQuery;
	@Field
	public String mainProductBuy;
	@Field
	public String mainProductSupply;
	@Field
	public String areaName;
	@Field
	public String provinceName;
	@Field
	public String indeustryName;
	@Field
	public String businessName;
	@Field
	public String address;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetailsQuery() {
		return detailsQuery;
	}
	public void setDetailsQuery(String detailsQuery) {
		this.detailsQuery = detailsQuery;
	}
	public String getMainProductBuy() {
		return mainProductBuy;
	}
	public void setMainProductBuy(String mainProductBuy) {
		this.mainProductBuy = mainProductBuy;
	}
	public String getMainProductSupply() {
		return mainProductSupply;
	}
	public void setMainProductSupply(String mainProductSupply) {
		this.mainProductSupply = mainProductSupply;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getIndeustryName() {
		return indeustryName;
	}
	public void setIndeustryName(String indeustryName) {
		this.indeustryName = indeustryName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
}