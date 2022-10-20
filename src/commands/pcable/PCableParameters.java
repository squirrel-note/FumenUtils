package commands.pcable;

import java.util.List;

public record PCableParameters(
    List<String> fumens,
    int height,
    String outpath
) {}
