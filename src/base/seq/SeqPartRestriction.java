package base.seq;

//import java.util.ArrayList;
//import java.util.List;

//import exceptions.SequenceException;

class SeqPartRestriction /*implements SeqPart*/ {/*
    private Sequence base;
    private Restriction q;

    SeqPartRestriction(String p, String o) throws Exception {
        base = new Sequence(p);
        q = restrictors(o);
    }

    private Restriction restrictors(String p) throws Exception {
        int i = 0;
        int e = 0;
        int n = 0;
        boolean d = false;
        while (i < p.length()) switch (p.charAt(i)) {
            case '(':
                for (int j = i; j < p.length(); j++) {
                    switch (p.charAt(j)) {
                        case '(':
                            n++;
                            break;
                        case ')':
                            if (n < 0) throw new SequenceException();
                            if (n == 0) {
                                e = j;
                                d = true;
                                break;
                            }
                            n--;
                        default: break;
                    }
                }
                break;

            default:
                break;
        }
    }

    @Override
    public List<String> parse() {
        return null;
    }


    private interface Restriction {
        List<String> parse(List<String> p);
    }

    private interface ConditionPair<A, B> extends Restriction {}

    private class PairAnd<A, B> implements ConditionPair<Restriction, Restriction> {
        PairAnd(String p) {}

        @Override
        public List<String> parse(List<String> p) {
            return null;
        }
    }

    private class PairOr<A, B> implements ConditionPair<Restriction, Restriction> {
        PairOr(String p) {}

        @Override
        public List<String> parse(List<String> p) {
            return null;
        }
    }

    private class PairXor<A, B> implements ConditionPair<Restriction, Restriction> {
        PairXor(String p) {}

        @Override
        public List<String> parse(List<String> p) {
            return null;
        }
    }

    private class RestrictPosition implements Restriction {
        RestrictPosition(String p) {}

        @Override
        public List<String> parse(List<String> p) {
            return null;
        }
    }

    private class RestrictOrder implements Restriction {
        RestrictOrder(String p) {}

        @Override
        public List<String> parse(List<String> p) {
            return null;
        }
    }*/
}
