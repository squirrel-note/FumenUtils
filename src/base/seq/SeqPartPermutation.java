package base.seq;

import java.util.ArrayList;
import java.util.List;

import exceptions.SequenceException;

class SeqPartPermutation implements SeqPart {
    private String q = "";
    private int c = 0;

    SeqPartPermutation(String p, int d) throws Exception {
        p = p.replace(",", "");
        if (!p.matches("[TILJSZO]+")) {
            if (p.charAt(0) != '^') throw new SequenceException();
            for (char ch : "TILJSZO".toCharArray()) {
                if (p.indexOf(ch) < 0) q += ch;
            }
        } else {
            q = p;
        }
        if (d > q.length()) throw new SequenceException();
        c = d;
    }

    @Override
    public List<String> parse() throws Exception {
        return concat(q, c);
    }

    private static List<String> concat(String q, int c) throws Exception {
        if (c < 1) {
            throw new SequenceException();
        } else if (c == 1) {
            List<String> output = new ArrayList<>();
            for (char a : q.toCharArray()) output.add(Character.toString(a));
            return output;
        } else {
            List<String> output = new ArrayList<>();
            for (int i = 0; i < q.length(); i++) for (String a : concat(q.substring(0, i) + q.substring(i + 1), c - 1)) output.add(q.substring(i, i + 1) + a);
            return output;
        }
    }
}
