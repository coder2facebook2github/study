package com.github;


import com.alibaba.fastjson.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ObjectStudy {

	private String a;

	public static void main(String... args) {

	}


	public static void getClassStudy() {
		DependencyPO dependencyPO = new DependencyPO();
		Class dependencyClass = dependencyPO.getClass();
		System.out.println(dependencyClass.getName());
	}

	public void classpath() {
		Properties properties = new Properties();
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(new File(this.getClass().getResource("/").getPath() + "common.properties"));
			properties.load(fileInputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(properties.getProperty("spring"));
	}

	public static void getHashCodeStudy() {
		DependencyPO dependencyPO = new DependencyPO();

		System.out.println("HashCode: " + dependencyPO.hashCode());
	}

	public static void cloneStudy() {
		DependencyPO dependencyPO = new DependencyPO();
		dependencyPO.setGroupId("com.spring");
		dependencyPO.setArtifactId("spring");
		dependencyPO.setVersion("1.0");
		dependencyPO.setJavaIoStudy(new JavaIoStudy());
		DependencyPO dependencyPOClone = (DependencyPO)dependencyPO.clone();

		System.out.println("dependencyPOClone version: " + dependencyPOClone.getVersion());
		System.out.println(dependencyPO.getJavaIoStudy() == dependencyPOClone.getJavaIoStudy());

		dependencyPOClone.setVersion("2.0");

		System.out.println("dependencyPOClone version: " + dependencyPOClone.getVersion());
		System.out.println("dependencyPO version: " + dependencyPO.getVersion());

		System.out.println("dependencyPOClone javaIoStudy: " + dependencyPOClone.getJavaIoStudy().test);
		System.out.println("dependencyPO javaIoStudy: " + dependencyPO.getJavaIoStudy().test);

		dependencyPO.getJavaIoStudy().test = "world";

		System.out.println("dependencyPOClone javaIoStudy: " + dependencyPOClone.getJavaIoStudy().test);
		System.out.println("dependencyPO javaIoStudy: " + dependencyPO.getJavaIoStudy().test);

	}

	public static void deepClone() {
		DependencyPO dependencyPO = new DependencyPO();
		dependencyPO.setGroupId("com.spring");
		dependencyPO.setArtifactId("spring");
		dependencyPO.setVersion("1.0");
		dependencyPO.setJavaIoStudy(new JavaIoStudy());
		DependencyPO dependencyPOClone = dependencyPO.deepClone();

		System.out.println("dependencyPOClone version: " + dependencyPOClone.getVersion());
		System.out.println(dependencyPO.getJavaIoStudy() == dependencyPOClone.getJavaIoStudy());

		dependencyPOClone.setVersion("2.0");

		System.out.println("dependencyPOClone version: " + dependencyPOClone.getVersion());
		System.out.println("dependencyPO version: " + dependencyPO.getVersion());

		System.out.println("dependencyPOClone javaIoStudy: " + dependencyPOClone.getJavaIoStudy().test);
		System.out.println("dependencyPO javaIoStudy: " + dependencyPO.getJavaIoStudy().test);

		dependencyPO.getJavaIoStudy().test = "world";

		System.out.println("dependencyPOClone javaIoStudy: " + dependencyPOClone.getJavaIoStudy().test);
		System.out.println("dependencyPO javaIoStudy: " + dependencyPO.getJavaIoStudy().test);


		System.out.println("================");
		System.out.println(dependencyPO.getJavaIoStudy().dom4jStudy == dependencyPOClone.getJavaIoStudy().dom4jStudy);
		dependencyPO.getJavaIoStudy().dom4jStudy.dom = "!";
		System.out.println("dependencyPO dom4jStudy.dom: " + dependencyPO.getJavaIoStudy().dom4jStudy.dom);
		System.out.println("dependencyPOClone dom4jStudy.dom: " + dependencyPOClone.getJavaIoStudy().dom4jStudy.dom);
	}

	public void notifyStudy() {


	}


}
