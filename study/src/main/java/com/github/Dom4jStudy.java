package com.github;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dom4j 解析XML 和 生成 XML
 */
public class Dom4jStudy implements Serializable{

	private static final long serialVersionUID = 2424344924308212929L;

	public String dom = "你好";

	public static void main(String[] args) {
//		new Dom4jStudy().dom4jRead();
		try {
			new Dom4jStudy().dom4jWrite();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void dom4jRead() {
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(new File("C:\\MyIDE\\Idea\\workspace\\github_space\\javaStudy\\study\\pom.xml"));
			Element rootElement = document.getRootElement();
			List<DependencyPO> dependencyPOList = new ArrayList<>();
			List<Element> childrenList  = rootElement.element("dependencies").elements("dependency");
			for(Element e : childrenList) {
				DependencyPO dependency = new DependencyPO();
				dependency.setVersion(e.element("version").getTextTrim());
				dependency.setArtifactId(e.element("artifactId").getTextTrim());
				dependency.setGroupId(e.element("groupId").getTextTrim());
				dependencyPOList.add(dependency);
			}
			for(DependencyPO dependency : dependencyPOList) {
				System.out.println("groupId: " + dependency.getGroupId());
				System.out.println("artifactId: " + dependency.getArtifactId());
				System.out.println("version: " + dependency.getVersion()+ "\n");
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	public void dom4jWrite() throws IOException {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement("project", "http://maven.apache.org/POM/4.0.0");
		Namespace namespaceXSI = new Namespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.add(namespaceXSI);
		root.addAttribute("xsi:schemaLocation", "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd");
		Element modelVersion = root.addElement("modelVersion");
		modelVersion.setText("4.0.0");
		Element dependencies = root.addElement("dependencies");
		Element dependency = dependencies.addElement("dependency");
		dependency.addElement("groupId").addText("dom4j");
		dependency.addElement("artifactId").addText("dom4j");
		dependency.addElement("version").addText(".6.1");

		OutputFormat format = new OutputFormat("\t", true);
		File xmlFile = new File("D:\\MyIDE\\IDEA\\IdeaWorkspace\\github_space\\javaStudy\\study\\dom4jCreateXml.xml");
		XMLWriter writer = new XMLWriter(new FileOutputStream(xmlFile), format);
		writer.write(document);
		writer.flush();
	}
}
