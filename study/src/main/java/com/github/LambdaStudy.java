package com.github;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class LambdaStudy implements Serializable {


    public static void main(String... args) {
        LambdaStudy demo = new LambdaStudy();
        demo.reduce(0, null, null);

        Date now = new Date();
        Date yesterday = DateUtils.addDays(now, -1);
        System.out.println(yesterday.before(now));
    }


    public void predicateAnd() {
        List<String> languages = Arrays.asList("Java", "Python", "Scala", "C#", "Lisp", "Haskell", "Javascript");
        Predicate<String> startWithJ = (name) -> name.startsWith("J");
        Predicate<String> fourLetterLong = (name) -> name.length() > 4;
        filter(languages, startWithJ.and(fourLetterLong));
    }

    public void lambda() {
        List<String> languages = Arrays.asList("Java", "Python", "Scala", "C#", "Lisp", "Haskell", "Javascript");
        filter(languages, name -> name.startsWith("J"));
    }

    public void nullNameInnerClass() {
        List<String> languages = Arrays.asList("Java", "Python", "Scala", "C#", "Lisp", "Haskell");
        filter(languages, new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.startsWith("C");
            }
        });
    }

    public void innerClass() {
        List<String> languages = Arrays.asList("Java", "Python", "Scala", "C#", "Lisp", "Haskell");
        filter(languages, new PredicateImp());
    }

    class PredicateImp implements Predicate<String> {

        @Override
        public boolean test(String s) {
            return s.startsWith("J");
        }
    }


    public static void filter(List<String> names, Predicate<String> condition) {
        for (String name : names) {
            if (condition.test(name)) {
                System.out.println("name: " + name);
            }
        }
    }


    public static void map() {
        List<DependencyPO> dependencyPOList = new ArrayList<DependencyPO>() {{
            add(new DependencyPO("张三", 22L));
            add(new DependencyPO("李四", 25L));
            add(new DependencyPO("王五", 26L));
        }};
        List<String> names = dependencyPOList.stream().map(user -> JSONObject.toJSONString(user)).collect(Collectors.toList());
        System.out.println(Joiner.on(",").join(names));
    }

    public void reduce(BinaryOperator<Integer> operator) {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 5);
        operator = (a, b) -> a + b;
        Optional<Integer> optional = list.stream().reduce(operator);
        System.out.println(optional.get());
    }

    public void reduce(Integer t, BinaryOperator<Integer> operator) {
        List<Integer> list = Lists.newArrayList(1, 3, 5, 6);
        operator = (a, b) -> a + b;
        int result = list.stream().reduce(t, operator);
        System.out.println("result: " + result);
    }

    public <T, U> void reduce(U identity, BiFunction<U, ? super T, U> accumulator,
                              BinaryOperator<U> combiner) {
        List<String> list = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        Integer result = list.stream().reduce(0, (a, b) -> a + Integer.parseInt(b), (u, t) -> t + u);
        System.out.println(result);
    }

    public static void getdebahecaixiang() {
        long[] sushuArray = LongStream.rangeClosed(2, 10000).filter(value -> testSushu(value)).toArray();
        LongStream.rangeClosed(4, 10000).filter(value -> value % 2 == 0).forEach(value -> {
            for (int i = 0; i < sushuArray.length; i++) {
                long left = value - sushuArray[i];
                if (testSushu(left)) {
                    System.out.println(value + " = " + sushuArray[i] + " + " + left);
                    break;
                }
            }
        });
    }

    public static boolean testSushu(long value) {
        int i;
        for (i = 2; i < Math.sqrt(value); i++) {
            if (value % i == 0) {
                return false;
            }
        }
        if (i > Math.sqrt(value)) {
            return true;
        }
        return false;
    }

    public static void newThread() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("===================: " + i);
                try {
                    Thread.sleep(8);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        long[] sushuArray = LongStream.rangeClosed(2, 10000).filter(value -> testSushu(value)).toArray();
        LongStream.rangeClosed(4, 100).filter(value -> value % 2 == 0).forEach(value -> {
            for (int i = 0; i < sushuArray.length; i++) {
                long left = value - sushuArray[i];
                if (testSushu(left)) {
                    System.out.println(value + " = " + sushuArray[i] + " + " + left);
                    break;
                }
            }
        });
    }


    public void subListTest() {
//		List<Integer> list = Lists.newArrayList(1, 3, 5, 6);
//		System.out.println(Arrays.toString(list.toArray()));
//		List<Integer> list2 = list.subList(0, 2);
//		System.out.println(Arrays.toString(list.toArray()));
//		System.out.println(Arrays.toString(list2.toArray()));
//		list2.set(0, 2);
//		System.out.println(Arrays.toString(list.toArray()));
//		System.out.println(Arrays.toString(list2.toArray()));

//		Integer[] a = new Integer[]{1, 2, 3, 5, 6};
//		List<Integer> list3 = Arrays.asList(a);
//		System.out.println(Arrays.toString(a));
//		System.out.println(Arrays.toString(list3.toArray()));
////		list3.add(7);
//		a[0] = 0;
//		System.out.println(Arrays.toString(a));
//		System.out.println(Arrays.toString(list3.toArray()));

//        List<Integer> list4 = Lists.newArrayList(1, 2, 3, 5, 6, 7);
//        Iterator<Integer> integerIterator = list4.iterator();
//
//        while (integerIterator.hasNext()) {
//            Integer integer = integerIterator.next();
//            System.out.println(integer);
//            if (integer > 5) {
//                integerIterator.remove();
//            }
//        }
//
//        System.out.println(Arrays.toString(list4.toArray()));


        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        for (String item : list) {
            if ("3".equals(item)) {
                list.remove(item);
            }
        }


        System.out.println(Arrays.toString(list.toArray()));

    }

}
