package base.v115;

import java.util.ArrayList;
import java.util.List;

import base.Code64;
import base.Constants;
import exceptions.FumenException;

public class Fumen {
    private String v115 = "";

    private List<Page> page = new ArrayList<>();

    public Fumen(String a) throws Exception {
        if (!a.matches(Constants.FUMENREGEX)) throw new FumenException("Input string is not a fumencode.");

        v115 = a;
        page.clear();

        String t = v115.replace("?", "");
        int i = t.indexOf("@") + 1;
        int vh = 0;

        while (i < t.length()) {
            Page x = new Page(this);

            if (vh == 0) {
                List<Integer> table = new ArrayList<>();
                int fill = 0;
                while (table.size() < 240) {
                    fill = Code64.decode(t.substring(i, i += 2));
                    int cnt = fill % 240 + 1;
                    int col = Math.floorDiv(fill, 240) - 8;
                    for (int j = 0; j < cnt && table.size() < 240; j++) table.add(col);
                }
                x.setDiffTable(table);

                if (fill == 2159) vh = Code64.decode(t.substring(i, ++i));
            } else {
                x.setEmpDiffTable();
                vh--;
            }

            x.setFlags(t.substring(i, i += 3));

            if (x.commentExist()) {
                x.setComment(t.substring(i, i += 2 + 5 * Math.ceil((double)(Code64.decode(t.substring(i, i + 2))) / 4)));
            }

            page.add(x);
        }
    }

    public void encode() {
        v115 = "v115@";

        int vh = 0;
        for (int p = 0; p < page.size(); p++) {
            Page x = page.get(p);

            if (vh != 0) vh --;
            else {
                int col = 0;
                int cnt = 0;

                for (int a : x.getDiffTable()) {
                    if (col == a) cnt++;
                    else {
                        v115 += Code64.encode(240 * (col + 8) + cnt - 1, 2);

                        col = a;
                        cnt = 1;
                    }
                }
                v115 += Code64.encode(240 * (col + 8) + cnt - 1, 2);

                if (240 * (col + 8) + cnt == 2160) {
                    for (int i = p + 1; i < page.size(); i++) {
                        int sum_ = 0;
                        for (int a : page.get(i).getDiffTable()) sum_ += Math.abs(a);
                        if (sum_ != 0) {
                            break;
                        }
                        vh++;
                    }
                    v115 += Code64.encode(Math.min(64, vh), 1);
                }
            }

            v115 += x.getFlags();
        }

        if (v115.length() > 47) {
            StringBuilder sb = new StringBuilder(v115);
            for (int i = 0; i < Math.floorDiv(v115.length() - 1, 47); i++) {
                sb.insert(47 + 48 * i, "?");
            }
            v115 = sb.toString();
        }
    }

    public String getEncode() {
        encode();
        return v115;
    }

    public void buildTable() throws Exception {
        List<Integer> prev = new ArrayList<>();
        for (int i = 0; i < 240; i++) prev.add(0);

        for (Page x : page) {
            x.buildTable(prev);
            prev.clear();
            prev = x.getFlagsAppliedTable();
        }
    }


    public Fumen add(String t) throws Exception {
        buildTable();

        Fumen f = new Fumen(t);
        f.buildTable();
        f.page.get(0).buildDiffTable(page.get(page.size() - 1).getFlagsAppliedTable());
        for (Page p : f.page) page.add(p);

        return this;
    }

    public Fumen add(List<String> t) throws Exception {
        buildTable();

        for (String a : t) {
            Fumen f = new Fumen(a);
            f.buildTable();
            f.page.get(0).buildDiffTable(page.get(page.size() - 1).getFlagsAppliedTable());
            for (Page p : f.page) page.add(p);
        }

        return this;
    }


    public String getv115(int p) throws Exception {
        if (p < 0) {
            throw new FumenException("Specified pageindex is negative.");
        } else if (p < page.size()) {
            return page.get(p).getv115();
        } else {
            Page x = new Page();
            x.setEmpDiffTable();
            x.setFlags("AgH");
            x.buildTable(page.get(page.size() - 1).getFlagsAppliedTable());
            return x.getv115();
        }
    }

    public List<String> getChunkedv115() throws Exception {
        List<String> output = new ArrayList<>();
        for (Page x : page) output.add(x.getv115());
        return output;
    }

    public int getHeight(int p) throws Exception {
        if (p < 0 || p >= page.size()) {
            throw new FumenException("Specified pageindex is out of page or negative.");
        }
        return page.get(p).getHeight();
    }

    public int getClearLine(int p) throws Exception {
        if (p < 0 || p >= page.size()) {
            throw new FumenException("Specified pageindex is out of page or negative.");
        }
        return page.get(p).countClearLine();
    }

    public int countClearLine() throws Exception {
        int output = 0;
        for (Page x : page) output += x.countClearLine();
        return output;
    }

    public Fumen clearComment() {
        for (Page x : page) x.clearComment();
        return this;
    }

    public Fumen clearComment(int p) throws Exception {
        if (p < 0 || p >= page.size()) {
            throw new FumenException("Specified pageindex is out of page or negative.");
        }
        page.get(p).clearComment();

        return this;
    }

    public Fumen dry() throws Exception {
        buildTable();

        List<Integer> prev = new ArrayList<>();
        for (int i = 0; i < 240; i++) prev.add(0);

        for (Page x : page) {
            x.dry();
            x.buildDiffTable(prev);
            prev = x.getFlagsAppliedTable();
        }

        encode();
        return this;
    }
}
