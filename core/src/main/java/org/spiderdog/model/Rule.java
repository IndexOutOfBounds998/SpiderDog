package org.spiderdog.model;

import java.lang.reflect.Type;

/**
 * Created by yang on 2019/8/13.
 */
public class Rule {

    String xpath;
    String seletor;
    String attr;
    Type type;
    boolean isNextUrl;

    public boolean isNextUrl() {
        return isNextUrl;
    }

    public void setNextUrl(boolean nextUrl) {
        isNextUrl = nextUrl;
    }

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
