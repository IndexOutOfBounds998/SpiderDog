package org.spiderdog.command;

import com.google.common.base.Strings;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.spiderdog.api.GenericTypeCommand;
import org.spiderdog.model.Rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * StringCommand
 * Created by yang on 2019/8/13.
 */
public class ArrayCommand extends AbsCommand implements GenericTypeCommand {

    @Override
    public Object command(Rule rule, Document document) {
        List<Object> list = new ArrayList<>();
        String seletor = rule.getSeletor();
        if (!Strings.isNullOrEmpty(seletor)) {
            Elements select = document.select(seletor);
            for (Element element : select) {
                if (Strings.isNullOrEmpty(rule.getAttr())) {
                    list.add(element.text())
                    ;
                } else {
                    list.add(element.attr(rule.getAttr()));
                }
            }

        }
        return (list).toArray(new String[list.size()]);
    }
}
