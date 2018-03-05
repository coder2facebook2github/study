package com.github.proxy.self_proxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//代码生成 编译 重新动态load到JVM
public class SelfClassLoader extends ClassLoader {
	private File baseDir;

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String className = SelfClassLoader.class.getPackage().getName() + "." + name;
		if (baseDir != null) {
			File classFile = new File(baseDir, name.replaceAll("\\.", "/") + ".class");
			if (classFile.exists()) {
				FileInputStream inputStream = null;
				ByteArrayOutputStream outputStream = null;
				try {
					inputStream = new FileInputStream(classFile);
					outputStream = new ByteArrayOutputStream();
					byte[] buff = new byte[1024];
					int len;
					while ((len = inputStream.read(buff)) != -1) {
						outputStream.write(buff, 0, len);
					}
					return defineClass(className, outputStream.toByteArray(), 0, outputStream.size());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (outputStream != null) {
						try {
							outputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					classFile.delete();
				}
			}
		}
		return super.findClass(name);
	}

	public SelfClassLoader() {
		String basePath = SelfClassLoader.class.getResource("").getPath();
		this.baseDir = new File(basePath);
	}
}
