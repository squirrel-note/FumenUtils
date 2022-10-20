package commands.substractpieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import commands.common.EntryPoint;
import commands.common.Output;

public class SubstractPiecesEntryPoint implements EntryPoint {
    private static final String TILJSZO = "TILJSZO";

    private final SubstractPiecesParameters parameters;

    public SubstractPiecesEntryPoint(List<String> args) throws Exception {
        parameters = SubstractPiecesSettingParser.parse(args);
    }

    @Override
    public void run() throws Exception {
        String base = parameters.base().replace("!", TILJSZO).toUpperCase();
        List<Integer> basePiecesCountList = Arrays.asList(0,0,0,0,0,0,0);
        int baselength = base.length();
        for (int i = 0; i < 7; i++) basePiecesCountList.set(i, baselength - base.replace(TILJSZO.subSequence(i, i + 1), "").length());

        List<String> output = new ArrayList<>();

        if (parameters.option().equals(SubstractPiecesOptionalParameter.NONE)) {
            output.add("base:," + parameters.base());
            output.add("seq,surplus,lack");
        }

        for (String pieces : parameters.pieces()) {
            if (pieces.equals("")) {
                output.add("");
                continue;
            }

            pieces = pieces.replace("!", TILJSZO).toUpperCase();
            List<Integer> piecesCountList = Arrays.asList(0,0,0,0,0,0,0);
            int length = pieces.length();
            for (int i = 0; i < 7; i++) piecesCountList.set(i, length - pieces.replace(TILJSZO.subSequence(i, i + 1), "").length());

            String surplus = "";
            String lack = "";
            for (int i = 0; i < 7; i++) {
                int diff = piecesCountList.get(i) - basePiecesCountList.get(i);
                if (diff < 0) {
                    lack += TILJSZO.substring(i, i + 1).repeat(- diff);
                } else {
                    surplus += TILJSZO.substring(i, i + 1).repeat(diff);
                }
            }

            switch (parameters.option()) {
                case SURPLUS:
                    if (!lack.equals("")) throw new Exception("The option 'surplus' specified but there is lack.");
                    output.add(surplus);
                    break;

                case LACK:
                    if (!surplus.equals("")) throw new Exception("The option 'lack' specified but there is surplus.");
                    output.add(lack);
                    break;

                case NONE:
                    output.add(pieces + "," + surplus + "," + lack);
                    break;
            
                default: throw new Exception();
            }
        }

        Output.listOutput(output, parameters.outpath());
    }

    @Override
    public String getName() throws Exception {
        return "substractpieces";
    }
}
