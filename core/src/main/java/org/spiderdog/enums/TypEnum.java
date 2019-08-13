package org.spiderdog.enums;

import java.lang.reflect.Type;

/**
 * TypEnum
 * Created by yang on 2019/8/13.
 */
public enum TypEnum {


    String(java.lang.String.class),

    Array(java.lang.String[].class);

    private Type type;


    TypEnum(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
