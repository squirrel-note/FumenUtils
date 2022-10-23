package commands.clearline;

import java.util.List;

import base.v115.URL.Site;

public record ClearLineParameters(
    List<String> fumens,
    Site link,
    String outpath
) {}
