package base.seq;

import java.util.List;

import exceptions.SequenceException;

public class SeqPartFactorial implements SeqPart {
    private SeqPartPermutation q;

    SeqPartFactorial(String p) throws Exception {
        String q = "";
        if (!p.matches("[TILJSZO]+")) {
            if (p.charAt(0) != '^') throw new SequenceException();
            for (char ch : "TILJSZO".toCharArray()) {
                if (p.indexOf(ch) < 0) q += ch;
            }
        } else {
            q = p;
        }
        this.q = new SeqPartPermutation(q, q.length());
    }

    @Override
    public List<String> parse() throws Exception {
        return q.parse();
    }
}
