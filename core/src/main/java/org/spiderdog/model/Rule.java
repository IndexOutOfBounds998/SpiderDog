package org.spiderdog.model;

import java.lang.reflect.Type;

/**
 * Rule
 * Created by yang on 2019/8/13.
 */
public class Rule {

    private String xpath;
    private String seletor;
    private String attr;
    private Type type;


    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public String getSeletor() {
        return seletor;
    }

    public void setSeletor(String seletor) {
        this.seletor = seletor;
    }
}
