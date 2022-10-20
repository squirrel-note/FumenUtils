package commands.concat;

import java.util.List;

import base.v115.URL.Site;

public record ConcatParameters(
    List<String> fumens,
    Site link,
    String outpath
) {}
