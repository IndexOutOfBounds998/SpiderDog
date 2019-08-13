package org.spiderdog.api;

import org.jsoup.nodes.Document;
import org.spiderdog.model.Rule;

/**
 * GenericTypeCommand
 * Created by yang on 2019/8/13.
 */
public interface GenericTypeCommand {


    Object command(Rule rule, Document document);


}
