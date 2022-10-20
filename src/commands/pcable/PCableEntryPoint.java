package commands.pcable;

import java.util.ArrayList;
import java.util.List;

import base.Constants;
import base.v115.PageFumen;
import commands.common.EntryPoint;
import commands.common.Output;

public class PCableEntryPoint implements EntryPoint {
    private final PCableParameters parameters;

    public PCableEntryPoint(List<String> args) throws Exception {
        parameters = PCableSettingParser.parse(args);
    }

    @Override
    public void run() throws Exception {
        List<String> output = new ArrayList<>();
        for (String v115 : parameters.fumens()) {
            if (!v115.matches(Constants.FUMENREGEX)) {
                output.add("");
            } else if (new PageFumen(v115).isPCable(parameters.height())) {
                output.add("O");
            } else {
                output.add("X");
            }
        }
        Output.listOutput(output, parameters.outpath());
    }

    @Override
    public String getName() throws Exception {
        return "pcable";
    }
}
