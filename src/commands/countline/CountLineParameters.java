package commands.countline;

import java.util.List;

public record CountLineParameters(
    List<String> fumens,
    boolean allPage,
    String outpath
) {}
