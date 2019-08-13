package org.spiderdog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * PageScan 扫描页面的列表url 作为终极页的上一层url处理
 * Created by yang on 2019/8/13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageScan {

    String seletor() default "";

    String attr() default "";
}
