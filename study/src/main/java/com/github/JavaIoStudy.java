package com.github;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * java I/O API study
 */
public class JavaIoStudy implements Serializable{

	private static final long serialVersionUID = 3372405617905404331L;
	public String test = "Hello";
	public Dom4jStudy dom4jStudy = new Dom4jStudy();

	public static void main(String... args) throws IOException {
		new JavaIoStudy().copyFile();
	}

	public void writeFile() throws IOException {
		File file = new File("");
		System.out.println(file.getAbsolutePath());
//		FileInputStream inputStream = new FileInputStream(file);
		FileOutputStream outputStream = new FileOutputStream(file);
		byte[] txt = "Hello world !\n你好，世界！".getBytes(UTF_8);
		try {
			outputStream.write(txt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		outputStream.close();
	}

	public void test2() throws IOException {
		File file = new File("");

//		System.out.println(file.getAbsolutePath());
		File[] files = file.getParentFile().listFiles(pathname -> pathname.getName().indexOf("s") >= 0);
		File[] files2 = file.getParentFile().listFiles(pathname -> true);
		for (File f : files2) {
			System.out.println(f.getAbsolutePath());
			System.out.println(f.getName());
			System.out.println(f.getPath());
			System.out.println(f.getCanonicalPath() + "\n");
		}
	}

	public void readFile() throws IOException {
		File file = new File("");
		FileInputStream inputStream = new FileInputStream(file);
		StringBuilder fileContent = new StringBuilder();
		List<Byte> fileByteList = new ArrayList<>();
		byte b;
		while ((b = (byte) inputStream.read()) != -1) {
			fileByteList.add(b);
		}
		inputStream.close();
		byte[] b1 = new byte[fileByteList.size()];
		int i = 0;
		for (byte b2 : fileByteList) {
			b1[i] = b2;
			i++;
		}
		String str = new String(b1);
		System.out.println(str);
	}

	public void copyFile() throws IOException {
		File picture = new File("");
		FileInputStream fileInputStream = new FileInputStream(picture);
		File copyFile = new File("");
		FileOutputStream fileOutputStream = new FileOutputStream(copyFile);
		int b;
		while ( (b = fileInputStream.read()) != -1) {
			fileOutputStream.write(b);
		}
		fileInputStream.close();
		fileOutputStream.close();
	}
}
