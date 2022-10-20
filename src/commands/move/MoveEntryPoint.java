package commands.move;

import java.util.ArrayList;
import java.util.List;

import base.Constants;
import base.Pair;
import base.v115.PageFumen;
import base.v115.PieceFumen;
import base.v115.URL;
import commands.common.EntryPoint;
import commands.common.Output;

public class MoveEntryPoint implements EntryPoint {
    private final MoveParameters parameters;

    public MoveEntryPoint(List<String> args) throws Exception {
        parameters = MoveSettingParser.parse(args);
    }

    @Override
    public void run() throws Exception {
        List<String> t = parameters.fumens();
        List<String> p = parameters.seqs();
        int h = parameters.height();

        List<String> output = new ArrayList<>();
        String header = "";
        header += "piece (all)";
        header += ",piece (moved)";
        header += ",v115";
        header += ",reduced";
        for (MoveOptionalParameter optionalParameter : parameters.options()) header += "," + optionalParameter.str;
        output.add(header);

        if (parameters.combination()) {
            for (String v115 : t) if (v115.matches(Constants.FUMENREGEX)) {
                for (String seq : p) if (seq.matches(Constants.SEQREGEX)) {
                    output.add("");
                    for (Pair<String,List<PieceFumen>> result : Move.main(v115, seq, h)) for (PieceFumen fumen : result.right()) output.add(buildRow(fumen, result.left()));
                }
            }
        } else {
            if (t.size() != p.size()) throw new IllegalArgumentException("Invailed argument. -t and -p should have same size.");
            
            for (int i = 0; i < t.size(); i++) if (t.get(i).matches(Constants.FUMENREGEX) && p.get(i).matches(Constants.SEQREGEX)) {
                output.add("");
                for (Pair<String,List<PieceFumen>> result : Move.main(t.get(i), p.get(i), h)) for (PieceFumen fumen : result.right()) output.add(buildRow(fumen, result.left()));
            }
        }

        Output.listOutput(output, parameters.outpath());
    }
    
    private String buildRow(PieceFumen fumen, String pieces) throws Exception {
        String output = "";
        PageFumen fumen_ = fumen.toPageFumen();

        output +=       fumen.getPiece();
        output += "," + pieces;
        output += "," + URL.toLink(parameters.link(), fumen.getEncode());
        output += "," + URL.toLink(parameters.link(), fumen_.getEncode());
        for (MoveOptionalParameter optionalParameter : parameters.options()) output += "," + optionalParameter.process(fumen_, fumen, parameters.height());
        
        return output;
    }

    @Override
    public String getName() throws Exception {
        return "move";
    }
}
