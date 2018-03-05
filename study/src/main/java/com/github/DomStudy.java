package com.github;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dom 解析 和生成XML
 */
public class DomStudy {

	public static void main(String... args) {
//		new DomStudy().domRead();
		try {
			new DomStudy().domWrite();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	public void domRead() {
		File xmlFile = new File("C:\\MyIDE\\Idea\\workspace\\study\\study/pom.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		List<DependencyPO> list = new ArrayList<>();
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(xmlFile);
			NodeList dependencies = document.getElementsByTagName("dependency");
			for(int i = 0; i < dependencies.getLength(); i++) {
				NodeList childrenNode = dependencies.item(i).getChildNodes();
				DependencyPO dependencyPO = new DependencyPO();
				for(int j = 0; j < childrenNode.getLength(); j++) {
					Node child = childrenNode.item(j);
					if("groupId".equals(child.getNodeName())){
						dependencyPO.setGroupId(child.getTextContent());
					} else if("artifactId".equals(child.getNodeName())){
						dependencyPO.setArtifactId(child.getTextContent());
					} else if("version".equals(child.getNodeName())) {
						dependencyPO.setVersion(child.getTextContent());
					}
				}
				list.add(dependencyPO);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(DependencyPO dependency : list) {
			System.out.println("groupId: " + dependency.getGroupId());
			System.out.println("artifactId: " + dependency.getArtifactId());
			System.out.println("version: " + dependency.getVersion()+ "\n");
		}
	}

	public void domWrite() throws ParserConfigurationException, TransformerException, FileNotFoundException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		document.setXmlStandalone(true);
		Element root = document.createElement("project");
		root.setAttribute("xmlns", "http://maven.apache.org/POM/4.0.0");
		root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("xsi:schemaLocation", "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd");
		Text textEnter = document.createTextNode("\n");
		Text textTab = document.createTextNode("\t");
		Element modelVersion = document.createElement("modelVersion");
		modelVersion.setTextContent("4.0.0");
		root.appendChild(textEnter);
//		root.appendChild(textTab);
		root.appendChild(modelVersion);
		root.appendChild(textEnter);
		document.appendChild(root);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);

		File xmlFile = new File("C:\\MyIDE\\Idea\\workspace\\github_space\\javaStudy\\study\\CreatePom.xml");
		PrintWriter printWriter = new PrintWriter(new FileOutputStream(xmlFile));
		StreamResult streamResult = new StreamResult(printWriter);
		transformer.transform(source, streamResult);
		printWriter.close();

	}

}
