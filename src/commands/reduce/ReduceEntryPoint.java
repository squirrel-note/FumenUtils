package commands.reduce;

import java.util.ArrayList;
import java.util.List;

import base.Constants;
import base.v115.PieceFumen;
import base.v115.URL;
import commands.common.EntryPoint;
import commands.common.Output;

public class ReduceEntryPoint implements EntryPoint {
    private final ReduceParameters parameters;

    public ReduceEntryPoint(List<String> args) throws Exception {
        parameters = ReduceSettingParser.parse(args);
    }

    @Override
    public void run() throws Exception {
        List<String> output = new ArrayList<>();
        for (String v115 : parameters.fumens()) {
            if (v115.matches(Constants.FUMENREGEX)) {
                output.add(new PieceFumen(v115).getReducedv115());
            } else {
                output.add("");
            }
        }
        Output.listOutput(URL.toLink(parameters.link(), output), parameters.outpath());
    }

    @Override
    public String getName() throws Exception {
        return "reduce";
    }
}
