/**
 * @author kongsj
 * @date 2014年11月20日
 * 
 */
package com.zz91.util.validate;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zz91.util.lang.StringUtils;

public class ValidateUtils {

	/**
	 *  验证字符串中是否包含 电话号码 或 手机号码
	 * @param content
	 * @return
	 */
	public Boolean checkMobile(String content) {
		Pattern p = Pattern.compile("[\\d]+");
		Matcher m = p.matcher(content);
		Set<String> numSet = new HashSet<String>();
		while (m.find()) {
			numSet.add(m.group());
		}

		String check = "^((0\\d{2,3})?\\d{7,8})$|1[3,5,8]{1}[0-9]{9}$";
		for (String num : numSet) {
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(num);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 *  验证字符串中是否包含 电话号码 或 手机号码
	 * @param content
	 * @return
	 */
	public String checkMobileAndHide(String content,Integer length,String hideChar) {
		if (StringUtils.isEmpty(content)) {
			return "";
		}
		if (length==null) {
			length=4;
		}
		if (StringUtils.isEmpty(hideChar)) {
			hideChar = "*";
		}
		String tempChar="";
		for (int i = 0; i < length; i++) {
			tempChar = tempChar + hideChar;
		}
		Pattern p = Pattern.compile("[\\d]+");
		Matcher m = p.matcher(content);
		Set<String> numSet = new HashSet<String>();
		while (m.find()) {
			numSet.add(m.group());
		}

		String check = "^((0\\d{2,3})?\\d{7,8})$|1[3,5,8]{1}[0-9]{9}$";
		for (String num : numSet) {
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(num);
			if (matcher.matches()) {
				String tempNum = num.substring(0, num.length()-length) + tempChar;
				content = content.replace(num, tempNum);
			}
		}
		return content;
	}

	public static void main(String[] args) {
		ValidateUtils a = new ValidateUtils();
		System.out.println(a.checkMobileAndHide("诚信第一 价格优惠，要多少有多少， 13425616695", null, null));
	}
	
}
