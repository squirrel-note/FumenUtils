package commands.height;

import java.util.ArrayList;
import java.util.List;

import base.Constants;
import base.v115.PageFumen;
import commands.common.EntryPoint;
import commands.common.Output;

public class HeightEntryPoint implements EntryPoint {
    private final HeightParameters parameters;

    public HeightEntryPoint(List<String> args) throws Exception {
        parameters = HeightSettingParser.parse(args);
    }

    @Override
    public void run() throws Exception {
        List<String> output = new ArrayList<>();
        for (String v115 : parameters.fumens()) {
            if (v115.matches(Constants.FUMENREGEX)) {
                output.add(String.valueOf(new PageFumen(v115).getHeight()));
            } else {
                output.add("");
            }
        }
        Output.listOutput(output, parameters.outpath());
    }

    @Override
    public String getName() throws Exception {
        return "height";
    }
}
