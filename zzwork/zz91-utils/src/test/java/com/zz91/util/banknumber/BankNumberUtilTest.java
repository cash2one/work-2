package com.zz91.util.banknumber;

import com.zz91.util.banknumber.BankNumberUtil;

public class BankNumberUtilTest {

	public static void main(String[] args) {
//		String cardNumber = "6228 4828 9820 3884 775";// 卡号  
		String cardNumber = "6228 4803 2211 4722 513";
        cardNumber = cardNumber.replaceAll(" ", "");  
          
        //位数校验  
        if (cardNumber.length() == 16 || cardNumber.length() == 19) {  
  
        } else {  
            System.out.println("卡号位数无效");  
            return;  
        }  
          
        //校验  
        if (BankNumberUtil.checkBankCard(cardNumber) == true) {  
  
        } else {  
            System.out.println("卡号校验失败");  
            return;  
        }  
  
        //判断是不是银联，老的卡号都是服务电话开头，比如农行是95599  
        // http://cn.unionpay.com/cardCommend/gyylbzk/gaishu/file_6330036.html  
        if (cardNumber.startsWith("62")) {  
            System.out.println("中国银联卡");  
        } else {  
  
            System.out.println("国外的卡，或者旧银行卡，暂时没有收录");  
            return;  
        }  
  
        String name = BankNumberUtil.getNameOfBank(cardNumber.substring(0, 6), 0);// 获取银行卡的信息  
        System.out.println(name); 
	}

}
