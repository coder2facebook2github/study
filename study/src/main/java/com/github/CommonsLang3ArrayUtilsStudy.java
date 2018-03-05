package com.github;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.stream.Collectors;

public class CommonsLang3ArrayUtilsStudy {
	public static void main(String... args) {
//		String a = "北京市,110100|天津市,120100|石家庄市,130100|沈阳市,210100|大连市,210200|哈尔滨市,230100|上海市,310100|南京市,320100|无锡市,320200|徐州市,320300|常州市,320400|苏州市,320500|南通市,320600|连云港市,320700|盐城市,320900|扬州市,321000|杭州市,330100|宁波市,330200|温州市,330300|嘉兴市,330400|湖州市,330500|绍兴市,330600|金华市,330700|台州市,331000|合肥市,340100|芜湖市,340200|蚌埠市,340300|福州市,350100|厦门市,350200|泉州市,350500|漳州市,350600|南昌市,360100|九江市,360400|赣州市,360700|济南市,370100|青岛市,370200|淄博市,370300|烟台市,370600|潍坊市,370700|威海市,371000|临沂市,371300|郑州市,410100|武汉市,420100|十堰市,420300|宜昌市,420500|襄阳市,420600|长沙市,430100|株洲市,430200|湘潭市,430300|衡阳市,430400|岳阳市,430600|广州市,440100|深圳市,440300|珠海市,440400|汕头市,440500|佛山市,440600|江门市,440700|惠州市,441300|东莞市,441900|南宁市,450100|柳州市,450200|桂林市,450300|海口市,460100|重庆市,500100|成都市,510100|泸州市,510500|德阳市,510600|绵阳市,510700|遂宁市,510900|乐山市,511100|南充市,511300|达州市,511700|贵阳市,520100|昆明市,530100|西安市,610100|宝鸡市,610300|兰州市,620100|银川市,640100";
//		String[] arr = a.split("\\|");
//		System.out.println(arr[0]);
//		String b = "a, ";
//		String[] arr2 = b.split(",");
//		System.out.println(Arrays.toString(arr2));


	}


	/**
	 * 把一个二维数组转换成map
	 */
	public static void toMap(){
		String[][] arr = new String[][]{{"key1", "value1"}, {"key2", "value2"}, {"key3", "value3"}};
		Map map = ArrayUtils.toMap(arr);
		map.forEach((k, v) -> System.out.println(k + " = " + v));
//		Map map1 = Map.of("a", 1);
	}



	public static void toArray(){
		Integer[] arr = ArrayUtils.toArray(1, 2, 3);

	}


}
