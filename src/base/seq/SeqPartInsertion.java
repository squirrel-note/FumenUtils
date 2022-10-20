package base.seq;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import exceptions.SequenceException;

class SeqPartInsertion implements SeqPart {
    private Sequence base;
    private List<Insertion> q = new ArrayList<>();

    SeqPartInsertion(List<String> p) throws Exception {
        base = new Sequence(p.remove(0));
        for (String a : p) q.add(new Insertion(a));
    }

    @Override
    public List<String> parse() throws Exception {
        return null;
    }


    private class Insertion {
        private SeqPart q;
        private List<Integer> in = new ArrayList<>();
        private boolean un = true;

        Insertion(String p) throws Exception {
            int u = p.indexOf("_");
            if (u < 0) {
                q = new Sequence(p);
            } else if (u == 0 || u == p.length() - 1) {
                throw new SequenceException();
            } else {
                q = new Sequence(p.substring(0, u));
                if (p.subSequence(u + 1, u + 2) == "^" && u == p.length() - 2) {
                    for (String a : p.substring(u + 2).split(",")) in.add(Integer.parseInt(a));
                } else {
                    for (String a : p.substring(u + 1).split(",")) in.add(Integer.parseInt(a));
                    un = false;
                }
            }
        }

        List<String> parse(List<String> p) throws Exception {
            List<String> r = q.parse();
            List<String> output = new ArrayList<>();
            for (String a : p) {
                StringBuilder a_ = new StringBuilder(a);
                LinkedList<String> s = new LinkedList<>();
            }
            return output;
        }
    }
}
