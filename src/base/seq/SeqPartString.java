package base.seq;

import java.util.ArrayList;
import java.util.List;

import exceptions.SequenceException;

class SeqPartString implements SeqPart {
    private String q = "";

    SeqPartString(String p) throws Exception {
        p = p.replace(",", "");
        if (!p.matches("[TILJSZO]+")) throw new SequenceException();
        q = p;
    }

    @Override
    public List<String> parse() {
        List<String> output = new ArrayList<>();
        output.add(q);
        return output;
    }
}
