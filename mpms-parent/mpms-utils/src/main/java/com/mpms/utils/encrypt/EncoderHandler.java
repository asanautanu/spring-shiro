package com.mpms.utils.encrypt;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * 
 * 加密
 * 
 * @author lc
 * @data 2015年11月2日 下午2:37:17
 * 
 */
public class EncoderHandler {

	/**
	 * md5加密
	 */
	private static final String ALGORITHM = "MD5";

	/**
	 * sha-1加密
	 */
	private static final String SHA1 = "SHA-1";

	/**
	 * 字符数组表示的16进制位
	 */
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 随机数生成器
	 */
	private static SecureRandom random = new SecureRandom();

	/**
	 * 加密
	 * 
	 * @param algorithm
	 *            加密类型
	 * @param str
	 *            原始数据
	 * @return
	 * @exception
	 * @auth lc
	 * @date 2015年11月2日 下午2:40:35
	 */
	public static String encode(String algorithm, String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * md5加密
	 * 
	 * @param str
	 *            原始数据
	 * @return
	 * @exception
	 * @auth lc
	 * @date 2015年11月2日 下午2:41:20
	 */
	public static String encodeByMD5(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 将byte数据转成string
	 * 
	 * @param bytes
	 * @return
	 * @exception
	 * @auth lc
	 * @date 2015年11月2日 下午2:41:42
	 */
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	/**
	 * base64编码
	 * 
	 * @param source
	 * @return
	 * @exception
	 * @auth lc
	 * @date 2015年11月2日 下午2:42:32
	 */
	public static String encodeBase64(String source) {
		if (StringUtils.isNotBlank(source)) {
			byte[] bs = Base64.encodeBase64(source.getBytes());
			return new String(bs);
		}
		return null;
	}

	/**
	 * base64解码
	 * 
	 * @param source
	 * @return
	 * @exception
	 * @auth lc
	 * @date 2015年11月2日 下午2:42:45
	 */
	public static String decodeBase64(String source) {
		if (StringUtils.isNotBlank(source)) {
			byte[] bs = Base64.decodeBase64(source.getBytes());
			return new String(bs);
		}
		return null;
	}

	/**
	 * sha1加密
	 * 
	 * @param source
	 * @return
	 * @exception
	 * @auth lc
	 * @date 2015年11月2日 下午2:42:57
	 */
	public static String encodeSha1(String source) {
		if (StringUtils.isNotBlank(source)) {
			return DigestUtils.sha1Hex(source);
		}
		return null;
	}

	/**
	 * md5加密
	 * 
	 * @param source
	 * @return
	 * @exception
	 * @auth lc
	 * @date 2015年11月2日 下午2:43:06
	 */
	public static String encodeMD5(String source) {
		if (StringUtils.isNotBlank(source)) {
			return DigestUtils.md5Hex(source);
		}
		return null;
	}

	/**
	 * 生成随机的Byte[]作为salt.
	 * 
	 * @param numBytes
	 * @return
	 * @date 2015年11月4日 下午5:49:10
	 * @author luogang
	 */
	public static byte[] generateSalt(int numBytes) {
		Validate.isTrue(numBytes > 0,
				"numBytes argument must be a positive integer (1 or larger)",
				numBytes);

		byte[] bytes = new byte[numBytes];
		random.nextBytes(bytes);
		return bytes;
	}

	/**
	 * 对文件进行sha1散列.
	 * 
	 * @param input
	 * @param salt
	 * @param iterations
	 * @return
	 * @date 2015年11月4日 下午5:54:08
	 * @author luogang
	 */
	public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
		return digest(input, SHA1, salt, iterations);
	}

	/**
	 * 对字符串进行散列, 支持md5与sha1算法.
	 * 
	 * @param input
	 * @param algorithm
	 * @param salt
	 * @param iterations
	 * @return
	 * @date 2015年11月4日 下午5:53:52
	 * @author luogang
	 */
	private static byte[] digest(byte[] input, String algorithm, byte[] salt,
			int iterations) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			if (salt != null) {
				digest.update(salt);
			}

			byte[] result = digest.digest(input);

			for (int i = 1; i < iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return result;
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Hex编码.
	 * 
	 * @param input
	 * @return
	 * @date 2015年11月4日 下午6:06:01
	 * @author luogang
	 */
	public static String encodeHex(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码.
	 * 
	 * @param input
	 * @return
	 * @date 2015年11月4日 下午6:05:44
	 * @author luogang
	 */
	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(new String( decodeHex(encodeHex("￥%~~~".getBytes()))));
	}

}