package commands.pcable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import base.v115.URL;
import commands.common.Helper;
import commands.common.Input;
import commands.common.OptionBuilder;
import exceptions.ParseException;
import exceptions.ParseException.Parameter;

public class PCableSettingParser {
    private static final Options OPTIONS = getOptions();

    public static PCableParameters parse(List<String> args) throws Exception {
        CommandLine cl = new DefaultParser().parse(OPTIONS, args.toArray(new String[args.size()]));

        if (cl.hasOption("help")) Helper.displayHelp(OPTIONS);

        List<String> fumens = new ArrayList<>();
        if (cl.hasOption("t")) fumens = URL.toCode(Arrays.asList(cl.getOptionValues("t")));
        else if (cl.hasOption("tp")) fumens = URL.toCode(Input.inputAll(cl.getOptionValue("tp")));
        else throw new ParseException(Parameter.FUMEN);

        int height = Integer.parseInt(cl.getOptionValue("h"));

        String outpath = cl.getOptionValue("o", "output/pcable.txt");

        return new PCableParameters(fumens, height, outpath);
    }

    private static Options getOptions() {
        return new Options().addOption(OptionBuilder.buildBooleanOption("help", "(no args)", "help"))
                            .addOption(OptionBuilder.buildMultipleOption("t", "tetfu (v115 only)", "multi-order accepted"))
                            .addOption(OptionBuilder.buildSingleOption("tp", "path", "path of v115 input (disable when -t specified)"))
                            .addOption(OptionBuilder.buildSingleOption("h", "int", "height order"))
                            .addOption(OptionBuilder.buildSingleOption("o", "path", "path of output file"))
                            ;
    }
}
