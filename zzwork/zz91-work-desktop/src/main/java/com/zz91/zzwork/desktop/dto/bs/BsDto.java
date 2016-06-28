/**
 * 
 */
package com.zz91.zzwork.desktop.dto.bs;

import java.io.Serializable;

import com.zz91.zzwork.desktop.domain.bs.Bs;

/**
 * @author yuyh
 *
 */
public class BsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Bs bs;
	private String typeName;
	private String rightName;
	
	public Bs getBs() {
		return bs;
	}
	public void setBs(Bs bs) {
		this.bs = bs;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * @return the rightName
	 */
	public String getRightName() {
		return rightName;
	}
	/**
	 * @param rightName the rightName to set
	 */
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	
}
