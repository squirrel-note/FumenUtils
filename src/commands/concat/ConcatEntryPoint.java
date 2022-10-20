package commands.concat;

import java.util.ArrayList;
import java.util.List;

import base.Constants;
import base.v115.Fumen;
import base.v115.URL;
import commands.common.EntryPoint;
import commands.common.Output;

public class ConcatEntryPoint implements EntryPoint {
    private final ConcatParameters parameters;

    public ConcatEntryPoint(List<String> args) throws Exception {
        parameters = ConcatSettingParser.parse(args);
    }

    @Override
    public void run() throws Exception {
        List<String> t = new ArrayList<>();
        for (String v115 : parameters.fumens()) if (v115.matches(Constants.FUMENREGEX)) t.add(v115);
        Output.output(URL.toLink(parameters.link(), new Fumen(t.get(0)).add(t.subList(1, t.size())).getEncode()), parameters.outpath());
    }

    @Override
    public String getName() throws Exception {
        return "concat";
    }
}
