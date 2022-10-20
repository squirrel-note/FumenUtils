package commands.substractpieces;

import java.util.List;

public record SubstractPiecesParameters(
    List<String> pieces,
    String base,
    SubstractPiecesOptionalParameter option,
    String outpath
) {}
