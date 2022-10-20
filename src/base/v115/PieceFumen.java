package base.v115;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import base.Code64;
import base.Constants;
import base.Pair;
import base.Primes;
import exceptions.FumenException;

public class PieceFumen {
    private String v115;

    private List<Integer> table = new ArrayList<>();

    private List<Piece> pieces = new ArrayList<>();

    public PieceFumen(String a) throws Exception {
        v115 = a;
        table.clear();
        pieces.clear();

        String t = v115.replace("?", "");
        int i = t.indexOf("@") + 1;
        int vh = 0;

        int fill = 0;
        while (table.size() < 240) {
            fill = Code64.decode(t.substring(i, i += 2));
            int cnt = fill % 240 + 1;
            int col = Math.floorDiv(fill, 240) - 8;
            for (int j = 0; j < cnt && table.size() < 240; j++) table.add(col);
        }

        if (fill == 2159) {
            vh = Code64.decode(t.substring(i, ++i));
        }

        int flags = Code64.decode(t.substring(i, i += 3));

        if (flags % 8 != 0) pieces.add(new Piece(flags));

        if (Math.floorDiv(flags, 61440) % 2 == 1) i += 2 + 5 * Math.ceil((double)(Code64.decode(t.substring(i, i + 2))) / 4);

        while (Math.floorDiv(flags, 7680) % 4 == 0 && i < t.length()) {
            if (vh == 0) {
                int cnt = 0;
                while (cnt < 240) cnt += Code64.decode(t.substring(i, i += 2)) % 240 + 1;
                if (t.substring(i - 2, i).equals("vh")) vh = Code64.decode(t.substring(i, ++i));
            } else vh--;

            flags = Code64.decode(t.substring(i, i += 3));
            if (Math.floorDiv(flags, 122880) % 2 == 0 && flags % 8 != 0) pieces.add(new Piece(flags));

            if (Math.floorDiv(flags, 61440) % 2 == 1) i += 2 + 5 * Math.ceil((double)(Code64.decode(t.substring(i, i + 2))) / 4);
        }
    }

    public PieceFumen(PieceFumen a) throws Exception {
        v115 = a.v115;
        if (a.table.size() != 240) throw new IllegalArgumentException("Invailed table.");
        for (int i : a.table) table.add(i);
        for (Piece p : a.pieces) pieces.add(p);
    }

    public PieceFumen(List<Integer> table, List<Piece> pieces) {
        if (table.size() != 240) throw new IllegalArgumentException("Invailed table.");
        for (int i : table) this.table.add(i);
        for (Piece p : pieces) this.pieces.add(p);
        encode();
    }

    public void encode() {
        v115 = "v115@";
        int vh = 0;

        int col = 0;
        int cnt = 0;
        for (int a : table) {
            if (col == a) cnt ++;
            else {
                v115 += Code64.encode(240 * (col + 8) + cnt - 1, 2);

                col = a;
                cnt = 1;
            }
        }
        v115 += Code64.encode(240 * (col + 8) + cnt - 1, 2);

        if (pieces.isEmpty()) {
            if (240 * (col + 8) + cnt == 2160) v115 += "A";
            v115 += "AgH";
        } else {
            Piece x = pieces.get(0);
            if (240 * (col + 8) + cnt == 2160) {
                vh = Math.min(64, pieces.size() - 1);
                v115 += Code64.encode(vh, 1);
            }
            v115 += x.getFlagCode(true);

            for (int i = 1; i < pieces.size(); i++) {
                if (vh == 0) {
                    vh = Math.min(64, pieces.size() - i - 1);
                    v115 += "vh" + Code64.encode(vh, 1);
                }
                x = pieces.get(i);
                v115 += x.getFlagCode(false);
                vh --;
            }
        }
    }

    public String getEncode() {
        encode();
        return v115;
    }

    public PieceFumen add(int p, int r, int l) throws Exception {
        pieces.add(new Piece(p, r, l));

        return this;
    }

    public String getPiece() {
        String output = "";
        for (Piece x : pieces) output += "_ILOZTJS".charAt(x.p());
        return output;
    }

    public int pieceCount() {
        return pieces.size();
    }

