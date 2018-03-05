package com.github.proxy.self_proxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//生成代理对象的代码
public class SelfProxy {

	private static String line = "\n";

	public static Object newProxyInstance(SelfClassLoader classLoader,
										  Class<?>[] interfaces,
										  SelfInvocationHandler h) {
		try {
			//1. 生成源代码
			String proxySrc = generateSrc(interfaces[0]);
			//2. 将生成的源代码输出带磁盘，保存为 .java文件
			String filePath = SelfProxy.class.getResource("").getPath();
			File file = new File(filePath + "$Proxy0.java");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(proxySrc);
			fileWriter.flush();
			fileWriter.close();

			//3. 编译源代码，并且生成 .class文件
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
			Iterable iterable = manager.getJavaFileObjects(file);
			JavaCompiler.CompilationTask task = compiler.getTask
					(null, manager, null, null, null, iterable);
			task.call();
			manager.close();
			//4. 将 .class文件中的内容动态加载到JVM中来

			//5.返回被代理后的代理对象
			Class proxyClass = classLoader.findClass("$Proxy0");
			Constructor constructor = proxyClass.getConstructor(SelfInvocationHandler.class);
			file.delete();
			return constructor.newInstance(h);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String generateSrc(Class interfaces) {
		StringBuffer src = new StringBuffer();
		src.append("package com.github.proxy.self;" + line);
		src.append("import java.lang.reflect.Method;" + line);
		src.append("public class $Proxy0\n" +
				" extends SelfProxy\n" +
				" implements " + interfaces.getName() + "{" + line);
		src.append("SelfInvocationHandler h;" + line);
		src.append("public $Proxy0(SelfInvocationHandler h) {" + line);
		src.append("this.h = h;" + line + "}" + line);

		for (Method m : interfaces.getMethods()) {
			src.append("public " + m.getReturnType().getName() + " " + m.getName() + "(){" + line);
			src.append("try{" + line);
			src.append("Method m = " + interfaces.getName() + ".class.getMethod(\"" + m.getName() + "\", new Class[]{});" + line);
			src.append("this.h.invoke(this,m,null);" + line);
			src.append("}catch(Throwable e){e.printStackTrace();}" + line);
			src.append("}" + line);
		}

		src.append("}");
		return src.toString();
	}
}
