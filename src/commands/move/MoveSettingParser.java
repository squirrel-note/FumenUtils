package commands.move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import base.v115.URL;
import base.v115.URL.Site;
import commands.common.Helper;
import commands.common.Input;
import commands.common.OptionBuilder;
import exceptions.ParseException;
import exceptions.ParseException.Parameter;

public class MoveSettingParser {
    private static final Options OPTIONS = getOptions();

    public static MoveParameters parse(List<String> args) throws Exception {
        CommandLine cl = new DefaultParser().parse(OPTIONS, args.toArray(new String[args.size()]));

        if (cl.hasOption("help")) Helper.displayHelp(OPTIONS);

        List<String> fumens = new ArrayList<>();
        if (cl.hasOption("t")) fumens = URL.toCode(Arrays.asList(cl.getOptionValues("t")));
        else if (cl.hasOption("tp")) fumens = URL.toCode(Input.inputAll(cl.getOptionValue("tp")));
        else throw new ParseException(Parameter.FUMEN);

        List<String> seqs = new ArrayList<>();
        if (cl.hasOption("p")) seqs = Arrays.asList(cl.getOptionValues("p"));
        else if (cl.hasOption("pp")) seqs = Input.inputAll(cl.getOptionValue("pp"));
        else throw new ParseException(Parameter.SEQ);

        int height = Integer.parseInt(cl.getOptionValue("h", "0"));
        
        boolean conbination = cl.hasOption("co");

        List<MoveOptionalParameter> options = new ArrayList<>();
        if (cl.hasOption("op")) for (String str : cl.getOptionValues("op")) switch (str) {
            case "code":
                options.add(MoveOptionalParameter.CODE);
                break;
            case "gray":
                options.add(MoveOptionalParameter.GRAY);
                break;
            case "height":
                options.add(MoveOptionalParameter.HEIGHT);
                break;
            case "pcable":
                options.add(MoveOptionalParameter.PCABLE);
                break;

            case "all":
                options.add(MoveOptionalParameter.GRAY);
                options.add(MoveOptionalParameter.HEIGHT);
                options.add(MoveOptionalParameter.PCABLE);
                options.add(MoveOptionalParameter.CODE);
                break;

            default:
                break;
        }

        Site link = Site.valueOf(cl.getOptionValue("li", "none").toUpperCase());

        String outpath = cl.getOptionValue("o", "output/move.csv");

        return new MoveParameters(fumens, seqs, height, conbination, options, link, outpath);
    }

    private static Options getOptions() {
        return new Options().addOption(OptionBuilder.buildBooleanOption("help", "(no args)", "help"))
                            .addOption(OptionBuilder.buildMultipleOption("t", "tetfu (v115 only)", "multi-order accepted"))
                            .addOption(OptionBuilder.buildSingleOption("tp", "path", "path of v115 input (disable when -t specified)"))
                            .addOption(OptionBuilder.buildMultipleOption("p", "piece sequence", "multi-order accepted"))
                            .addOption(OptionBuilder.buildSingleOption("pp", "path", "path of seq input (disable when -p specified)"))
                            .addOption(OptionBuilder.buildSingleOption("h", "int", "height restriction"))
                            .addOption(OptionBuilder.buildBooleanOption("co", "(no args)", "run all combinations of v115 and seq"))
                            .addOption(OptionBuilder.buildMultipleOption("op", "options", "'gray', 'code', 'height' and 'pcable' are available"))
                            .addOption(OptionBuilder.buildSingleOption("li", "link", "'none', 'zui' or 'knew' are available"))
                            .addOption(OptionBuilder.buildSingleOption("o", "path", "path of output file"))
                            ;
    }
}
