package com.github;


import java.io.*;

public class DependencyPO implements Cloneable, Serializable{


	private static final long serialVersionUID = -5347295981099132369L;
	private String groupId;
	private String artifactId;
	private String version;

	private Long age;
	private String name;

	public DependencyPO (){}

	public DependencyPO (String name, Long age) {
		this.age = age;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DependencyPO (Long age) {
		this.age = age;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	private JavaIoStudy javaIoStudy;

	public JavaIoStudy getJavaIoStudy() {
		return javaIoStudy;
	}

	public void setJavaIoStudy(JavaIoStudy javaIoStudy) {
		this.javaIoStudy = javaIoStudy;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Object 的 clone() 方法只能实现 浅copy
	 *
	 * @return
	 */
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	public DependencyPO deepClone() {
		DependencyPO clone = null;
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(this);
			objectOutputStream.flush();
			objectOutputStream.close();

			ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
			clone = (DependencyPO) objectInputStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clone;
	}


}
