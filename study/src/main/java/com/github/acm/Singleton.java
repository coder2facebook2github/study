package com.github.acm;

/**
 * 线程安全的单例模式
 */
public class Singleton {
    private Singleton() {}

    private volatile static Singleton instance = null;

    public static Singleton getInstance() {
        if(instance == null){
            synchronized(Singleton.class) {
                if(instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
