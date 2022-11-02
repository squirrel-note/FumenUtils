package commands.sequence;

import java.util.ArrayList;
import java.util.List;

import base.Constants;
import base.seq.SequenceParser;
import commands.common.EntryPoint;
import commands.common.Output;

public class SequenceEntryPoint implements EntryPoint {
    private final SequenceParameters parameters;

    public SequenceEntryPoint(List<String> args) throws Exception {
        parameters = SequenceSettingParser.parse(args);
    }

    @Override
    public void run() throws Exception {
        List<String> output = new ArrayList<>();
        for (String seq : parameters.seqs()) {
            if (seq.toUpperCase().matches(Constants.SEQREGEX)) {
                for (String result : SequenceParser.parseNoDuplication(seq)) output.add(result);
            } else {
                output.add("");
            }
        }
        Output.listOutput(output, parameters.outpath());
    }

    @Override
    public String getName() throws Exception {
        return "sequence";
    }
}
