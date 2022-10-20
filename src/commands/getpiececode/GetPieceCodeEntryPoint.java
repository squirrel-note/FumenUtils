package commands.getpiececode;

import java.util.ArrayList;
import java.util.List;

import base.Constants;
import base.v115.PieceFumen;
import commands.common.EntryPoint;
import commands.common.Output;

public class GetPieceCodeEntryPoint implements EntryPoint {
    private final GetPieceCodeParameters parameters;

    public GetPieceCodeEntryPoint(List<String> args) throws Exception {
        parameters = GetPieceCodeSettingParser.parse(args);
    }

    @Override
    public void run() throws Exception {
        List<String> output = new ArrayList<>();
        for (String v115 : parameters.fumens()) {
            if (v115.matches(Constants.FUMENREGEX)) {
                output.add("&" + new PieceFumen(v115).getCode());
            } else {
                output.add("");
            }
        }
        Output.listOutput(output, parameters.outpath());
    }

    @Override
    public String getName() throws Exception {
        return "getpiececode";
    }
}
