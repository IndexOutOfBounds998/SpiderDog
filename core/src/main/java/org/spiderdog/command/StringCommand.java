package org.spiderdog.command;

import com.google.common.base.Strings;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.spiderdog.api.GenericTypeCommand;
import org.spiderdog.model.Rule;

/**
 * StringCommand
 * Created by yang on 2019/8/13.
 */
public class StringCommand extends AbsCommand implements GenericTypeCommand {

    @Override
    public Object command(Rule rule, Document document) {
        String parseText = "";
        String seletor = rule.getSeletor();
        if (!Strings.isNullOrEmpty(seletor)) {
            Elements select = document.select(seletor);
            if (Strings.isNullOrEmpty(rule.getAttr())) {
                parseText = select.text();
            } else {
                parseText = select.attr(rule.getAttr());
            }
        }

        return parseText;
    }
}
