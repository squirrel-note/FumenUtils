package commands.hole;

import java.util.ArrayList;
import java.util.List;

import base.Constants;
import base.v115.PageFumen;
import commands.common.EntryPoint;
import commands.common.Output;

public class HoleEntryPoint implements EntryPoint {
    private final HoleParameters parameters;

    public HoleEntryPoint(List<String> args) throws Exception {
        parameters = HoleSettingParser.parse(args);
    }

    @Override
    public void run() throws Exception {
        List<String> output = new ArrayList<>();
        for (String v115 : parameters.fumens()) {
            if (v115.matches(Constants.FUMENREGEX)) {
                output.add(Integer.toString(new PageFumen(v115).countHoles()));
            } else {
                output.add("");
            }
        }
        Output.listOutput(output, parameters.outpath());
    }

    @Override
    public String getName() throws Exception {
        return "hole";
    }
}
