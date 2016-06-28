/**
 * @author kongsj
 * @date 2014年9月5日
 * 
 */

package com.zz91.util.encrypt;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class AES {

	/**
	 * @param key
	 *            随机字符串 32 位
	 * @param uid
	 *            用户名
	 * @param pwd
	 *            密码
	 * @param text
	 *            需要加密的数据
	 * @return
	 */
	public static String encrypt(String key, String uid, String pwd, String text)
			throws Exception {
		byte[] a = getMD5(key.substring(0, 16) + "#" + uid + "#" + pwd);
		byte[] b = getMD5(key.substring(16, 32) + "#" + pwd + "#" + uid);
		return encryptAES(a, b, text);
	}

	/**
	 * @param key
	 *            随机字符串 32 位
	 * @param uid
	 *            用户名
	 * @param pwd
	 *            密码密文
	 * @param text
	 *            需要解密的数据
	 * @return
	 */
	public static String decrypt(String key, String uid, String pwd, String text)
			throws Exception {
		byte[] a = getMD5(key.substring(0, 16) + "#" + uid + "#" + pwd);
		byte[] b = getMD5(key.substring(16, 32) + "#" + pwd + "#" + uid);
		return decryptAES(a, b, text);
	}

	// AES算法加密
	private static String encryptAES(byte[] key, byte[] iv, String text)
			throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		int blockSize = cipher.getBlockSize();
		byte[] textBytes = text.getBytes();
		int plaintextLength = textBytes.length;
		if (plaintextLength % blockSize != 0) {
			plaintextLength += (blockSize - (plaintextLength % blockSize));
		}

		byte[] plaintext = new byte[plaintextLength];
		System.arraycopy(textBytes, 0, plaintext, 0, textBytes.length);
		SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
		IvParameterSpec ivspec = new IvParameterSpec(iv);

		cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
		byte[] encrypted = cipher.doFinal(plaintext);
		return encoder(encrypted).trim();
	}

	// AES算法解密
	private static String decryptAES(byte[] key, byte[] vi, String text)
			throws Exception {
		byte[] encrypted = decoder(text);
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
		IvParameterSpec ivspec = new IvParameterSpec(vi);

		cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
		byte[] original = cipher.doFinal(encrypted);
		return new String(original).trim();
	}

	// MD5加密 @param text
	private static byte[] getMD5(String text) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(text.getBytes());
		return md.digest();
	}

	// BASE64编码
	public static String encoder(byte[] b) {
		return new BASE64Encoder().encode(b);
	}

	// BASE64解码
	public static byte[] decoder(String s) throws Exception {
		return new BASE64Decoder().decodeBuffer(s);
	}

}
