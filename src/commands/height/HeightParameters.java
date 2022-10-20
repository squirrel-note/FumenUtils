package commands.height;

import java.util.List;

public record HeightParameters(
    List<String> fumens,
    String outpath
) {}
