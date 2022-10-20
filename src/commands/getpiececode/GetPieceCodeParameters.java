package commands.getpiececode;

import java.util.List;

public record GetPieceCodeParameters(
    List<String> fumens,
    String outpath
) {}
