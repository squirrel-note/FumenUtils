package commands.common;

import org.apache.commons.cli.Option;

public class OptionBuilder {
    public static Option buildBooleanOption(String name, String argName, String desc) {
        return Option.builder(name)
                     .optionalArg(true)
                     .argName(argName)
                     .desc(desc)
                     .build();
    }

    public static Option buildSingleOption(String name, String argName, String desc) {
        return Option.builder(name)
                     .optionalArg(true)
                     .hasArg()
                     .numberOfArgs(1)
                     .argName(argName)
                     .desc(desc)
                     .build();
    }

    public static Option buildMultipleOption(String name, String argName, String desc) {
        return Option.builder(name)
                     .optionalArg(true)
                     .hasArg()
                     .numberOfArgs(Integer.MAX_VALUE)
                     .valueSeparator(' ')
                     .argName(argName)
                     .desc(desc)
                     .build();
    }
}