    public List<Integer> getPieceFlags() {
        List<Integer> output = new ArrayList<>();
        for (Piece x : pieces) output.add(x.p() + x.r() * 8 + x.l() * 32);
        return output;
    }

    public String getCode(int beginIndex, int endIndex) throws Exception {
        List<String> flags = new ArrayList<>();
        for (int i = beginIndex; i < endIndex; i++) {
            Piece x = pieces.get(i);
            int primeIndex = x.getPieceIndex(toPageFumen(i).countClearedLine(0, x.getHeight()));
            if (primeIndex < 6440) flags.add(Code64.encode(Primes.PRIMELIST_3726.get(primeIndex)));
            else flags.add(Code64.encode(Primes.getPrime(primeIndex)));
        }
        return Code64.product(flags);
    }

    public String getCode(int beginIndex) throws Exception {
        return getCode(beginIndex, pieces.size());
    }

    public String getCode() throws Exception {
        return getCode(0, pieces.size());
    }

    public int getHeight() {
        int index = 0;
        for (int i = 0; i < table.size(); i++) if (table.get(i) != 0) {
            index = i;
            break;
        }
        int height = 23 - Math.floorDiv(index, 10);
        if (height < 0 || height > 22) height = 0;

        return height;
    }

    public int getHeight(int endIndex) throws Exception {
        int index = 0;
        List<Integer> table_ = getReducedTable(endIndex);
        for (int i = 0; i < table_.size(); i++) if (table_.get(i) != 0) {
            index = i;
            break;
        }
        int height = 23 - Math.floorDiv(index, 10);
        if (height < 0 || height > 22) height = 0;

        return height;
    }

    public PieceFumen reduce(int endIndex) throws Exception {
        table = getReducedTable(endIndex);
        pieces = pieces.subList(endIndex, pieces.size());

        return this;
    }

    public PieceFumen reduce() throws Exception {
        table = getReducedTable();
        pieces = new ArrayList<>();

        return this;
    }

    public List<Integer> getReducedTable(int endIndex) throws Exception {
        List<Integer> table_ = new ArrayList<>();
        for (int i = 0; i < 240; i++) table_.add(table.get(i));
        LinkedList<Pair<Integer, List<Integer>>> clears = new LinkedList<>();

        for (Piece x : pieces.subList(0, endIndex)) {
            if (x.isIllegal()) throw new FumenException("Invailed piece.");

            int p = x.p();
            int r = x.r();
            int l = x.l();

            for (int i = 0; i < 4; i++) table_.set(l + Constants.PIECE_SHAPE[p - 1][r][i], p);

            int y = Math.floorDiv(l, 10);
            for (int i = y - 1; i <= Math.min(y + 2, 22); i++) {
                if (!table_.subList(10 * i, 10 * i + 10).contains(0)) {
                    List<Integer> line = new ArrayList<>();
                    for (int j = 0; j < 10; j++) line.add(table_.remove(10 * i));
                    clears.push(new Pair<Integer,List<Integer>>(i, line));
                    for (int j = 0; j < 10; j++) table_.add(0, 0);
                }
            }
        }

        if (clears.size() > 22) throw new FumenException("Couldn't reduce fumen.");

        while (!clears.isEmpty()) {
            Pair<Integer,List<Integer>> insert = clears.pop();
            for (int j = 0; j < 10; j++) if (table_.remove(0) != 0) throw new FumenException("Couldn't reduce fumen.");
            for (int j = 0; j < 10; j++) table_.add(10 * insert.left() + j, insert.right().get(j));
        }

        return table_;
    }

    public List<Integer> getReducedTable() throws Exception {
        return getReducedTable(pieces.size());
    }

    public PageFumen toPageFumen() throws Exception {
        return new PageFumen(getReducedTable());
    }

    public PageFumen toPageFumen(int endIndex) throws Exception {
        return new PageFumen(getReducedTable(endIndex));
    }

    public String getReducedv115() throws Exception {
        return new PageFumen(getReducedTable()).getEncode();
    }

    public String getReducedv115(int endIndex) throws Exception {
        return new PageFumen(getReducedTable(endIndex)).getEncode();
    }
}
