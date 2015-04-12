package com.zhong.mobilephonetools.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtiles {
	/**
	 * ��һ���ֽ���ת����String����
	 * 
	 * @param in
	 *            �ֽ���
	 * @return �������Ӧ��String
	 * @throws IOException
	 */
	public static String formateStream2String(InputStream in) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int len = 0;
		byte[] bu = new byte[1024];
		while ((len = in.read(bu)) > 0) {
			bos.write(bu, 0, len);
		}
		in.close();
		String result = bos.toString();
		return result;
	}
}
