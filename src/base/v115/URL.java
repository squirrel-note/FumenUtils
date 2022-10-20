package base.v115;

import java.util.ArrayList;
import java.util.List;

import base.Constants;

public class URL {
    public enum Site { NONE, ZUI, KNEW }

    public static String toCode(String link) throws Exception {
        String output = link.replaceAll("(https?://)?(fumen\\.zui\\.jp/\\?|knewjade\\.github\\.io/#\\?d=)[vdDm]115@", "v115@");
        if (!output.matches(Constants.FUMENREGEX)) output = "";
        return output;
    }

    public static List<String> toCode(List<String> links) throws Exception {
        List<String> output = new ArrayList<>();
        for (String link : links) output.add(toCode(link));
        return output;
    }

    public static String toLink(Site site, String v115) throws Exception {
        switch (site) {
            case NONE:
                return v115;
            case ZUI:
                return "https://fumen.zui.jp/?" + v115;
            case KNEW:
                return "https://knewjade.github.io/fumen-for-mobile/#?d=" + v115;

            default: throw new Exception("Unknown Error.");
        }
    }

    public static List<String> toLink(Site site, List<String> v115) throws Exception {
        List<String> output = new ArrayList<>();
        for (String v : v115) output.add(toLink(site, v));
        return output;
    }
}
