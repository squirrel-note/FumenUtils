package base.seq;

import java.util.ArrayList;
import java.util.List;

import exceptions.SequenceException;

class Sequence implements SeqPart {
    private List<SeqPart> q = new ArrayList<>();

    Sequence(String p) throws Exception {
        p = p.toUpperCase();
        if (!p.matches("[TILJSZO*\\[\\]\\^P0-9!,\\(\\):_\\{\\}|+]+")) throw new SequenceException();

        int i = 0;
        int e = 0;
        int n = 0;
        boolean d = false;
        while (i < p.length()) switch (p.charAt(i)) {
            case '[':
                e = p.indexOf("]", i);
                if (e == p.length() - 1) {
                    q.add(new SeqPartPermutation(p.substring(i + 1, e), 1));
                    i = e + 1;
                } else switch (p.charAt(e + 1)) {
                    case 'P':
                        String z = "";
                        int k = e + 2;
                        while (k < p.length() && p.substring(k, k + 1).matches("[0-9]")) z += p.charAt(k++);
                        q.add(new SeqPartPermutation(p.substring(i + 1, e), Integer.parseInt(z)));
                        i = k + 1;
                        break;
                    case '!':
                        q.add(new SeqPartFactorial(p.substring(i + 1, e)));
                        i = e + 2;
                        break;
                    default:
                        q.add(new SeqPartPermutation(p.substring(i + 1, e), 1));
                        i = e + 1;
                        break;
                }
                break;

            case '(':
                System.out.print("Sorry, the syntax ( <sequence> : <insert sequence> : ... ) is not supported currently.");
                System.exit(0);
                n = 0;
                d = false;
                List<String> r = new ArrayList<>();
                for (int j = i + 1; j < p.length(); j++) {
                    switch (p.charAt(j)) {
                        case '(':
                            n++;
                            break;
                        case ':':
                            if (n == 0) {
                                r.add(p.substring(i + 1, j));
                                i = j;
                                break;
                            }
                            break;
                        case ')':
                            if (n < 0) throw new SequenceException();
                            if (n == 0) {
                                r.add(p.substring(i + 1, j));
                                i = j + 1;
                                d = true;
                                break;
                            }
                            n--;
                            break;
                        default: break;
                    }
                    if (d) break;
                }
                q.add(new SeqPartInsertion(r));
                break;

            case '{':
                System.out.print("Sorry, the syntax { <sequence> | <condition> } is not supported currently.");
                System.exit(0);
                /*
                e = 0;
                n = 0;
                d = false;
                Boolean g = false;
                String w = "";
                String h = "";
                for (int j = i + 1; j < p.length(); j++) {
                    switch (p.charAt(j)) {
                        case '{':
                            n++;
                            break;
                        case '|':
                            if (g) throw new SequenceException();
                            if (n == 0) {
                                w = p.substring(i + 1, j);
                                i = j;
                                g = true;
                                break;
                            }
                            break;
                        case '}':
                            if (n < 0 || !g) throw new SequenceException();
                            if (n == 0) {
                                h = p.substring(i + 1, j);
                                i = j + 1;
                                d = true;
                                break;
                            }
                            n--;
                            break;
                        default: break;
                    }
                    if (d) break;
                }
                q.add(new SeqPartRestriction(w, h));
                break;
                */

            case '*':
                if (i == p.length() - 1) {
                    q.add(new SeqPartPermutation("TILJSZO", 1));
                    i += 1;
                } else switch (p.charAt(i + 1)) {
                    case 'P':
                        q.add(new SeqPartPermutation("TILJSZO", Integer.parseInt(p.substring(i + 2, i + 3))));
                        i += 3;
                        break;
                    case '!':
                        q.add(new SeqPartPermutation("TILJSZO", 7));
                        i += 2;
                        break;
                    default:
                        q.add(new SeqPartPermutation("TILJSZO", 1));
                        i += 1;
                        break;
                }
                break;

            case 'I':
            case 'J':
            case 'L':
            case 'O':
            case 'S':
            case 'T':
            case 'Z':
                String x = "";
                while (i < p.length() && ("TILJSZO".contains(p.substring(i, i + 1)) || p.charAt(i) == ',')) x += p.substring(i, ++i);
                q.add(new SeqPartString(x));
                break;
            case ',':
                i++;
                break;

            default:
                throw new SequenceException();
        }
    }

    @Override
    public List<String> parse() throws Exception {
        List<List<String>> l = new ArrayList<>();
        for (SeqPart a : q) l.add(a.parse());
        return concat(l);
    }
    
    private static List<String> concat(List<List<String>> l) throws Exception {
        if (l.size() < 1) {
            throw new SequenceException();
        } else if (l.size() == 1) {
            return l.get(0);
        } else {
            List<String> output = new ArrayList<>();
            List<String> m = concat(l.subList(1, l.size()));
            for (String a : l.get(0)) for (String b : m) output.add(a + b);
            return output;
        }
    }
}
