package commands.move;

import java.util.ArrayList;
import java.util.List;

import base.Constants;
import base.Pair;
import base.seq.SequenceParser;
import base.v115.PieceFumen;
import exceptions.SequenceException;

public class Move {
    public static List<Pair<String,List<PieceFumen>>> main(String v115, String seq, int height) throws Exception {
        List<Pair<String,List<PieceFumen>>> output = new ArrayList<>();

        for (String p : SequenceParser.parseNoDuplication(seq)) {
            List<PieceFumen> preview = new ArrayList<>();
            List<PieceFumen> result = new ArrayList<>();
            result.add(new PieceFumen(v115));

            for (char piece : p.toCharArray()) {
                int pieceIndex = "ILOZTJS".indexOf(piece);
                preview.clear();
                for (PieceFumen x : result) preview.add(x);

                result.clear();
                for (PieceFumen baseFumen : preview) {
                    List<Integer> clearedTable = baseFumen.toPageFumen().getLineClearedTable();
                    int clearedHeight = baseFumen.toPageFumen().getLineClearedHeight();
                    int ceil = 22;
                    int left = 0;
                    int right = 0;
                    switch (pieceIndex) {
                        case 0:
                            if (height < 1) ceil = clearedHeight + 4;
                            else ceil = height - baseFumen.toPageFumen().countClearLine();
                            for (int i = 1; i <= ceil; i++) {
                                for (int j = 0; j < 2; j++) {
                                    left = Constants.PIECE_COLUMN_RESTRICTION[pieceIndex][j][0];
                                    right = Constants.PIECE_COLUMN_RESTRICTION[pieceIndex][j][1];
                                    for (int k = left; k < right; k++) {
                                        boolean gr = false;
                                        for (int f : Constants.MIN_PIECE_SHAPE[pieceIndex][j]) if (10 * i - k - f > 0 && (10 * i - k - f <= 10 || clearedTable.get(240 - 10 * i + k + f) != 0)) { gr = true; break; }
                                        for (int f : Constants.MIN_PIECE_SHAPE[pieceIndex][j]) if (10 * i - k - f <= 0 || 10 * i - k - f > 10 * ceil || clearedTable.get(230 - 10 * i + k + f) != 0) { gr = false; break; }
                                        if (gr) {
                                            PieceFumen x = new PieceFumen(baseFumen);
                                            x.add(pieceIndex + 1, j + 2, 230 - 10 * i + k);
                                            result.add(x);
                                        }
                                    }
                                }
                            }
                            break;

                        case 3:
                        case 6:
                            if (height < 1) ceil = clearedHeight + 3;
                            else ceil = height - baseFumen.toPageFumen().countClearLine();
                            for (int i = 2; i <= ceil; i++) {
                                for (int j = 0; j < 2; j++) {
                                    left = Constants.PIECE_COLUMN_RESTRICTION[pieceIndex][j][0];
                                    right = Constants.PIECE_COLUMN_RESTRICTION[pieceIndex][j][1];
                                    for (int k = left; k < right; k++) {
                                        boolean gr = false;
                                        for (int f : Constants.MIN_PIECE_SHAPE[pieceIndex][j]) if (10 * i - k - f > 0 && (10 * i - k - f <= 10 || clearedTable.get(240 - 10 * i + k + f) != 0)) { gr = true; break; }
                                        for (int f : Constants.MIN_PIECE_SHAPE[pieceIndex][j]) if (10 * i - k - f <= 0 || 10 * i - k - f > 10 * ceil || clearedTable.get(230 - 10 * i + k + f) != 0) { gr = false; break; }
                                        if (gr) {
                                            PieceFumen x = new PieceFumen(baseFumen);
                                            x.add(pieceIndex + 1, j + 2, 230 - 10 * i + k);
                                            result.add(x);
                                        }
                                    }
                                }
                            }
                            break;

                        case 2:
                            if (height < 1) ceil = clearedHeight + 2;
                            else ceil = height - baseFumen.toPageFumen().countClearLine();
                            for (int i = 2; i <= ceil; i++) {
                                left = Constants.PIECE_COLUMN_RESTRICTION[pieceIndex][0][0];
                                right = Constants.PIECE_COLUMN_RESTRICTION[pieceIndex][0][1];
                                for (int k = left; k < right; k++) {
                                    boolean gr = false;
                                    for (int f : Constants.MIN_PIECE_SHAPE[pieceIndex][0]) if (10 * i - k - f > 0 && (10 * i - k - f <= 10 || clearedTable.get(240 - 10 * i + k + f) != 0)) { gr = true; break; }
                                    for (int f : Constants.MIN_PIECE_SHAPE[pieceIndex][0]) if (10 * i - k - f <= 0 || 10 * i - k - f > 10 * ceil || clearedTable.get(230 - 10 * i + k + f) != 0) { gr = false; break; }
                                    if (gr) {
                                        PieceFumen x = new PieceFumen(baseFumen);
                                        x.add(pieceIndex + 1, 2, 230 - 10 * i + k);
                                        result.add(x);
                                    }
                                }
                            }
                            break;

                        case 1:
                        case 4:
                        case 5:
                            if (height < 1) height = clearedHeight + 3;
                            else ceil = height - baseFumen.toPageFumen().countClearLine();
                            for (int i = 1; i <= ceil; i++) {
                                for (int j = 0; j < 4; j++) {
                                    left = Constants.PIECE_COLUMN_RESTRICTION[pieceIndex][j][0];
                                    right = Constants.PIECE_COLUMN_RESTRICTION[pieceIndex][j][1];
                                    for (int k = left; k < right; k++) {
                                        boolean gr = false;
                                        for (int f : Constants.MIN_PIECE_SHAPE[pieceIndex][j]) if (10 * i - k - f > 0 && (10 * i - k - f <= 10 || clearedTable.get(240 - 10 * i + k + f) != 0)) { gr = true; break; }
                                        for (int f : Constants.MIN_PIECE_SHAPE[pieceIndex][j]) if (10 * i - k - f <= 0 || 10 * i - k - f > 10 * ceil || clearedTable.get(230 - 10 * i + k + f) != 0) { gr = false; break; }
                                        if (gr) {
                                            PieceFumen x = new PieceFumen(baseFumen);
                                            x.add(pieceIndex + 1, j, 230 - 10 * i + k);
                                            result.add(x);
                                        }
                                    }
                                }
                            }
                            break;

                        default: throw new SequenceException();
                    }
                }
            }

            List<PieceFumen> add = new ArrayList<>();
            for (PieceFumen x : result) add.add(x);
            output.add(new Pair<String,List<PieceFumen>>(p, add));
        }

        return output;
    }
}
