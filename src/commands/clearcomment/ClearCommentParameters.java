package commands.clearcomment;

import java.util.List;

import base.v115.URL.Site;

public record ClearCommentParameters(
    List<String> fumens,
    Site link,
    String outpath
) {}
