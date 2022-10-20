package commands.sequence;

import java.util.List;

public record SequenceParameters(
    List<String> seqs,
    String outpath
) {}
