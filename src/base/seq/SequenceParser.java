package base.seq;

import java.util.ArrayList;
import java.util.List;

public class SequenceParser {
    public static List<String> parse(List<String> args) throws Exception {
        List<String> output = new ArrayList<>();
        for (String a : args) for (String p : a.split(";")) {
            Sequence x = new Sequence(p);
            for (String b : x.parse()) output.add(b);
        }
        return output;
    }

    public static List<String> parseNoDuplication(List<String> args) throws Exception {
        List<String> output = new ArrayList<>();
        for (String str : parse(args)) if (!output.contains(str)) output.add(str);
        return output;
    }

    public static List<String> parse(String args) throws Exception {
        List<String> output = new ArrayList<>();
        for (String p : args.split(";")) {
            Sequence x = new Sequence(p);
            for (String a : x.parse()) output.add(a);
        }
        return output;
    }

    public static List<String> parseNoDuplication(String args) throws Exception {
        List<String> output = new ArrayList<>();
        for (String str : parse(args)) if (!output.contains(str)) output.add(str);
        return output;
    }
}
