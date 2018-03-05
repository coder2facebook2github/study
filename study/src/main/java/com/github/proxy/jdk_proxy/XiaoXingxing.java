package com.github.proxy.jdk_proxy;

public class XiaoXingxing implements Person{

	private String sex = "女";
	private String name = "小星星";

	@Override
	public void findLove() {
		System.out.println("我叫" + this.name + ",性别:" + this.sex);
		System.out.println("高富帅");
		System.out.println("有房有车");
		System.out.println("身高180CM，体重70KG");
	}

	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
