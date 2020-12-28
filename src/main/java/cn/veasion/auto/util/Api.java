package cn.veasion.auto.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Api
 *
 * @author luozhuowei
 * @date 2020/6/12
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Api {

    /**
     * 注释
     */
    String value() default "";

    /**
     * 是否生成文档
     */
    boolean generator() default true;

    /**
     * 返回结果（被代理前的结果）
     */
    Class<?> result() default Object.class;

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @interface Param {

        /**
         * 参数描述
         */
        String desc() default "";

        /**
         * 参数是否允许不传
         */
        boolean allowNone() default false;

        /**
         * 参数是否允许传 null
         */
        boolean allowNull() default false;

        /**
         * 类型
         */
        Class<?> type() default Object.class;

        /**
         * js 类型
         */
        String jsType() default "";
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface ClassInfo {

        /**
         * 绑定名称
         */
        String value() default "";

        /**
         * 是否 root
         */
        boolean root() default false;

        /**
         * 参数描述
         */
        String desc() default "";

    }
}
