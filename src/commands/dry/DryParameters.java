package commands.dry;

import java.util.List;

import base.v115.URL.Site;

public record DryParameters(
    List<String> fumens,
    Site link,
    String outpath
) {}
