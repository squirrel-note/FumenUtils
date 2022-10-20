package commands.sequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import commands.common.Helper;
import commands.common.Input;
import commands.common.OptionBuilder;
import exceptions.ParseException;
import exceptions.ParseException.Parameter;

public class SequenceSettingParser {
    private static final Options OPTIONS = getOptions();

    public static SequenceParameters parse(List<String> args) throws Exception {
        CommandLine cl = new DefaultParser().parse(OPTIONS, args.toArray(new String[args.size()]));

        if (cl.hasOption("help")) Helper.displayHelp(OPTIONS);

        List<String> seqs = new ArrayList<>();
        if (cl.hasOption("p")) seqs = Arrays.asList(cl.getOptionValues("p"));
        else if (cl.hasOption("pp")) seqs = Input.inputAll(cl.getOptionValue("pp"));
        else throw new ParseException(Parameter.SEQ);

        String outpath = cl.getOptionValue("o", "output/sequence.txt");

        return new SequenceParameters(seqs, outpath);
    }

    private static Options getOptions() {
        return new Options().addOption(OptionBuilder.buildBooleanOption("help", "(no args)", "help"))
                            .addOption(OptionBuilder.buildMultipleOption("p", "piece sequence", "multi-order accepted"))
                            .addOption(OptionBuilder.buildSingleOption("pp", "path", "path of seq input (disable when -p specified)"))
                            .addOption(OptionBuilder.buildSingleOption("o", "path", "path of output file"))
                            ;
    }
}
