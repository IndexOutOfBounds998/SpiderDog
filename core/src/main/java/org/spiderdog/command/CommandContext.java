package org.spiderdog.command;

import org.spiderdog.api.GenericTypeCommand;
import org.spiderdog.enums.TypEnum;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * CommandContext
 * Created by yang on 2019/8/13.
 */
public class CommandContext {

    private static CommandContext commandContext = new CommandContext();
    private static HashMap<Type, GenericTypeCommand> map = new HashMap<>();

    public static CommandContext getCommandContext() {
        return commandContext;
    }

    static {

        registry();


    }

    private static void registry() {
        map.put(TypEnum.String.getType(), new StringCommand());
        map.put(TypEnum.Array.getType(), new ArrayCommand());
    }


    public GenericTypeCommand genericTypeCommand(Type type) {

        return map.get(type);

    }


}
