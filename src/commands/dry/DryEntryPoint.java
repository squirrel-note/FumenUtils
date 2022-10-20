package commands.dry;

import java.util.ArrayList;
import java.util.List;

import base.Constants;
import base.v115.Fumen;
import base.v115.URL;
import commands.common.EntryPoint;
import commands.common.Output;

public class DryEntryPoint implements EntryPoint {
    private final DryParameters parameters;

    public DryEntryPoint(List<String> args) throws Exception {
        parameters = DrySettingParser.parse(args);
    }

    @Override
    public void run() throws Exception {
        List<String> output = new ArrayList<>();
        for (String v115 : parameters.fumens()) {
            if (v115.matches(Constants.FUMENREGEX)) {
                output.add(new Fumen(v115).dry().getEncode());
            } else {
                output.add("");
            }
        }
        Output.listOutput(URL.toLink(parameters.link(), output), parameters.outpath());
    }

    @Override
    public String getName() throws Exception {
        return "dry";
    }
}
