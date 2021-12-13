package com.putoet.day13;

import java.util.List;
import java.util.Optional;

public record FoldingInstruction(com.putoet.day13.FoldingInstruction.Along along, int offset) {
    private static final String FOLDING_ALONG_X = "fold along x=";
    private static final String FOLDING_ALONG_Y = "fold along y=";

    public enum Along {X, Y}

    public static FoldingInstruction of(Along along, int offset) {
        return new FoldingInstruction(along,offset);
    }

    public static Optional<FoldingInstruction> of(String line) {
        assert line != null;
        line = line.trim();

        if (line.startsWith(FOLDING_ALONG_X))
            return Optional.of(new FoldingInstruction(Along.X, Integer.parseInt(line.substring(FOLDING_ALONG_X.length()))));
        else if (line.startsWith(FOLDING_ALONG_Y))
            return Optional.of(new FoldingInstruction(Along.Y, Integer.parseInt(line.substring(FOLDING_ALONG_Y.length()))));

        return Optional.empty();
    }

    public static List<FoldingInstruction> of(List<String> lines) {
        assert lines != null;

        return lines.stream().map(FoldingInstruction::of).filter(Optional::isPresent).map(Optional::get).toList();
    }
}
