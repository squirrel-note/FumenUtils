package commands.hole;

import java.util.List;

public record HoleParameters(
    List<String> fumens,
    String outpath
) {}
