package commands.countline;

import java.util.ArrayList;
import java.util.List;

import base.Constants;
import base.v115.Fumen;
import base.v115.PageFumen;
import commands.common.EntryPoint;
import commands.common.Output;

public class CountLineEntryPoint implements EntryPoint {
    private final CountLineParameters parameters;

    public CountLineEntryPoint(List<String> args) throws Exception {
        parameters = CountLineSettingParser.parse(args);
    }

    @Override
    public void run() throws Exception {
        List<String> output = new ArrayList<>();
        for (String v115 : parameters.fumens()) {
            if (v115.matches(Constants.FUMENREGEX)) {
                if (parameters.allPage()) {
                    output.add(String.valueOf(new Fumen(v115).countClearLine()));
                } else {
                    output.add(String.valueOf(new PageFumen(v115).countClearLine()));
                }
            } else {
                output.add("");
            }
        }
        Output.listOutput(output, parameters.outpath());
    }

    @Override
    public String getName() throws Exception {
        return "clearline";
    }
}
