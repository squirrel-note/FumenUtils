package commands.clearline;

import java.util.List;

public record ClearLineParameters(
    List<String> fumens,
    boolean allPage,
    String outpath
) {}
