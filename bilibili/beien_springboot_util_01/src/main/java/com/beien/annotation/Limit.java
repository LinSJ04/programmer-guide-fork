package com.beien.annotation;

import com.beien.enums.LimitType;

import java.lang.annotation.*;

/**
 * 自定义限流注解
 * @author beien
 */
@Target(ElementType.METHOD) // 限流注解可以标注在方法上
// RetentionPolicy.SOURCE:注解仅保留在源码级别，在编译时会被丢弃。这种类型的注解通常用于提供一些提示或者信息给编译器使用。
// @Override 和 @SuppressWarnings 这样的注解不需要在字节码中保留，它们的作用在于编译阶段帮助开发者检查代码是否正确覆盖了方法或抑制不必要的警告。
// RetentionPolicy.CLASS:默认的保留策略。注解会被编译到 class 文件中，但在运行时并不需要被虚拟机保留，除非虚拟机特别指定要保留。
// 某些框架可能会使用这种保留策略来在编译期进行代码生成或者优化，而这些注解在程序运行时不再需要。
// RetentionPolicy.RUNTIME:注解不仅会被编译到 class 文件中，并且会在运行时通过反射机制可以被读取。当你需要在运行时动态获取注解信息并据此执行某些逻辑时，就需要使用这种保留策略。
// 创建自定义限流注解，配合 AOP 或者其他反射机制，在运行时读取注解内容来实现特定的功能（如权限验证、日志记录、限流控制等）。
@Retention(RetentionPolicy.RUNTIME)
// 加了这个注解后，使用工具生成文档时，会显示该注解的信息。Javadoc
@Documented
public @interface  Limit { // 像定义接口一样定义属性
    /**
     * 限流key前缀，主要用于写入redis
     */
    String prefix() default "limit:";
    /**
     * 限流时间,单位秒
     */
    int time() default 60;
    /**
     * 限流次数，一分钟之内可以访问接口10次
     */
    int count() default 10;
    /**
     * 限流类型
     */
    LimitType type() default LimitType.DEFAULT;
}
