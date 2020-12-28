package cn.veasion.auto.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ResultProxy
 *
 * <p>被代理方法返回类型请用 Object !</p>
 *
 * @author luozhuowei
 * @date 2020/6/10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResultProxy {

    /**
     * 结果是否走代理
     */
    boolean value() default true;

    /**
     * 是否有执行间隔
     */
    boolean interval() default false;

    /**
     * 是否记录日志
     */
    boolean log() default true;

}
