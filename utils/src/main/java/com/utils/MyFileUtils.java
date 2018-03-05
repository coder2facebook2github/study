package com.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyFileUtils {

	/**
	 * 上传文件
	 * 
	 * @param mf
	 * @param path
	 *            上传文件的路径
	 */
	public static Map<String, String> upLoadFile(MultipartFile mf,
			String path) {
		Date currTime = new Date();
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddhhmmssS",
				Locale.US);
		Map<String, String> map = null;
		String fileLocal = null;
		if (!mf.isEmpty()) {
			String fileName = null;
			try {
				fileName = new String(formatter2.format(currTime).getBytes(
						"utf-8"))
						+ mf.getOriginalFilename();
				;
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			try {
				FileUtils.copyInputStreamToFile(mf.getInputStream(), new File(path + fileName));
				fileLocal=path;
				map = new HashMap<String, String>();
				map.put(fileName, fileLocal);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	@SuppressWarnings("rawtypes")
	public static Object getMapKey(Map map) {
		if (map == null) {
			return null;
		}
		Object key = null;
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			key = entry.getKey();
		}
		return key;
	}

	@SuppressWarnings("rawtypes")
	public static Object getMapValue(Map map) {
		if (map == null) {
			return null;
		}
		Object value = null;
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			value = entry.getValue();
		}
		return value;
	}

	/**
	 * 根据路径删除文件
	 * 
	 * @param path
	 */
	public static void deleteFile(String path) {
		if (path != null && !path.trim().equals("")) {
			File file = new File(path);
			if(file.isFile()){
				file.delete();
			}
		}
	}

	public static String getExtension(File f) {
		return (f != null) ? getExtension(f.getName()) : "";
	}

	public static String getExtension(String filename) {
		return getExtension(filename, "");
	}

	public static String getExtension(String filename, String defExt) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > -1) && (i < (filename.length() - 1))) {
				return filename.substring(i + 1);
			}
		}
		return defExt;
	}

	public static String trimExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');
			if ((i > -1) && (i < (filename.length()))) {
				return filename.substring(0, i);
			}
		}
		return filename;
	}
}
