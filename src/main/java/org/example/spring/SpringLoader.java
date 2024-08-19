package org.example.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * SpringLoader类表示，用于加载Spring容器
 */
public class SpringLoader {
    private static AnnotationConfigApplicationContext  context;// Spring容器

    /**
     * 初始化spring
     */
    public static void Init()  {
        context = new AnnotationConfigApplicationContext();// 创建Spring容器
        context.scan("org.example"); // 指定要扫描的包路径
        context.refresh();// 刷新Spring容器
    }

    /**
     * 根据类型获取对应的实例
     * @param cls
     * @return
     * @param <T>
     */
    public static <T> T getBean(Class<T> cls){
        return context.getBean(cls);// 根据类型获取对应的实例
    }

}