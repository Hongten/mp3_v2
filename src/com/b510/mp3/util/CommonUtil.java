/**
 * 
 */
package com.b510.mp3.util;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;

import com.b510.mp3.common.Common;

/**
 * @author Hongten
 * @created Jul 29, 2014
 */
public class CommonUtil {

	public static String getPostfix(String path) {
		if (path == null || Common.EMPTY.equals(path.trim())) {
			return Common.EMPTY;
		}
		if (path.contains(Common.FULL_SPOT)) {
			return path.substring(path.lastIndexOf(Common.FULL_SPOT) + 1, path.length());
		}
		return Common.EMPTY;
	}

	/**
	 * get file size
	 */
	public static long getFileSize(String filePath) {
		long size = 0;
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			size = file.length();
		}
		return size;
	}

	/**
	 * get song time
	 */
	public static String getSongTime(String filePath) {
		File source = new File(filePath);
		Encoder encoder = new Encoder();
		String time = "";
		String temp = "";
		try {
			MultimediaInfo m = encoder.getInfo(source);
			long ls = m.getDuration();
			long ms = (((ls % (60 * 60 * 1000)) % 60000) % 1000);
			long s = (((ls % (60 * 60 * 1000)) % 60000) / 1000);
			if (ms > 0) {
				if ((s + 1) <= 9) {
					temp = Common.COLOR + Common.CHAR_ZERO + (s + 1);
				} else {
					temp = Common.COLOR + (s + 1);
				}
			} else {
				if ((s + 1) <= 9) {
					temp = Common.COLOR + Common.CHAR_ZERO + s;
				} else {
					temp = Common.COLOR + s;
				}
			}
			time = (ls % (60 * 60 * 1000)) / 60000 + temp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}
}
