package commands.getpiece;

import java.util.List;

public record GetPieceParameters(
    List<String> fumens,
    String outpath
) {}
