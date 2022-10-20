package commands.common;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class Helper {
    public static void displayHelp(Options options) {
        new HelpFormatter().printHelp("[options]", options);
        System.exit(0);
    }
}
