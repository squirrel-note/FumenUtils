package commands.move;

import java.util.List;

import base.v115.URL.Site;

public record MoveParameters(
    List<String> fumens,
    List<String> seqs,
    int height,
    boolean combination,
    List<MoveOptionalParameter> options,
    Site link,
    String outpath
) {}
