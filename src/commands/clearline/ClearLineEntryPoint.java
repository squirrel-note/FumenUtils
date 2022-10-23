package commands.clearline;

import java.util.ArrayList;
import java.util.List;

import base.Constants;
import base.v115.PageFumen;
import base.v115.URL;
import commands.common.EntryPoint;
import commands.common.Output;

public class ClearLineEntryPoint implements EntryPoint {
    private final ClearLineParameters parameters;

    public ClearLineEntryPoint(List<String> args) throws Exception {
        parameters = ClearLineSettingParser.parse(args);
    }

    @Override
    public void run() throws Exception {
        List<String> output = new ArrayList<>();
        for (String v115 : parameters.fumens()) {
            if (v115.matches(Constants.FUMENREGEX)) {
                output.add(Integer.toString(new PageFumen(v115).countClearLine()));
            } else {
                output.add("");
            }
        }
        Output.listOutput(URL.toLink(parameters.link(), output), parameters.outpath());
    }

    @Override
    public String getName() throws Exception {
        return "clearline";
    }
}
