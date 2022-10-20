package commands.reduce;

import java.util.List;

import base.v115.URL.Site;

public record ReduceParameters(
    List<String> fumens,
    Site link,
    String outpath
) {}
