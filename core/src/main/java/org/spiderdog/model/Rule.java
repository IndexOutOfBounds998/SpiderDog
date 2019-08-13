package org.spiderdog.model;

/**
 * Created by yang on 2019/8/13.
 */
public class Rule {

    String xpath;
    String seletor;
    String attr;
    Class aClass;

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
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
