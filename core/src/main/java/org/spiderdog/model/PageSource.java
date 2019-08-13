package org.spiderdog.model;

/**
 * Created by yang on 2019/8/13.
 */
public class PageSource {

    private String seletor;

    private String attr;

    public PageSource() {
    }

    public PageSource(String seletor, String attr) {
        this.seletor = seletor;
        this.attr = attr;
    }

    public String getSeletor() {
        return seletor;
    }

    public void setSeletor(String seletor) {
        this.seletor = seletor;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }
}
