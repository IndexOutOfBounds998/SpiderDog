package org.spiderdog.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
    String selector() default "";

    String attr() default "";

    /**
     * 是否作为下一次执行的地址
     *
     * @return
     */
    boolean isNextUrl() default false;
}